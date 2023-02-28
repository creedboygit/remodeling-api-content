package com.hanssem.remodeling.content.common.model;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * PaginationRequest와 나눠진 이유는 Sort의 자유로운 조작때문에 Injection의 가능성을 배제하기 위하여
 * Api스펙에서 Sorting을 해야하는 경우에만 Sort를 받아서 처리하고, 그 외에는 설정을 할 수 없도록한다.
 */
@Getter
@ToString
public class PaginationRequest implements Serializable {

    @Schema(name = "page", title = "요청할 페이지", type = "java.lang.Integer", example = "1")
    private int page;

    @Schema(name = "size", title = "요청할 데이터수량(최대50)", type = "java.lang.Integer", example = "50")
    private int size;

    @Schema(name = "sort", title = "정렬타입(sort=id,desc&sort=title,asc(생략가능))", type = "String", example = "")
    private final List<Sort.Order> sorts;

    protected PaginationRequest(int page, int size, List<Sort.Order> sorts) {
        this.page = page;
        this.size = size;
        this.sorts = sorts;
    }

    public static PaginationRequest of(int page, int size, List<Sort.Order> sorts) {
        return new PaginationRequest(page, size, Collections.emptyList());
    }
    @Schema(hidden = true)
    public Pageable getPageable() {
        return PageRequest.of(page, size, Sort.unsorted());
    }
    public Pageable getPageable(List<Sort.Order> sorts) {
        return PageRequest.of(page, size, Sort.by(sorts));
    }
    public Pageable getPageable(Sort.Order sort) {
        return PageRequest.of(page, size, Sort.by(List.of(sort)));
    }

    @Schema(hidden = true)
    public OrderSpecifier[] getOrderSpecifiers() {
        OrderSpecifier [] orders = new OrderSpecifier[sorts.size()];
        int i = 0;
        for (Sort.Order order : sorts) {
            orders[i++] = new OrderSpecifier(Order.valueOf(order.getDirection().toString()), ExpressionUtils.path(String.class, order.getProperty()));
        }
        return orders;
    }

    public void modifyPageAndSize(int page, int size) {
        this.page = page;
        this.size = size;
    }

}
