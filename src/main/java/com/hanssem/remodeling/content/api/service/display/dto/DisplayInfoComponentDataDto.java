package com.hanssem.remodeling.content.api.service.display.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DisplayInfoComponentDataDto {

    private String dispComponentNo;
    private String componentLayoutCd;

    List<Object> componentDataList = new ArrayList<>();

}
