package com.company.finance.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.company.finance.entity.Category;
import com.company.finance.entity.OperationType;
import com.company.finance.entity.User;
import com.company.finance.test_support.AuthenticatedAsUser;
import io.jmix.core.DataManager;
import io.jmix.core.security.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

/** Sample integration test for the User entity. */
@SpringBootTest
@ExtendWith(AuthenticatedAsUser.class)
public class UserTest {

  @Autowired DataManager dataManager;

  @Autowired PasswordEncoder passwordEncoder;

  @Autowired UserRepository userRepository;

  User savedUser;

  // У пользователя user должна быть категория "Перевод между кошельками" с типом операции "Приход".

  @Test
  void test_TransferCategoryincome() {
    Category cat =
        dataManager
            .load(Category.class)
            .query(
                "select c from Category c where "
                    + "c.user.username = :username and "
                    + "c.name = :name and "
                    + "c.type = :type")
            .parameter("username", "user")
            .parameter("name", "Перевод между кошельками")
            .parameter("type", OperationType.ПРИХОД.getId())
            .one();
    assertThat(cat).isNotNull();
  }

  // У пользователя user должна быть категория "Перевод между кошельками" с типом операции "Расход".
  @Test
  void test_T1ransferCategoryExpense() {
    Category cat =
        dataManager
            .load(Category.class)
            .query(
                "select c from Category c where "
                    + "c.user.username = :username and "
                    + "c.name = :name and "
                    + "c.type = :type")
            .parameter("username", "user")
            .parameter("name", "Перевод между кошельками")
            .parameter("type", OperationType.РАСХОД.getId())
            .one();
    assertThat(cat).isNotNull();
  }

  @Test
  void test_OperationTypeIncomeExists() {
    // Проверяем, что enum содержит значение с id "Income"
    OperationType type = OperationType.fromId("Income");
    assertThat(type).isNotNull();
    // Можно проверить имя, если нужно
    assertThat(type).isEqualTo(OperationType.ПРИХОД);
  }

  @Test
  void test_OperationTypeExpenseExists() {
    // Проверяем, что enum содержит значение с id "Expense"
    OperationType type = OperationType.fromId("Expense");
    assertThat(type).isNotNull();
    // Можно проверить имя, если нужно
    assertThat(type).isEqualTo(OperationType.РАСХОД);
  }

  @AfterEach
  void tearDown() {
    if (savedUser != null) {
      dataManager.remove(savedUser);
    }
  }
}
