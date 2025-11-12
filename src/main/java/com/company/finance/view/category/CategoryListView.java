package com.company.finance.view.category;

import com.company.finance.entity.Category;
import com.company.finance.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.ViewNavigators;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "categories", layout = MainView.class)
@ViewController(id = "Category.list")
@ViewDescriptor(path = "category-list-view.xml")
@LookupComponent("categoriesDataGrid")
@DialogMode(width = "64em")
public class CategoryListView extends StandardListView<Category> {
  @Autowired private ViewNavigators viewNavigators;

  @Subscribe(id = "helpButton", subject = "singleClickListener")
  public void onButtonClick(final ClickEvent<JmixButton> event) {
    viewNavigators.view("CategoryHelp").navigate();
  }
}
