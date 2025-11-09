package com.company.finance.service;

import com.company.finance.entity.Category;
import com.company.finance.entity.Operation;
import com.company.finance.entity.OperationType;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;

@Service
public class CategoryService {
    @Autowired
    private DataManager dataManager;

    public BigDecimal getLimitLeftover(Category category){

        Assert.isTrue (category.getType() == OperationType.РАСХОД, "Должен быть расход");

        BigDecimal spent = dataManager.loadValue(
                        "select coalesce(sum(o.amount), 0) from Operation o where o.category = :category",
                        BigDecimal.class)
                .parameter("category", category)
                .one();

        BigDecimal leftover = category.getLimit().subtract(spent);

        return leftover;
    }
}
