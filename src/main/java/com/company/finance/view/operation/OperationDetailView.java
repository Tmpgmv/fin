package com.company.finance.view.operation;

import com.company.finance.entity.Category;
import com.company.finance.entity.Operation;
import com.company.finance.entity.OperationType;
import com.company.finance.entity.Wallet;
import com.company.finance.service.CategoryService;
import com.company.finance.service.WalletService;
import com.company.finance.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.ViewNavigators;
import io.jmix.flowui.component.valuepicker.EntityPicker;
import io.jmix.flowui.exception.ValidationException;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import java.math.BigDecimal;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "operations/:id", layout = MainView.class)
@ViewController(id = "Operation.detail")
@ViewDescriptor(path = "operation-detail-view.xml")
@EditedEntityContainer("operationDc")
public class OperationDetailView extends StandardDetailView<Operation> {
  @Autowired CategoryService categoryService;
  @Autowired private DataManager dataManager;
  @ViewComponent private TextField amountField;
  @Autowired private Notifications notifications;

  @Autowired private ViewValidation viewValidation;
  @ViewComponent private EntityPicker categoryField;
  @ViewComponent private EntityPicker walletField;

  @Autowired private WalletService walletService;
  @Autowired private ViewNavigators viewNavigators;

  @Install(to = "amountField", subject = "validator")
  private void amountFieldValidator(final BigDecimal value) {
    if (getEditedEntity().getCategory() == null
        || getEditedEntity().getCategory().getType() == OperationType.ПРИХОД) {
      return;
    }
    Map<String, BigDecimal> categoryTotal =
        categoryService.getLimitLeftover(getEditedEntity().getCategory());
    BigDecimal canSpendForCategory = null;
    if (categoryTotal != null) {
      canSpendForCategory =
          categoryTotal.get("leftover").compareTo(new BigDecimal(0)) > 0
              ? categoryTotal.get("leftover")
              : new BigDecimal(0);
    }
    if (getEditedEntity().getCategory().getType() == OperationType.РАСХОД
        && getEditedEntity().getCategory().getLimit() != null) {
      if (value.compareTo(canSpendForCategory) < 0) {
        notifications
            .create("Платеж в пределах лимита.")
            .withType(Notifications.Type.WARNING)
            .withPosition(Notification.Position.BOTTOM_END)
            .withDuration(1000)
            .show();
      } else if (value.compareTo(canSpendForCategory) > 0) {
        notifications
            .create(
                String.format(
                    "Перерасход лимита по категории. " + "Можно потратить %s руб.",
                    canSpendForCategory))
            .withType(Notifications.Type.ERROR)
            .withPosition(Notification.Position.BOTTOM_END)
            .withDuration(1000)
            .show();
      } else {
        notifications
            .create("При проведении операции бюджет " + "на категорию будет исчерпан.")
            .withType(Notifications.Type.WARNING)
            .withPosition(Notification.Position.BOTTOM_END)
            .withDuration(1000)
            .show();
      }
    }
  }

  @Subscribe(id = "helpButton", subject = "singleClickListener")
  public void onButtonClick(final ClickEvent<JmixButton> event) {
    viewNavigators.view("OperationHelp").navigate();
  }

  @Install(to = "categoryField", subject = "validator")
  private void categoryFieldValidator(final Category value) {

    if (value == null) {
      return;
    }

    if (value.getType() == OperationType.ПРИХОД) {
      return;
    }

    if (amountField.getValue().isEmpty()) {
      return;
    }

    BigDecimal amount;

    try {
      amount = new BigDecimal(amountField.getValue().replaceAll("\\s+", "").replaceAll(",", "."));
    } catch (NumberFormatException e) {
      return;
    }

    Map<String, BigDecimal> categoryTotal = categoryService.getLimitLeftover(value);

    BigDecimal canSpend =
        categoryTotal.get("leftover").compareTo(new BigDecimal(0)) > 0
            ? categoryTotal.get("leftover")
            : new BigDecimal(0);
  }

  @Install(to = "walletField", subject = "validator")
  private void walletFieldValidator(final Wallet value) {

    if (categoryField == null) {
      return;
    }

    if (categoryField.getValue() == null) {
      return;
    }

    if (((Category) categoryField.getValue()).getType() == OperationType.ПРИХОД) {
      return;
    }

    if (walletService.getWalletAmount(value).compareTo(new BigDecimal(0)) <= 0) {
      throw new ValidationException("В кошельке нет денег.");
    }

    BigDecimal amount = new BigDecimal(0);
    if (!amountField.getValue().equals("")) {
      amount = new BigDecimal(amountField.getValue().replaceAll(",", ".").replaceAll(" ", ""));
    }

    if (walletService.getWalletAmount(value).compareTo(amount) < 0) {
      throw new ValidationException(
          String.format(
              "В кошельке не хватает средств. Доступно %s руб.",
              walletService.getWalletAmount(value)));
    }

    if (walletService.getWalletAmount(value).compareTo(amount) == 0) {
      notifications
          .create("В кошельке после операции не останется средств")
          .withType(Notifications.Type.WARNING)
          .withPosition(Notification.Position.BOTTOM_END)
          .withDuration(3000)
          .show();
    }
  }
}
