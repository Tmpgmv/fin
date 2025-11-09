
package com.company.finance.service;

import com.company.finance.entity.OperationType;
import com.company.finance.entity.Wallet;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WalletService {
    @Autowired
    private DataManager dataManager;


    public BigDecimal getWalletAmount(Wallet wallet) {

        BigDecimal amount = dataManager.loadValue(
                        "select coalesce(sum(" +
                                "case when c.type = :incomeType then o.amount " +
                                "     when c.type = :expenseType then -o.amount " +
                                "     else 0 end), 0) " +
                                "from Operation o join o.category c " +
                                "where o.wallet = :wallet",
                        BigDecimal.class)
                .parameter("wallet", wallet)
                .parameter("incomeType", OperationType.ПРИХОД)
                .parameter("expenseType", OperationType.РАСХОД)
                .one();
        return amount != null ? amount : BigDecimal.ZERO;
    }
}