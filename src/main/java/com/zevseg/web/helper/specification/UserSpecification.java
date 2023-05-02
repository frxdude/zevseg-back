package com.zevseg.web.helper.specification;

import com.zevseg.web.entity.User;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.util.Date;

@Data
@Component
public class UserSpecification implements Specification<User> {

    private SearchCriteria criteria;
    private Expression<Object> expression;
    private String groupBy;


    public UserSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    public UserSpecification() {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setKey("id");
        searchCriteria.setOperation(">");
        searchCriteria.setValue(0);
        this.criteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate
            (@NonNull Root<User> root,
             @NonNull CriteriaQuery<?> query,
             @NonNull CriteriaBuilder builder) {

        switch (criteria.getOperation()) {
            case ">":
                return builder.greaterThan(
                        root.get(criteria.getKey()), criteria.getValue().toString());
            case ">=":
                return builder.greaterThanOrEqualTo(
                        root.get(criteria.getKey()), criteria.getValue().toString());
            case "<":
                return builder.lessThan(
                        root.get(criteria.getKey()), criteria.getValue().toString());
            case "<=":
                return builder.lessThanOrEqualTo(
                        root.get(criteria.getKey()), criteria.getValue().toString());
            case "=":
                return builder.equal(
                        root.get(criteria.getKey()), criteria.getValue());
            case "!=":
                return builder.notEqual(
                        root.get(criteria.getKey()), criteria.getValue());
            case "<=>":
                return builder.between(
                        root.get(criteria.getKey()), (Date) criteria.getValue(), (Date) criteria.getValue2());
            case ".%":
                return builder.like(
                        root.get(criteria.getKey()), criteria.getValue().toString() + "%");
            case "%.":
                return builder.like(
                        root.get(criteria.getKey()), "%" + criteria.getValue().toString());
            case "%%":
                return builder.like(
                        root.get(criteria.getKey()), "%" + criteria.getValue().toString() + "%");
            case ".i%":
                return builder.like(
                        builder.lower(
                                root.get(criteria.getKey())), criteria.getValue().toString().toLowerCase() + "%");
            default:
                return null;
        }
    }
}
