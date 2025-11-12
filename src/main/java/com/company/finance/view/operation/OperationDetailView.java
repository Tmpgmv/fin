package com.company.finance.view.operation;

import com.company.finance.entity.Operation;
import com.company.finance.entity.OperationType;
import com.company.finance.entity.Wallet;
import com.company.finance.service.CategoryService;
import com.company.finance.service.WalletService;
import com.company.finance.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.UiComponentUtils;
import io.jmix.flowui.component.validation.ValidationErrors;
import io.jmix.flowui.component.valuepicker.EntityPicker;
import io.jmix.flowui.exception.ValidationException;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Map;

@Route(value = "operations/:id", layout = MainView.class)
@ViewController(id = "Operation.detail")
@ViewDescriptor(path = "operation-detail-view.xml")
@EditedEntityContainer("operationDc")
public class OperationDetailView extends StandardDetailView<Operation> {
    @Autowired
    private DataManager dataManager;

    @ViewComponent
    private TextField amountField;

    @Autowired
    CategoryService categoryService;

    @Autowired
    private Notifications notifications;

    @Autowired
    private ViewValidation viewValidation;

    @ViewComponent
    private EntityPicker walletField;

    @Autowired
    private WalletService walletService;

    @Install(to = "amountField", subject = "validator")
    private void amountFieldValidator(final BigDecimal value) {
        if (getEditedEntity().getCategory() == null){
            return;
        }

        if (getEditedEntity().getCategory().getType() == OperationType.ПРИХОД) {
            return;
        }

        Map<String, BigDecimal> categoryTotal = categoryService.getLimitLeftover(getEditedEntity().getCategory());

        if (getEditedEntity().getCategory().getType() == OperationType.РАСХОД
                && getEditedEntity().getCategory().getLimit() != null){
            if (value.compareTo(categoryTotal.get("leftover"))<0){
                notifications.create(String.format("Платеж в пределах лимита."))
                        .withType(Notifications.Type.WARNING)
                        .withPosition(Notification.Position.BOTTOM_END)
                        .withDuration(1000)
                        .show();
            } else if (value.compareTo(categoryTotal.get("leftover")) == 0){
                notifications.create(String.format("Нулевой баланс"))
                        .withType(Notifications.Type.WARNING)
                        .withPosition(Notification.Position.BOTTOM_END)
                        .withDuration(1000)
                        .show();
            } else if (value.compareTo(categoryTotal.get("leftover")) > 0){
                notifications.create(String.format("Перерасход лимита по категории."))
                        .withType(Notifications.Type.ERROR)
                        .withPosition(Notification.Position.BOTTOM_END)
                        .withDuration(1000)
                        .show();
            }
        }

        Wallet wallet = (Wallet)walletField.getValue();
        BigDecimal walletAmount = walletService.getWalletAmount(wallet);

        if (value.compareTo(walletAmount)>0){
            notifications.create(String.format("В кошельке не хватает средств. Доступно только %s.", walletAmount))
                    .withType(Notifications.Type.ERROR)
                    .withPosition(Notification.Position.BOTTOM_END)
                    .withDuration(1000)
                    .show();
        }
    }
}