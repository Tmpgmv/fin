package com.company.finance.view.category;

import com.company.finance.entity.Category;
import com.company.finance.entity.OperationType;
import com.company.finance.view.main.MainView;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.select.JmixSelect;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.view.*;
import org.springframework.util.Assert;

@Route(value = "categories/:id", layout = MainView.class)
@ViewController(id = "Category.detail")
@ViewDescriptor(path = "category-detail-view.xml")
@EditedEntityContainer("categoryDc")
public class CategoryDetailView extends StandardDetailView<Category> {
  @ViewComponent private JmixSelect typeField;

  @ViewComponent private TypedTextField limitField;

  @Subscribe("typeField")
  public void onTypeFieldComponentValueChange(
      final AbstractField.ComponentValueChangeEvent<JmixSelect<OperationType>, OperationType>
          event) {
    if (event.getValue().equals(OperationType.ПРИХОД)) {
      limitField.setEnabled(false);
    } else {
      Assert.isTrue(event.getValue().equals(OperationType.РАСХОД), "Должен быть 'Расход'");
      limitField.setEnabled(true);
    }
  }
}
