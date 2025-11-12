package com.company.finance.view.operation;

import com.company.finance.entity.Operation;
import com.company.finance.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.ViewNavigators;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "operations", layout = MainView.class)
@ViewController(id = "Operation.list")
@ViewDescriptor(path = "operation-list-view.xml")
@LookupComponent("operationsDataGrid")
@DialogMode(width = "64em")
public class OperationListView extends StandardListView<Operation> {
  @Autowired private ViewNavigators viewNavigators;

  @Subscribe(id = "helpButton", subject = "singleClickListener")
  public void onButtonClick(final ClickEvent<JmixButton> event) {
    viewNavigators.view("OperationHelp").navigate();
  }
}
