package com.hanssem.remodeling.content.domain.goods.entity;


import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity
@ToString
@Table(name = "tb_goods_category_d")
public class GoodsCategoryImport implements Comparable<GoodsCategoryImport> {

    @Id
    @Column(name = "GOODS_CATEGORY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "GOODS_ID")
    private Long goodsId;

    @Column(name = "CATEGORY_NO")
    private int categoryNo;

    @Column(name = "CATEGORY_LRCL_NO")
    private int categoryLargeNo;

    @Column(name = "CATEGORY_MDCL_NO")
    private int categoryMiddleNo;

    @Column(name = "CATEGORY_SMCL_NO")
    private int categorySmallNo;

    @Column(name = "CATEGORY_TNCL_NO")
    private int categoryTinyNo;

    @Column(name = "CATEGORY_LRCL_NAME")
    private String categoryLargeName;

    @Column(name = "CATEGORY_MDCL_NAME")
    private String categoryMiddleName;

    @Column(name = "CATEGORY_SMCL_NAME")
    private String categorySmallName;

    @Column(name = "CATEGORY_TNCL_NAME")
    private String categoryTinyName;

    @Column(name = "CATEGORY_DISPLAY_SEQ")
    private int categoryDisplaySeq;

    @Column(name = "SYSTEM_CREATE_DATETIME")
//    @CreatedDate
    private LocalDateTime systemCreateDateTime;

    @Column(name = "SYSTEM_CONSTRUCTOR_ID")
    private String systemConstructorId;

    @Column(name= "SYSTEM_UPDATE_DATETIME")
//    @LastModifiedDate
    private LocalDateTime systemUpdateDateTime;

    @Column(name = "SYSTEM_UPDATER_ID")
    private String systemUpdaterId;


    @Override
    public int compareTo(@NotNull GoodsCategoryImport o) {
        if (this.categoryLargeNo > o.categoryLargeNo) {
            return 1;
        } else if (this.categoryLargeNo < o.categoryLargeNo) {
            return -1;
        } else {
            return 0;
        }
    }
}
