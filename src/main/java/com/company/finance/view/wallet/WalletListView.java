package com.company.finance.view.wallet;

import com.company.finance.entity.Wallet;
import com.company.finance.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "wallets", layout = MainView.class)
@ViewController(id = "Wallet.list")
@ViewDescriptor(path = "wallet-list-view.xml")
@LookupComponent("walletsDataGrid")
@DialogMode(width = "64em")
public class WalletListView extends StandardListView<Wallet> {
}