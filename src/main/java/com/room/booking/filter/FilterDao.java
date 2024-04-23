package com.room.booking.filter;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class FilterDao<T> {

    @PersistenceContext
    protected EntityManager em;

    private final Class<T> entityClass;

    public FilterDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public List<T> filter(Map<String, Object> filters, SortByFilter sortBy, PaginationFilter pagination) {
        CriteriaMaker<T> cm = new CriteriaMaker<>(em, entityClass, filters, sortBy, pagination);
        CriteriaQuery<T> cq = cm.getCriteriaQuery();
        cq.select(cq.getSelection())
          .where(extractPredicates(cm))
          .orderBy(extractOrders(cm));
        TypedQuery<T> query = em.createQuery(cq);
        setPagination(cm, query);

        return query.getResultList();
    }

    public List<T> filter(Map<String, Object> filters, SortByFilter sortByFilter) {
        return filter(filters, sortByFilter, null);
    }

    public List<T> filter(Map<String, Object> filters) {
        return filter(filters, null, null);
    }

    public Long count(Map<String, Object> filters) {
        CriteriaMaker<T> qb = new CriteriaMaker<>(em, entityClass, filters);
        CriteriaQuery<Long> cq = qb.getCriteriaBuilder().createQuery(Long.class);
        qb.setCriteriaQuery(cq);
        Root<T> rootCount = cq.from(entityClass);
        qb.setRoot(rootCount);
        cq.where(this.extractPredicates(qb));
        cq.select(qb.getCriteriaBuilder().count(qb.getRoot()));

        return em.createQuery(cq)
                 .getSingleResult();
    }

    private void setPagination(CriteriaMaker<T> cm, TypedQuery<T> query) {
        PaginationFilter pagination = cm.getPaginationFilter();
        if (pagination != null) {
            Integer page = pagination.getPage();
            Integer pageSize = pagination.getPageSize();
            if (page != null && pagination.getPageSize() != null) {
                query.setFirstResult((page - 1) * pageSize).setMaxResults(pageSize);
            }
        }
    }

    private Predicate[] extractPredicates(CriteriaMaker<T> cm) {
        List<Predicate> predicates = new ArrayList<>();
        Map<String, Object> filters = cm.getFilters();
        if (filters == null || filters.isEmpty()) {
            return new Predicate[] {};
        }

        for (Map.Entry<String, Object> e : filters.entrySet()) {
            if (e.getValue() != null) {
                final Path path = getPath(cm, e);
                if (e.getValue() instanceof DateFilter value) {
                    if (value.getTo() != null && value.getFrom() != null) {
                        Predicate predicate = predicatesForDatesBetween(cm, value, e.getKey(), path);
                        predicates.add(predicate);
                    }
                } else if (e.getValue() instanceof TimeFilter value) {
                    if (value.getTo() != null && value.getFrom() != null) {
                        Predicate predicate = predicatesForTimesBetween(cm, value, e.getKey(), path);
                        predicates.add(predicate);
                    }
                } else {
                    String key = e.getKey();
                    String[] associations = key.split("\\|");
                    Predicate predicate;
                    if (associations.length > 1) {
                        predicate = cm.getCriteriaBuilder().equal(path, e.getValue());
                    } else {
                        predicate = cm.getCriteriaBuilder().equal(cm.getRoot().get(key), e.getValue());
                    }

                    predicates.add(predicate);
                }
            }
        }

        return predicates.toArray(new Predicate[] {});
    }

    private static <T> Path getPath(CriteriaMaker<T> cm, Map.Entry<String, Object> e) {
        final String key = e.getKey();
        String[] associations = key.split("\\|");
        if (associations.length <= 1) {
            return cm.getRoot().get(key);
        }

        Join pt = cm.getRoot().join(associations[0]);
        for (int i = 1; i < associations.length - 1; i++) {
            pt = pt.join(associations[i]);
        }

        return pt.get(associations[associations.length - 1]);
    }

    private Predicate predicatesFromAssociation(CriteriaMaker<T> cm, Map.Entry<String, Object> e,
                                                String[] associations) {
        Join pt = cm.getRoot().join(associations[0]);
        for (int i = 1; i < associations.length - 1; i++) {
            pt = pt.join(associations[i]);
        }
        return cm.getCriteriaBuilder().equal(pt.get(associations[associations.length - 1]), e.getValue());
    }

    private List<Order> extractOrders(CriteriaMaker<T> cm) {
        SortByFilter sortByFilter = cm.getSortByFilter();
        if (sortByFilter == null || sortByFilter.getAttribute() == null) {
            return Collections.EMPTY_LIST;
        }

        switch (sortByFilter.getType()) {
            case DESC:
                Order desc = cm.getCriteriaBuilder().desc(cm.getRoot().get(sortByFilter.getAttribute()));
                return Arrays.asList(desc);

            case ASC:
            default:
                Order asc = cm.getCriteriaBuilder().asc(cm.getRoot().get(sortByFilter.getAttribute()));
                return Arrays.asList(asc);
        }

    }

    private Predicate predicatesForTimesBetween(CriteriaMaker<T> cm, TimeFilter dateValue, String field, Path path) {
        try {
            LocalTime time1 = LocalTime.parse(dateValue.getFrom(), DateTimeFormatter.ISO_LOCAL_TIME);
            LocalTime time2 = LocalTime.parse(dateValue.getTo(), DateTimeFormatter.ISO_LOCAL_TIME);

            Predicate predicate1 = cm.getCriteriaBuilder().greaterThanOrEqualTo(path, time1);
            Predicate predicate2 = cm.getCriteriaBuilder().lessThan(path, time2.plusMinutes(1L));

            return cm.getCriteriaBuilder().and(predicate1, predicate2);
        } catch (Exception e) {
            throw new RuntimeException("Cannot extract time parameter");
        }
    }

    private Predicate predicatesForDatesBetween(CriteriaMaker<T> cm, DateFilter dateValue, String field, Path pt) {
        try {
            LocalDateTime date1 = LocalDate.parse(dateValue.getFrom(), DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay();
            LocalDateTime date2 = LocalDate.parse(dateValue.getTo(), DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay();


            Predicate predicate1 = cm.getCriteriaBuilder().greaterThanOrEqualTo(pt, date1);
            Predicate predicate2 = cm.getCriteriaBuilder().lessThan(pt, date2.plusDays(1L));

            return cm.getCriteriaBuilder().and(predicate1, predicate2);
        } catch (Exception e) {
            throw new RuntimeException("Cannot extract date parameter", e);
        }
    }

}
