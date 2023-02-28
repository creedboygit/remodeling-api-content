package com.hanssem.remodeling.content.domain.goods.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "tb_goods_keyword_d")
public class GoodsKeywordImport implements Serializable {

    @Id
    @Column(name = "KEYWORD_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "GOODS_ID")
    private Long goodsId;

    @Column(name = "KEYWORD_TYPE_CODE")
    private String keywordType;

    @Column(name = "KEYWORD_SEQ")
    private int keywordSeq;

    @Column(name = "KEYWORD_NAME")
    private String keyword;

    @Column(name = "SYSTEM_CREATE_DATETIME")
    @CreatedDate
    private LocalDateTime systemCreateDateTime;

    @Column(name = "SYSTEM_CONSTRUCTOR_ID")
    private String systemConstructorId;

    @Column(name= "SYSTEM_UPDATE_DATETIME")
    @LastModifiedDate
    private LocalDateTime systemUpdateDateTime;

    @Column(name = "SYSTEM_UPDATER_ID")
    private String systemUpdaterId;
}
