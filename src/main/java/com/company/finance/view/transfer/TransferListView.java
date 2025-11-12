package com.company.finance.view.transfer;

import com.company.finance.entity.Transfer;
import com.company.finance.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.ViewNavigators;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "transfers", layout = MainView.class)
@ViewController(id = "Transfer.list")
@ViewDescriptor(path = "transfer-list-view.xml")
@LookupComponent("transfersDataGrid")
@DialogMode(width = "64em")
public class TransferListView extends StandardListView<Transfer> {
  @Autowired private ViewNavigators viewNavigators;

  @Subscribe(id = "helpButton", subject = "singleClickListener")
  public void onButtonClick(final ClickEvent<JmixButton> event) {
    viewNavigators.view("TransferHelp").navigate();
  }
}
