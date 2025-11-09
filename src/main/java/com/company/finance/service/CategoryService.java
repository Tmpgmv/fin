package com.company.finance.service;

import com.company.finance.entity.Category;
import com.company.finance.entity.CategoryGridData;
import com.company.finance.entity.Operation;
import com.company.finance.entity.OperationType;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService {
    @Autowired
    private DataManager dataManager;

    public Map<String, BigDecimal> getLimitLeftover(Category category){

        BigDecimal amount = dataManager.loadValue(
                        "select coalesce(sum(o.amount), 0) from Operation o where o.category = :category",
                        BigDecimal.class)
                .parameter("category", category)
                .one();

        BigDecimal leftover = category.getLimit().subtract(amount);

        Map<String, BigDecimal> result = new HashMap<>();

        result.put("amount", amount);
        result.put("leftover", leftover);


        return result;
    }




    public List<CategoryGridData> getCategories(OperationType type){
        List<Category> categoryList = dataManager.load(Category.class).query("select c from Category c where c.type = :type").parameter("type", type.getId()).list();

        List<CategoryGridData> result = categoryList.stream().map(c->new CategoryGridData(c, c.getLimit(),
                getLimitLeftover(c).get("amount"),
                getLimitLeftover(c).get("leftover"))).toList();
        return result;
    }
}
