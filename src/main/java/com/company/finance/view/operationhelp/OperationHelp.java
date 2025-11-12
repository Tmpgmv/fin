package com.company.finance.view.operationhelp;

import com.company.finance.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "operation-help", layout = MainView.class)
@ViewController(id = "OperationHelp")
@ViewDescriptor(path = "operation-help.xml")
public class OperationHelp extends StandardView {}
