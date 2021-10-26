package com.vs4vijay.squidgame.models;

import lombok.Builder;
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

    public static final String SOFT_DELETE_CLAUSE = "is_deleted = false";

    @Id
    String id;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    Date createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    Date updatedAt;

    @CreatedBy
    @Column(updatable = false)
    String createdBy;

    @LastModifiedBy
    @Column()
    String updatedBy;

    @Column(updatable = false)
    Boolean isDeleted;

    @PrePersist
    private void onBaseModelCreate() {
        if(id == null) {
            // TODO: Find a better way to use String data type while storing UUID
            id = UUID.randomUUID().toString();
        }
        isDeleted = false;
    }
}
