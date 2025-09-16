package com.multiGenesys.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedBy
    @Column(nullable = false, updatable = false)
    @JsonIgnore
    private String createdBy;

    @LastModifiedBy
    @Column(nullable = false)
    @JsonIgnore
    private String lastModifiedBy;

    @CreatedDate
    @Column(updatable = false)
    @JsonIgnore
    private LocalDateTime createdDate;

    @LastModifiedDate
    @JsonIgnore
    private LocalDateTime lastModifiedDate;
}