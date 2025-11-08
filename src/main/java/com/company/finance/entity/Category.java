package com.company.finance.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

@JmixEntity
@Table(name = "CATEGORY")
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
    @InstanceName
    @Column(name = "NAME", nullable = false)
    @NotNull
    private String name;

    public OperationType getType() {
        return type == null ? null : OperationType.fromId(type);
    }

    public void setType(OperationType type) {
        this.type = type == null ? null : type.getId();
    }

    public BigDecimal getLimit() {
        return limit;
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

}