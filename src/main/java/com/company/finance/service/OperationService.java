package com.company.finance.service;

import com.company.finance.entity.Operation;
import com.company.finance.entity.OperationType;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OperationService {
    @Autowired
    private DataManager dataManager;

    public BigDecimal geTotal(OperationType type){
        BigDecimal total = dataManager.loadValue(
                        "select coalesce(sum(o.amount), 0) from Operation o where o.category.type = :type",
                        BigDecimal.class)
                .parameter("type", type)
                .one();

        return total;
    }

}
