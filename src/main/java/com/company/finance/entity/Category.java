package com.company.finance.entity;

import io.jmix.core.MetadataTools;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.annotation.CreatedBy;

import java.math.BigDecimal;
import java.util.UUID;

@JmixEntity
@Table(name = "CATEGORY", indexes = {
        @Index(name = "IDX_CATEGORY_USER", columnList = "USER_ID")
}, uniqueConstraints = {
        @UniqueConstraint(name = "IDX_CATEGORY_UNQ_USER_NAME", columnNames = {"USER_ID", "NAME", "TYPE_"})
})
@Entity
public class Category {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Positive
    @Column(name = "LIMIT_", precision = 19, scale = 2)
    private BigDecimal limit;

    @Column(name = "TYPE_", nullable = false)
    @NotNull
    private String type;

    @NotBlank
    @NotEmpty
    @Column(name = "NAME", nullable = false)
    @NotNull
    private String name;

    @CreatedBy
    @JoinColumn(name = "USER_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OperationType getType() {
        return type == null ? null : OperationType.fromId(type);
    }

    public void setType(OperationType type) {
        this.type = type == null ? null : type.getId();
    }

    public BigDecimal getLimit() {
        return limit == null ? new BigDecimal(0) : limit;
    }

    public void setLimit(BigDecimal limit) {
        this.limit = limit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @InstanceName
    @DependsOnProperties({"name"})
    public String getInstanceName(MetadataTools metadataTools) {
        return metadataTools.format(name);
    }
}