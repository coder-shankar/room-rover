package com.room.booking.filter;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class CriteriaMaker<T> {

    private CriteriaBuilder criteriaBuilder;

    private CriteriaQuery criteriaQuery;

    private Root<T> root;

    private Map<String, Object> filters;
    private SortByFilter sortByFilter;
    private PaginationFilter paginationFilter;

    public CriteriaMaker(EntityManager em, Class<T> entity, Map<String, Object> filters, SortByFilter sortByFilter,
                         PaginationFilter paginationFilter) {
        this.criteriaBuilder = em.getCriteriaBuilder();
        this.criteriaQuery = criteriaBuilder.createQuery(entity);
        this.root = criteriaQuery.from(entity);
        this.filters = filters;
        this.sortByFilter = sortByFilter;
        this.paginationFilter = paginationFilter;
    }

    public CriteriaMaker(EntityManager em, Class<T> entity, Map<String, Object> filters, SortByFilter sortByFilter) {
        this.criteriaBuilder = em.getCriteriaBuilder();
        this.criteriaQuery = criteriaBuilder.createQuery(entity);
        this.root = criteriaQuery.from(entity);
        this.filters = filters;
        this.sortByFilter = sortByFilter;
    }

    public CriteriaMaker(EntityManager em, Class<T> entity, Map<String, Object> filters) {
        this.criteriaBuilder = em.getCriteriaBuilder();
        this.criteriaQuery = criteriaBuilder.createQuery(entity);
        this.root = criteriaQuery.from(entity);
        this.filters = filters;
    }

}
