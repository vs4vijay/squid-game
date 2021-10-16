package com.vs4vijay.squidgame.models;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseModel {
    @Id
    @GeneratedValue(generator = "UUID")
    UUID id;

    Boolean isActive;

    @CreatedDate
    Date createdAt;

    @LastModifiedDate
    Date updatedAt;

    @CreatedBy
    String createdBy;

    @LastModifiedBy
    String updatedBy;
}
