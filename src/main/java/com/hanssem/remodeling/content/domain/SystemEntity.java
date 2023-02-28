package com.hanssem.remodeling.content.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class SystemEntity {

    @Column(name = "SYSTEM_CREATE_DATETIME")
    @CreatedDate
    private LocalDateTime systemCreateDateTime;

    @Column(name = "SYSTEM_CONSTRUCTOR_ID")
    private String systemConstructorId;

    @Column(name = "SYSTEM_UPDATE_DATETIME")
    @LastModifiedDate
    private LocalDateTime systemUpdateDatetime;

    @Column(name = "SYSTEM_UPDATER_ID")
    private String systemUpdaterId;

    public void setSystemConstructorId(String systemConstructorId) {
        if (Objects.isNull(systemConstructorId)) { return; }
        this.systemConstructorId = systemConstructorId;
    }

    public void setSystemUpdaterId(String systemUpdaterId) {
        if (Objects.isNull(systemUpdaterId)) { return; }
        this.systemUpdaterId = systemUpdaterId;
    }
}
