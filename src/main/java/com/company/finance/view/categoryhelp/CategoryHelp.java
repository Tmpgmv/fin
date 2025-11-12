package com.company.finance.view.categoryhelp;

import com.company.finance.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "category-help", layout = MainView.class)
@ViewController(id = "CategoryHelp")
@ViewDescriptor(path = "category-help.xml")
public class CategoryHelp extends StandardView {}
