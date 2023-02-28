package com.hanssem.remodeling.content.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @Column(name = "SYSTEM_CREATE_DATETIME")
    @CreatedDate
    private LocalDateTime systemCreateDatetime;

    @Column(name = "SYSTEM_UPDATE_DATETIME")
    @LastModifiedDate
    private LocalDateTime systemUpdateDatetime;
}
