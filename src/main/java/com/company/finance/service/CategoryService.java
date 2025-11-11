package com.company.finance.service;

import com.company.finance.entity.Category;
import com.company.finance.entity.CategoryGridData;
import com.company.finance.entity.Operation;
import com.company.finance.entity.OperationType;
import io.jmix.core.DataManager;
import io.jmix.core.FluentLoader;
import io.jmix.core.UnconstrainedDataManager;
import io.jmix.core.entity.KeyValueEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService {
    @Autowired
    private DataManager dataManager;

    @Autowired
    private UnconstrainedDataManager unconstrainedDataManager;




    public Map<String, BigDecimal> getLimitLeftover(Category category){

        BigDecimal amount = dataManager.loadValue(
                        "select coalesce(sum(o.amount), 0) from Operation o where o.category = :category",
                        BigDecimal.class)
                .parameter("category", category)
                .one();

        BigDecimal leftover = new BigDecimal(0);
        if (!category.getLimit().equals(new BigDecimal(0))){
            leftover = category.getLimit().subtract(amount);
        }

        Map<String, BigDecimal> result = new HashMap<>();

        result.put("amount", amount);
        result.put("leftover", leftover);


        return result;
    }




    public List<CategoryGridData> getCategories(OperationType type, LocalDate from, LocalDate through) {
        StringBuilder query = new StringBuilder(
                "select o.category, coalesce(o.category.limit, 0), coalesce(sum(o.amount), 0), coalesce(o.category.limit, 0) - coalesce(sum(o.amount), 0) " +
                        "from Operation o where o.category.type = :type"
        );

        if (from != null && through != null) {
            query.append(" and o.date >= :from and o.date <= :through");
        } else if (from != null) {
            query.append(" and o.date >= :from");
        } else if (through != null) {
            query.append(" and o.date <= :through");
        }

        query.append(" group by o.category, o.category.limit");

        var loader = unconstrainedDataManager.loadValues(query.toString())
                .properties("category", "limit", "amount", "leftover")
                .parameter("type", type.getId());

        if (from != null) {
            loader.parameter("from", from);
        }
        if (through != null) {
            loader.parameter("through", through);
        }

        List<KeyValueEntity> results = loader.list();

        List<CategoryGridData> data = new ArrayList<>();
        for (KeyValueEntity entity : results) {
            Category category = entity.getValue("category");
            BigDecimal limit = entity.getValue("limit");
            BigDecimal amount = entity.getValue("amount");
            BigDecimal leftover = entity.getValue("leftover");
            data.add(new CategoryGridData(category, limit, amount, leftover));
        }
        return data;
    }
}
