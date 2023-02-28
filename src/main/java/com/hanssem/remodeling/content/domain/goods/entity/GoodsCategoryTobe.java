package com.hanssem.remodeling.content.domain.goods.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_category_tobe")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GoodsCategoryTobe {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Path_CtgNo")
    private String path_CtgNo;

    @Column(name = "Path_CtgNm")
    private String path_CtgNm;

    @Column(name = "CtgNm")
    private String ctgNm;

    @Column(name = "CtgNo")
    private int ctgNo;

    @Column(name = "LVL")
    private int lvl;

    @Column(name = "DspOrder")
    private int dspOrder;

    @Column(name = "CtgNo_L")
    private int ctgNo_L;

    @Column(name = "CtgNo_M")
    private int ctgNo_M;

    @Column(name = "CtgNo_S")
    private int ctgNo_S;

    @Column(name = "CtgNo_D")
    private int ctgNo_D;

    @Column(name = "CtgNm_L")
    private String ctgNm_L;

    @Column(name = "CtgNm_M")
    private String ctgNm_M;

    @Column(name = "CtgNm_S")
    private String ctgNm_S;

    @Column(name = "CtgNm_D")
    private String ctgNm_D;

    @Column(name = "CtgNo_UP")
    private int ctgNo_UP;

}
