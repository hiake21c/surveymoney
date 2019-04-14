package com.surveymoney.common;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

public abstract class SearchSpecification<T> {

    public abstract Specification<T> toSpec();

    public <X> //
    Specification<T> isEqual(final SingularAttribute<? super T, X> e, final X target) {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.equal(root.get(e), target);
            }
        };
    }
}
