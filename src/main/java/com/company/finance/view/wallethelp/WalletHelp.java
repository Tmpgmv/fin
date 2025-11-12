package com.company.finance.view.wallethelp;

import com.company.finance.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "wallet-help", layout = MainView.class)
@ViewController(id = "WalletHelp")
@ViewDescriptor(path = "wallet-help.xml")
public class WalletHelp extends StandardView {}
