package com.hanssem.remodeling.content.api.service.event.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class HsMallPagingMapVo {

    private String curPage;
    private String totalRecordCount;
    private String pageCount;
    private String pageRow;

}
