package com.hanssem.remodeling.content.common.repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.NullExpression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import java.util.Collection;
import java.util.List;

public abstract class CustomRepository {
    private static OrderSpecifier orderByNull = new OrderSpecifier(Order.ASC, NullExpression.DEFAULT, OrderSpecifier.NullHandling.Default);

    protected final BooleanExpression isNullContains(String value, Path expression) {
        return value == null || value.isEmpty() ? null : ((StringPath) expression).contains(value);
    }

    protected final BooleanExpression isNullLike(String value, Path expression) {
        if (value == null || value.trim().isEmpty()) return null;
        return value == null ? null : ((StringPath) expression).like(value + '%');
    }

    protected final BooleanExpression isNullEq(String value, Path expression) {
        return value == null ? null : ((StringPath) expression).eq(value);
    }

    protected final BooleanExpression isNullEq(Long value, Path expression) {
        return value == null || value == 0 ? null : ((NumberPath<Long>) expression).eq(value);
    }

    protected final BooleanExpression isNullEq(Integer value, Path expression) {
        return value == null || value == 0 ? null : ((NumberPath<Integer>) expression).eq(value);
    }

    protected final BooleanExpression isNullIn(List<String> values, Path expression) {
        return values == null ? null : ((StringPath) expression).in(values);
    }

    protected final BooleanExpression isNullNe(String value, Path expression) {
        return value == null ? null : ((StringPath) expression).ne(value);
    }

    protected final BooleanExpression isNullNe(Enum value, Path expression) {
        return value == null ? null : ((EnumPath) expression).ne(value);
    }

    protected final BooleanExpression isNullEq(Enum value, Path expression) {
        return value == null ? null : ((EnumPath) expression).eq(value);
    }

    protected final BooleanExpression isNullEnum(Collection<?> values, Path expression) {
        return values == null ? null : ((EnumPath) expression).eq(values);
    }

    protected final BooleanExpression isNullEnumIn(Collection<?> values, Path expression) {
        return values == null ? null : ((EnumPath) expression).in(values);
    }


    protected final BooleanExpression isNullIn(Collection<?> values, Path expression) {
        return values == null ? null : ((NumberPath) expression).in(values);
    }

    protected final BooleanExpression isNullNe(Long value, Path expression) {
        return value == null || value == 0 ? null : ((NumberPath<Long>) expression).ne(value);
    }

    protected final BooleanExpression isTrueNe(boolean condition, Long value, Path expression) {
        return !condition ? null : isNullNe(value, expression);
    }

    protected OrderSpecifier orderByNull() {
        return orderByNull;
    }

    protected OrderSpecifier [] getDefaultOrderSpecifiers(Order order, String target) {
        OrderSpecifier [] orders = new OrderSpecifier[1];
        orders[0] = new OrderSpecifier(order, ExpressionUtils.path(String.class, target));
        return orders;
    }
}