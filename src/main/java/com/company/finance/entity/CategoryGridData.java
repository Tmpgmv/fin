package com.company.finance.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.JmixId;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

@JmixEntity
public class CategoryGridData {

    @JmixId
    private UUID id;

    @JmixProperty(mandatory = true)
    @NotNull
    private Category category;

    @JmixProperty(mandatory = true)
    @NotNull
    private BigDecimal amount;

    @JmixProperty(mandatory = true)
    @NotNull
    private BigDecimal limit;

    @JmixProperty(mandatory = true)
    @NotNull
    private BigDecimal leftover;

    public CategoryGridData(Category category,
                            BigDecimal limit,
                            BigDecimal amount,
                            BigDecimal leftover) {
        this.id = UUID.randomUUID();
        this.category = category;
        this.limit = limit;
        this.amount = amount;
        this.leftover = leftover;
    }

    public BigDecimal getLeftover() {
        return leftover;
    }

    public void setLeftover(BigDecimal leftover) {
        this.leftover = leftover;
    }

    public BigDecimal getLimit() {
        return limit;
    }

    public void setLimit(BigDecimal limit) {
        this.limit = limit;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}