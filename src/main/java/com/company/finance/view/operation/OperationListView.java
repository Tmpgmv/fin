package com.company.finance.view.operation;

import com.company.finance.entity.Operation;
import com.company.finance.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "operations", layout = MainView.class)
@ViewController(id = "Operation.list")
@ViewDescriptor(path = "operation-list-view.xml")
@LookupComponent("operationsDataGrid")
@DialogMode(width = "64em")
public class OperationListView extends StandardListView<Operation> {
}