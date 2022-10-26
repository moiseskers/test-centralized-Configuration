package com.gingapay.templateapplication.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Data
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 330465065254007913L;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        ZoneId zId = ZoneId.of("America/Sao_Paulo");
        ZonedDateTime now = ZonedDateTime.now(zId);

        this.setCreatedAt(now);
        this.setUpdatedAt(now);
    }

    @PreUpdate
    public void preUpdate() {
        ZoneId zid = ZoneId.of("America/Sao_Paulo");
        ZonedDateTime now = ZonedDateTime.now(zid);

        this.setUpdatedAt(now);
    }

}
