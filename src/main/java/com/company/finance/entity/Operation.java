package com.company.finance.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import org.springframework.data.annotation.CreatedDate;

@JmixEntity
@Table(
    name = "OPERATION",
    indexes = {
      @Index(name = "IDX_OPERATION_WALLET", columnList = "WALLET_ID"),
      @Index(name = "IDX_OPERATION_CATEGORY", columnList = "CATEGORY_ID")
    })
@Entity
public class Operation {
  @JmixGeneratedValue
  @Column(name = "ID", nullable = false)
  @Id
  private UUID id;

  @JoinColumn(name = "WALLET_ID", nullable = false)
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  private Wallet wallet;

  @JoinColumn(name = "CATEGORY_ID", nullable = false)
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  private Category category;

  @Positive
  @Column(name = "AMOUNT", nullable = false, precision = 19, scale = 2)
  @NotNull
  private BigDecimal amount;

  @InstanceName
  @Column(name = "DESCRIPTION")
  private String description;

  @CreatedDate
  @Column(name = "DATE_")
  private LocalDate date;

  @Lob
  @Column(name = "COMMENT_")
  private String comment;

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  //    @Column(name = "TYPE")
  @JmixProperty
  @DependsOnProperties({"category"})
  public OperationType getType() {
    return category.getType();
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public Wallet getWallet() {
    return wallet;
  }

  public void setWallet(Wallet wallet) {
    this.wallet = wallet;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }
}
