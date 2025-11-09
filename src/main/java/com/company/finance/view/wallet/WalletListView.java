package com.company.finance.view.wallet;

import com.company.finance.entity.Wallet;
import com.company.finance.service.WalletService;
import com.company.finance.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;


@Route(value = "wallets", layout = MainView.class)
@ViewController(id = "Wallet.list")
@ViewDescriptor(path = "wallet-list-view.xml")
@LookupComponent("walletsDataGrid")
@DialogMode(width = "64em")
public class WalletListView extends StandardListView<Wallet> {
    @ViewComponent
    private DataGrid<Wallet> walletsDataGrid;

    @Autowired
    private WalletService walletService;

    @Subscribe
    public void onInit(InitEvent event) {
        walletsDataGrid.addColumn(wallet -> walletService.getWalletAmount(wallet))
                .setHeader("Amount");
    }
}