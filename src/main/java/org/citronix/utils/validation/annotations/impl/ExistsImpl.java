package org.citronix.utils.validation.annotations.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.citronix.utils.validation.annotations.Exists;

import java.util.UUID;

public class ExistsImpl implements ConstraintValidator<Exists, String> {

    @PersistenceContext
    private EntityManager entityManager;

    private Class<?> entityClass;
    private String fieldName;

    @Override
    public void initialize(Exists constraintAnnotation) {
        this.entityClass = constraintAnnotation.entity();
        this.fieldName = constraintAnnotation.field();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null || value.isBlank()) {
            return true;
        }

        try {
            UUID uuid = UUID.fromString(value);
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> query = cb.createQuery(Long.class);
            Root<?> root = query.from(entityClass);
            query.select(cb.count(root)).where(cb.equal(root.get(fieldName), uuid));

            Long count = entityManager.createQuery(query).getSingleResult();
            return count != null && count > 0;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
