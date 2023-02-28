package com.hanssem.remodeling.content.admin.service.goods.dto;


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
public class AddCategoryDto {

    private Long ID;
    private String Path_CtgNo;
    private String Path_CtgNm;
    private String CtgNm;
    private int CtgNo;
    private int LVL;
    private int DspOrder;
    private int CtgNo_L;
    private int CtgNo_M;
    private int CtgNo_S;
    private int CtgNo_D;
    private int CtgNm_L;
    private int CtgNm_M;
    private int CtgNm_S;
    private int CtgNm_D;
    private int CtgNo_UP;
    private int AsisCtgNo;

}
