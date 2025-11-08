package com.company.finance.view.wallet;

import com.company.finance.entity.Wallet;
import com.company.finance.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.UiComponentUtils;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "wallets/:id", layout = MainView.class)
@ViewController(id = "Wallet.detail")
@ViewDescriptor(path = "wallet-detail-view.xml")
@EditedEntityContainer("walletDc")
public class WalletDetailView extends StandardDetailView<Wallet> {


    @ViewComponent
    private TextField idField;

    @Autowired
    private Notifications notifications;




    @Subscribe(id = "copyIdButton", subject = "clickListener")
    public void onCopyIdButtonClick(final ClickEvent<JmixButton> event) {
        String id = idField.getValue().toString();
        UiComponentUtils.copyToClipboard(id)
                .then(successResult -> notifications.create("Номер кошелька скопирован!")
                                .withPosition(Notification.Position.BOTTOM_END)
                                .withThemeVariant(NotificationVariant.LUMO_SUCCESS)
                                .show(),
                        errorResult -> notifications.create("Не удалось скопировать!")
                                .withPosition(Notification.Position.BOTTOM_END)
                                .withThemeVariant(NotificationVariant.LUMO_ERROR)
                                .show());
    }
}