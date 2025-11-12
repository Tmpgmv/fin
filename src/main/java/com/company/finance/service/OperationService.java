package com.company.finance.service;

import com.company.finance.entity.OperationType;
import com.company.finance.entity.User;
import io.jmix.core.DataManager;
import io.jmix.core.security.CurrentAuthentication;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperationService {
  @Autowired private DataManager dataManager;

  @Autowired private CurrentAuthentication currentAuthentication;

  public BigDecimal geTotal(OperationType type, LocalDate from, LocalDate through) {
    User currentUser = (User) currentAuthentication.getUser();
    StringBuilder query =
        new StringBuilder(
            "select coalesce(sum(o.amount), 0) from Operation o where o.category.type = :type and o.wallet.user = :user");
    if (from != null && through != null) {
      query.append(" and o.date >= :from and o.date <= :through");
    } else if (from != null) {
      query.append(" and o.date >= :from");
    } else if (through != null) {
      query.append(" and o.date <= :through");
    }

    var total =
        dataManager
            .loadValue(query.toString(), BigDecimal.class)
            .parameter("type", type)
            .parameter("user", currentUser);
    if (from != null) {
      total.parameter("from", from);
    }
    if (through != null) {
      total.parameter("through", through);
    }

    return total.one();
  }
}
