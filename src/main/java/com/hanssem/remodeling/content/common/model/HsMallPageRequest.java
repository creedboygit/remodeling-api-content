package com.hanssem.remodeling.content.common.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Optional;
import javax.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


/*
 * HanssaemMallClient 의 paging 처리시, page, size 파라메터가 String 인 경우 처리 목적.
 */
@Getter
@Setter
@ToString
public class HsMallPageRequest {

    @Schema(description = "page > 0", type = "integer", example = "1")
    @Min(value = 1, message = ": page는 0 보다 커야 합니다.")
    private Integer page;

    @Schema(description = "size > 0", type = "integer", example = "10")
    @Min(value = 1, message = ": size는 0 보다 커야 합니다.")
    private Integer size;

    @Schema(hidden = true)
    private String pageStr;

    @Schema(hidden = true)
    private String sizeStr;


    public HsMallPageRequest(Integer page, Integer size) {

        this.page = Optional.ofNullable(page).orElse(1);
        this.size = Optional.ofNullable(size).orElse(30);
        this.pageStr = String.valueOf(this.page);
        this.sizeStr = String.valueOf(this.size);
    }

    public Pageable getPageable() {

        return PageRequest.of(page - 1, size);
    }
}
