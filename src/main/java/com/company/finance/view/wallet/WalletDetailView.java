package com.company.finance.view.wallet;

import com.company.finance.entity.Wallet;
import com.company.finance.service.WalletService;
import com.company.finance.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.ViewNavigators;
import io.jmix.flowui.component.UiComponentUtils;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "wallets/:id", layout = MainView.class)
@ViewController(id = "Wallet.detail")
@ViewDescriptor(path = "wallet-detail-view.xml")
@EditedEntityContainer("walletDc")
public class WalletDetailView extends StandardDetailView<Wallet> {

  @ViewComponent private TextField idField;

  @ViewComponent private TextField amount;

  @Autowired private Notifications notifications;

  @Autowired private WalletService walletService;
  @ViewComponent private TypedTextField<Object> amountField;
  @Autowired private ViewNavigators viewNavigators;

  @Subscribe(id = "copyIdButton", subject = "clickListener")
  public void onCopyIdButtonClick(final ClickEvent<JmixButton> event) {
    String id = idField.getValue();
    UiComponentUtils.copyToClipboard(id)
        .then(
            successResult ->
                notifications
                    .create("Номер кошелька скопирован!")
                    .withPosition(Notification.Position.BOTTOM_END)
                    .withThemeVariant(NotificationVariant.LUMO_SUCCESS)
                    .show(),
            errorResult ->
                notifications
                    .create("Не удалось скопировать!")
                    .withPosition(Notification.Position.BOTTOM_END)
                    .withThemeVariant(NotificationVariant.LUMO_ERROR)
                    .show());
  }

  @Subscribe
  public void onReady(final ReadyEvent event) {
    amountField.setValue(walletService.getWalletAmount(getEditedEntity()).toString());
  }

  @Subscribe(id = "helpButton", subject = "singleClickListener")
  public void onButtonClick(final ClickEvent<JmixButton> event) {
    viewNavigators.view("WalletHelp").navigate();
  }
}
