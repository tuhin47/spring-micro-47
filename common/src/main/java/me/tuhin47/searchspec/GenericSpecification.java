package me.tuhin47.searchspec;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;


public class GenericSpecification<T> implements Specification<T> {

    private final List<SearchCriteria> list;

    private Function<SearchCriteria, String> GROUPID_FUNCTION = criteria -> criteria.getGroupId() == null ? UUID.randomUUID()
                                                                                                                .toString() : criteria.getGroupId();

    public GenericSpecification(List<SearchCriteria> searchCriteria) {
        this.list = Objects.requireNonNullElseGet(searchCriteria, ArrayList::new);
    }

    public void add(SearchCriteria criteria) {
        list.add(criteria);
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        return builder.and(list.stream()
                               .collect(Collectors.groupingBy(GROUPID_FUNCTION))
                               .values()
                               .stream()
                               .map(list -> getGroupPredicate(root, builder, list))
                               .toArray(Predicate[]::new));
    }

    private Predicate getGroupPredicate(Root<T> root, CriteriaBuilder builder, List<SearchCriteria> list) {

        Predicate[] predicates = list.stream().map(criteria -> getPredicateByCriteria(root, builder, criteria)).toArray(Predicate[]::new);

        return builder.or(predicates);
    }

    private <Y extends Comparable<? super Y>> Predicate getPredicateByCriteria(Root<T> root, CriteriaBuilder builder, SearchCriteria criteria) {

        Path<String> path = getPath(root, criteria.getKey(), null);
        Y objectValue = criteria.getObjectValue();
        Expression expression = objectValue != null ? path.as(objectValue.getClass()) : path.as(String.class);

        switch (criteria.getOperation()) {
            case GREATER_THAN:
                return objectValue != null ? builder.greaterThan(expression, objectValue) : builder.greaterThan(expression, criteria.getValue());
            case LESS_THAN:
                return objectValue != null ? builder.lessThan(expression, objectValue) : builder.lessThan(expression, criteria.getValue());
            case GREATER_THAN_EQUAL:
                return objectValue != null ? builder.greaterThanOrEqualTo(expression, objectValue) : builder.greaterThanOrEqualTo(expression, criteria.getValue());
            case LESS_THAN_EQUAL:
                return objectValue != null ? builder.lessThanOrEqualTo(expression, objectValue) : builder.lessThanOrEqualTo(expression, criteria.getValue());
            case NOT_EQUAL:
                return objectValue != null ? builder.notEqual(expression, objectValue) : builder.notEqual(expression, criteria.getValue());
            case EQUAL:
                return objectValue != null ? builder.equal(expression, objectValue) : builder.equal(expression, criteria.getValue());
            case MATCH:
                return builder.like(builder.lower(expression), "%" + criteria.getValue().toLowerCase() + "%");
            case MATCH_END:
                return builder.like(builder.lower(expression), criteria.getValue().toLowerCase() + "%");
        }

        throw new IllegalArgumentException("Not implemented yet :" + criteria.getOperation());
    }

    private Path<String> getPath(Root<T> root, String key,Path<String> path) {
        if (!key.contains(".") && path == null) {
            return root.get(key);
        }

        if (!key.contains(".")) {
            return path.get(key);
        }

        int dotIndex = key.indexOf(".");
        String now = key.substring(0, dotIndex);
        String next = key.substring(dotIndex + 1);

        return getPath(root, next, path == null ? root.get(now) : path.get(now));
    }
}
