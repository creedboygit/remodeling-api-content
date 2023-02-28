package com.hanssem.remodeling.content.common.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

/**
 * 많이 안쓰일 JSON Properties 제거
 *
 * @param <T>
 */

@JsonIgnoreProperties(value = {"pageable", "sort", "number"})
public class PageResponse<T> extends PageImpl {

    @Schema(title = "데이터 존재 유무", type = "java.lang.Boolean", example = "true|false")
    private boolean empty;
    @Schema(title = "첫 페이지 여부", type = "java.lang.Boolean", example = "true|false")
    private boolean first;
    @Schema(title = "마지막 페이지 여부", type = "java.lang.Boolean", example = "true|false")
    private boolean last;
    @Schema(title = "현 페이지 데이터수량", type = "java.lang.Integer", example = "50")
    private int numberOfElements;
    @Schema(title = "페이지의 크기", type = "java.lang.Integer", example = "50")
    private int size;
    @Schema(title = "총 데이터수량", type = "java.lang.Long", example = "50")
    private long totalElements;
    @Schema(title = "총 페이지 수 ", type = "java.lang.Integer", example = "50")
    private int totalPages;

    private PageResponse() {
        super(Collections.emptyList(), PageRequest.of(1, 50), 0);
    }

    private PageResponse(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public static PageResponse of(List<?> content, Pageable pageable, long total) {
        return new PageResponse(content, pageable, total);
    }

    public static PageResponse of(Page page) {
        return new PageResponse(page.getContent(), page.getPageable(), page.getTotalElements());
    }

    public static PageResponse emptyResponse() {
        return new PageResponse();
    }

}
