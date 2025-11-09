package com.company.finance.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedBy;

import java.math.BigDecimal;
import java.util.UUID;

@JmixEntity
@Table(name = "TRANSFER", indexes = {
        @Index(name = "IDX_TRANSFER_FROM", columnList = "FROM_ID"),
        @Index(name = "IDX_TRANSFER_TO", columnList = "TO_ID"),
        @Index(name = "IDX_TRANSFER_USER", columnList = "USER_ID")
})
@Entity
public class Transfer {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @CreatedBy
    @JoinColumn(name = "USER_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @JoinColumn(name = "FROM_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Wallet from;

//    @Column(name = "TO_", nullable = false)
//    @NotNull
//    private UUID to;

    @JoinColumn(name = "TO_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Wallet to;

    @Column(name = "AMOUNT", nullable = false, precision = 19, scale = 2)
    @NotNull
    private BigDecimal amount;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

//    public void setTo(UUID to) {
//        this.to = to;
//    }
//
//    public UUID getTo() {
//        return to;
//    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Wallet getFrom() {
        return from;
    }

    public void setFrom(Wallet from) {
        this.from = from;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Wallet getTo() {
        return to;
    }

    public void setTo(Wallet to) {
        this.to = to;
    }


}