package com.company.finance.view.transferhelp;

import com.company.finance.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "transfer-help", layout = MainView.class)
@ViewController(id = "TransferHelp")
@ViewDescriptor(path = "transfer-help.xml")
public class TransferHelp extends StandardView {}
