package com.hanssem.remodeling.content.api.controller.category.response;


import lombok.*;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MallCategoryListDetailResponse implements Serializable {

    private List<MallCategoryListDetailInfoResponse> categoryList;
}
