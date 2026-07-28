package com.eop.baseservice.helper;

import com.eop.baseservice.common.annotation.SortProperty;
import com.eop.baseservice.common.response.SortBy;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor
public class SpecificationHelper {

    public static Sort createSort(Class<?> clazz, List<SortBy> sortBy) {
        if (sortBy == null || sortBy.isEmpty()) {
            return Sort.unsorted();
        }

        Map<String, Field> fieldMap = getAllBaseField(clazz).stream()
                .collect(Collectors.toMap(Field::getName, field -> field, (f1, f2) -> f1));

        List<Sort.Order> orders = new ArrayList<>();

        for (SortBy s : sortBy) {
            Field field = fieldMap.get(s.getPropertyName());
            if (field == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "sort property not exists");
            }

            SortProperty sortProperty = field.getDeclaredAnnotation(SortProperty.class);
            String targetProperty = (sortProperty != null && !sortProperty.entityProperty().isEmpty())
                    ? sortProperty.entityProperty()
                    : s.getPropertyName();

            Sort.Direction direction = Sort.Direction.fromString(s.getDirection().name());
            orders.add(new Sort.Order(direction, targetProperty));
        }

        return Sort.by(orders);
    }

    public static <T> Specification<T> parameterFilterIn(String column, List<?> values) {
        return ((root, query, cb) -> {
            Path<T> path = getRootPath(column, root);
            return path.in(values);
        });
    }

    public static <T> Specification<T> inquiryFilter(List<String> columns, String inquiry) {
        return ((root, query, cb) -> {
            List<Expression<String>> concatColumns = new ArrayList<>();
            concatColumns.add(cb.<String>literal(""));
            for (String column : columns) {
                Path<T> path = getRootPath(column, root);
                concatColumns.add(path.as(String.class));
            }
            Expression<String> newColumn = cb.function(
                    "concat_ws", String.class, concatColumns.toArray(new Expression[0]));
            return cb.like(cb.lower(newColumn), "%|" + inquiry.toLowerCase() + "%", '|');
        });
    }

    public static <T> Specification<T> filter(String inquiry, Boolean isActive, String... columns) {
        Specification<T> spec = Specification.where(null);
        if (inquiry != null && !inquiry.isEmpty() && columns != null && columns.length > 0) {
            spec = spec.and(inquiryFilter(Arrays.asList(columns), inquiry));
        }
        if (isActive != null) {
            spec = spec.and(parameterFilter("isActive", isActive));
        }
        return spec;
    }

    public static <T> Specification<T> parameterFilter(String column, Object value) {
        return ((root, query, cb) -> {
            Path<T> path = getRootPath(column, root);
            return cb.equal(path, value);
        });
    }

    public static <T> Path<T> getRootPath(String column, Root<T> root) {
        if (!column.contains(".")) {
            return root.get(column);
        }
        String joinColumn = StringUtils.substringBefore(column, ".");
        column = StringUtils.substringAfter(column, ".");
        Join<T, ?> path = getJoin(root, joinColumn);
        while (true) {
            if (column.contains(".")) {
                joinColumn = StringUtils.substringBefore(column, ".");
                column = StringUtils.substringAfter(column, ".");
                path = getJoin(path, joinColumn);
            } else {
                return path.get(column);
            }
        }
    }

    private static List<Field> getAllBaseField(Class<?> entity) {
        Class<?> base = entity;
        List<Field> list = new ArrayList<>();
        list.addAll(Arrays.asList(base.getDeclaredFields()));

        boolean isBreak = false;
        while (!isBreak) {
            try {
                base = base.getSuperclass();
                list.addAll(Arrays.asList(base.getDeclaredFields()));
            } catch (Exception e) {
                isBreak = true;
            }
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    private static <T, Y> Join<T, Y> getJoin(Root<T> root, String column) {
        return (Join<T, Y>) root.getJoins().stream()
                .filter(
                        r -> r.getAttribute().getName().equals(column) && r.getJoinType().equals(JoinType.LEFT))
                .findFirst().orElseGet(() -> root.join(column, JoinType.LEFT));
    }

    @SuppressWarnings("unchecked")
    private static <T, Y> Join<T, Y> getJoin(Join<T, Y> root, String column) {
        return (Join<T, Y>) root.getJoins().stream()
                .filter(
                        r -> r.getAttribute().getName().equals(column) && r.getJoinType().equals(JoinType.LEFT))
                .findFirst().orElseGet(() -> root.join(column, JoinType.LEFT));
    }
}
