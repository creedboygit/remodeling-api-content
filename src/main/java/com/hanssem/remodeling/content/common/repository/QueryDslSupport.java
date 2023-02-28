package com.hanssem.remodeling.content.common.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.BooleanPath;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.DslExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import org.springframework.util.CollectionUtils;

@SuppressWarnings("unchecked")
public interface QueryDslSupport {

    default <T extends DslExpression<?>> T as(T path, String alias) {
        return (T) path.as(alias);
    }

    default <T extends SimpleExpression> BooleanExpression eq(T path, T value) {
        return path.eq(value);
    }

    default <T extends NumberPath<Long>, V extends Number & Comparable<?>> BooleanExpression lt(T path, V value) {
        return Objects.isNull(value) || value.equals(0L) ? null : path.lt(value);
    }

    default <T extends SimpleExpression, V> BooleanExpression eq(T path, V value) {
        return Objects.isNull(value) || value.equals(0L) ? null : path.eq(value);
    }

    default <T extends SimpleExpression, V> BooleanExpression ne(T path, V value) {
        return Objects.isNull(value) || value.equals(0L) ? null : path.ne(value);
    }

    default <T extends SimpleExpression, V extends List> BooleanExpression in(T path, V value) {
        return CollectionUtils.isEmpty(value) ? null : path.in(value);
    }

    default BooleanExpression between(DateTimePath<LocalDateTime> path, LocalDateTime start, LocalDateTime end) {
        return Objects.isNull(start) || Objects.isNull(end)  ? null : path.between(start, end);
    }

    default <T extends ComparableExpressionBase<? extends Comparable<?>>, R extends OrderSpecifier<? extends Comparable<?>>> R asc(T path) {
        return (R) path.asc();
    }

    default OrderSpecifier<Long> desc(NumberPath<Long> path) {
        return path.desc();
    }

    default BooleanExpression isFalse(BooleanPath path) {
        return path.isFalse();
    }

    default BooleanExpression isTrue(BooleanPath path) {
        return path.isTrue();
    }

    default StringExpression caseWhenThen(Predicate a, StringExpression b, StringPath c, String alias) {
        return new CaseBuilder().when(a).then(b).otherwise(c).as(alias);
    }
}
