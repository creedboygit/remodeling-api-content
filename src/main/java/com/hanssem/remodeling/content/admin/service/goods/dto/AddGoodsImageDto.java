package com.hanssem.remodeling.content.admin.service.goods.dto;

import java.time.LocalDateTime;
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
public class AddGoodsImageDto {

    private Long goodsId;
    private int imageSeq;

    private String imagePathName;
    private String imageDirectory;
    private String imageFile;

    private LocalDateTime systemCreateDateTime;
    private String systemConstructorId;
    private LocalDateTime systemUpdateDateTime;
    private String systemUpdaterId;
}
