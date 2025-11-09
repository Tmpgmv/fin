package com.company.finance.view.main;

import com.company.finance.entity.CategoryGridData;
import com.company.finance.entity.Operation;
import com.company.finance.entity.OperationType;
import com.company.finance.entity.User;
import com.company.finance.service.CategoryService;
import com.company.finance.service.OperationService;
import com.company.finance.service.WalletService;
import com.google.common.base.Strings;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.avatar.AvatarVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;
import io.jmix.core.Messages;
import io.jmix.core.usersubstitution.CurrentUserSubstitution;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.app.main.StandardMainView;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.model.DataLoader;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;


import java.math.BigDecimal;
import java.util.List;

@Route("")
@ViewController(id = "MainView")
@ViewDescriptor(path = "main-view.xml")
public class MainView extends StandardMainView {

    @Autowired
    private Messages messages;
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private CurrentUserSubstitution currentUserSubstitution;

    @Autowired
    private OperationService operationService;

    @Autowired
    private CategoryService categoryService;

    @ViewComponent
    private H2 totalIncome;

    @ViewComponent
    private H2 totalExpense;

    @ViewComponent
    private CollectionContainer<CategoryGridData> catIncomeDc;

    @ViewComponent
    private CollectionContainer<CategoryGridData> catExpenseDc;

    @Install(to = "userMenu", subject = "buttonRenderer")
    private Component userMenuButtonRenderer(final UserDetails userDetails) {
        if (!(userDetails instanceof User user)) {
            return null;
        }

        String userName = generateUserName(user);

        Div content = uiComponents.create(Div.class);
        content.setClassName("user-menu-button-content");

        Avatar avatar = createAvatar(userName);

        Span name = uiComponents.create(Span.class);
        name.setText(userName);
        name.setClassName("user-menu-text");

        content.add(avatar, name);

        if (isSubstituted(user)) {
            Span subtext = uiComponents.create(Span.class);
            subtext.setText(messages.getMessage("userMenu.substituted"));
            subtext.setClassName("user-menu-subtext");

            content.add(subtext);
        }

        return content;
    }

    @Install(to = "userMenu", subject = "headerRenderer")
    private Component userMenuHeaderRenderer(final UserDetails userDetails) {
        if (!(userDetails instanceof User user)) {
            return null;
        }

        Div content = uiComponents.create(Div.class);
        content.setClassName("user-menu-header-content");

        String name = generateUserName(user);

        Avatar avatar = createAvatar(name);
        avatar.addThemeVariants(AvatarVariant.LUMO_LARGE);

        Span text = uiComponents.create(Span.class);
        text.setText(name);
        text.setClassName("user-menu-text");

        content.add(avatar, text);

        if (name.equals(user.getUsername())) {
            text.addClassNames("user-menu-text-subtext");
        } else {
            Span subtext = uiComponents.create(Span.class);
            subtext.setText(user.getUsername());
            subtext.setClassName("user-menu-subtext");

            content.add(subtext);
        }

        return content;
    }

    private Avatar createAvatar(String fullName) {
        Avatar avatar = uiComponents.create(Avatar.class);
        avatar.setName(fullName);
        avatar.getElement().setAttribute("tabindex", "-1");
        avatar.setClassName("user-menu-avatar");

        return avatar;
    }

    private String generateUserName(User user) {
        String userName = String.format("%s %s",
                        Strings.nullToEmpty(user.getFirstName()),
                        Strings.nullToEmpty(user.getLastName()))
                .trim();

        return userName.isEmpty() ? user.getUsername() : userName;
    }

    private boolean isSubstituted(User user) {
        UserDetails authenticatedUser = currentUserSubstitution.getAuthenticatedUser();
        return user != null && !authenticatedUser.getUsername().equals(user.getUsername());
    }


    
    
    @Subscribe
    public void onInit(final InitEvent event) {
        BigDecimal totalExp = operationService.geTotal(OperationType.РАСХОД);
        totalExpense.setText(String.format("Общие расходы: %s", totalExp));
        List<CategoryGridData> categoriesExpense = categoryService.getCategories(OperationType.РАСХОД);
        catExpenseDc.setItems(categoriesExpense);


        BigDecimal totalInc = operationService.geTotal(OperationType.ПРИХОД);
        totalIncome.setText(String.format("Общий доход: %s", totalInc));
        List<CategoryGridData> categoriesIncome = categoryService.getCategories(OperationType.ПРИХОД);
        catIncomeDc.setItems(categoriesIncome);
    }
    
}
