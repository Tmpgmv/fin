package com.company.finance.entity;

import io.jmix.core.metamodel.datatype.EnumClass;
import org.springframework.lang.Nullable;

public enum OperationType implements EnumClass<String> {
  ПРИХОД("Income"),
  РАСХОД("Expense");

  private final String id;

  OperationType(String id) {
    this.id = id;
  }

  @Nullable
  public static OperationType fromId(String id) {
    for (OperationType at : OperationType.values()) {
      if (at.getId().equals(id)) {
        return at;
      }
    }
    return null;
  }

  public String getId() {
    return id;
  }
}
