package com.company.finance.view.transfer;

import com.company.finance.entity.Transfer;
import com.company.finance.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "transfers", layout = MainView.class)
@ViewController(id = "Transfer.list")
@ViewDescriptor(path = "transfer-list-view.xml")
@LookupComponent("transfersDataGrid")
@DialogMode(width = "64em")
public class TransferListView extends StandardListView<Transfer> {}
