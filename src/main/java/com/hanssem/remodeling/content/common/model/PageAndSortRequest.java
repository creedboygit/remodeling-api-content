package com.hanssem.remodeling.content.common.model;

import lombok.ToString;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * PaginationRequest와 나눠진 이유는 Sort의 자유로운 조작때문에 Injection의 가능성을 배제하기 위하여
 * Api스펙에서 Sorting을 해야하는 경우에만 Sort를 받아서 처리하고, 그 외에는 설정을 할 수 없도록한다.
 */
@ToString
public class PageAndSortRequest extends PaginationRequest {

    protected PageAndSortRequest(int page, int size, List<Sort.Order> sorts) {
        super(page, size, sorts);
    }

    public static PageAndSortRequest of(int page, int size, List<Sort.Order> sorts){
        return new PageAndSortRequest(page, size, sorts);
    }

    @Override
    public Pageable getPageable() {
        if (getSorts() != null && getSorts().size() > 0)
            return PageRequest.of(getPage(), getSize(), Sort.by(getSorts()));
        else
            return PageRequest.of(getPage(), getSize());
    }

    public void modifyPageAndSize(int page, int size) {
        super.modifyPageAndSize(page, size);
    }
}
