package com.company.finance.view.main;

import com.company.finance.entity.CategoryGridData;
import com.company.finance.entity.OperationType;
import com.company.finance.entity.ReportDto;
import com.company.finance.entity.User;
import com.company.finance.service.CategoryService;
import com.company.finance.service.OperationService;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.google.common.base.Strings;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.avatar.AvatarVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Section;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.Route;
import io.jmix.core.Messages;
import io.jmix.core.usersubstitution.CurrentUserSubstitution;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.app.main.StandardMainView;
import io.jmix.flowui.component.datepicker.TypedDatePicker;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.upload.FileUploadField;
import io.jmix.flowui.download.DownloadFormat;
import io.jmix.flowui.download.Downloader;
import io.jmix.flowui.exception.ValidationException;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.kit.component.upload.event.FileUploadSucceededEvent;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

@Route("")
@ViewController(id = "MainView")
@ViewDescriptor(path = "main-view.xml")
public class MainView extends StandardMainView {

  @Autowired private Messages messages;
  @Autowired private UiComponents uiComponents;
  @Autowired private CurrentUserSubstitution currentUserSubstitution;

  @Autowired private OperationService operationService;

  @Autowired private CategoryService categoryService;

  @ViewComponent private H2 totalIncome;

  @ViewComponent private H2 totalExpense;

  @ViewComponent private CollectionContainer<CategoryGridData> catIncomeDc;

  @ViewComponent private CollectionContainer<CategoryGridData> catExpenseDc;

  @ViewComponent private DataGrid catExpenseDataGrid;

  @ViewComponent private TypedDatePicker fromComponent;

  @ViewComponent private TypedDatePicker throughComponent;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Downloader downloader;

    @ViewComponent
    private FileUploadField fileUploadField;

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
    String userName =
        String.format(
                "%s %s",
                Strings.nullToEmpty(user.getFirstName()), Strings.nullToEmpty(user.getLastName()))
            .trim();

    return userName.isEmpty() ? user.getUsername() : userName;
  }

  private boolean isSubstituted(User user) {
    UserDetails authenticatedUser = currentUserSubstitution.getAuthenticatedUser();
    return user != null && !authenticatedUser.getUsername().equals(user.getUsername());
  }

  @Subscribe
  public void onInit(final InitEvent event) {
    showReport(null, null);
  }

  private void showReport(LocalDate from, LocalDate through) {
    BigDecimal totalExp = operationService.geTotal(OperationType.РАСХОД, from, through);
//    totalExpense.setText(String.format("Общие расходы: %s", totalExp));
    List<CategoryGridData> categoriesExpense =
        categoryService.getCategories(OperationType.РАСХОД, from, through);
//    catExpenseDc.setItems(categoriesExpense);

    BigDecimal totalInc = operationService.geTotal(OperationType.ПРИХОД, from, through);
//    totalIncome.setText(String.format("Общий доход: %s", totalInc));
    List<CategoryGridData> categoriesIncome =
        categoryService.getCategories(OperationType.ПРИХОД, from, through);
//    catIncomeDc.setItems(categoriesIncome);
    showReport(totalExp,
            categoriesExpense,
            totalInc,
            categoriesIncome);
  }

    private void showReport(
                            BigDecimal totalExp,
                            List<CategoryGridData> categoriesExpense,
                            BigDecimal totalInc,
                            List<CategoryGridData> categoriesIncome
                            ) {
        totalIncome.setText(String.format("Общий доход: %s", totalInc));
        totalExpense.setText(String.format("Общие расходы: %s", totalExp));
        catExpenseDc.setItems(categoriesExpense);
        catIncomeDc.setItems(categoriesIncome);
    }



  @Supply(to = "catExpenseDataGrid.leftover", subject = "renderer")
  protected Renderer<CategoryGridData> limitColumnRenderer() {
    return new ComponentRenderer<>(
        categoryGridData -> {
          Span span = uiComponents.create(Span.class);
          if (categoryGridData.getLeftover() != null
              && categoryGridData.getLeftover().compareTo(BigDecimal.ZERO) != 0) {
            span.setText(categoryGridData.getLeftover().toPlainString());
          } else {
            span.setText(""); // leave empty if limit is zero
          }
          return span;
        });
  }

  @Subscribe("fromComponent")
  public void onFromComponentValueChange(
      final AbstractField.ComponentValueChangeEvent<TypedDatePicker<Comparable>, Comparable>
          event) {
    LocalDate from = (LocalDate) event.getValue();

    showReport(from, (LocalDate) throughComponent.getValue());
  }

  @Subscribe("throughComponent")
  public void onThroughComponentValueChange(
      final AbstractField.ComponentValueChangeEvent<TypedDatePicker<Comparable>, Comparable>
          event) {
    LocalDate through = (LocalDate) event.getValue();

    showReport((LocalDate) fromComponent.getValue(), through);
  }

  @Install(to = "throughComponent", subject = "validator")
  private void throughComponentValidator(final Comparable value) {
    LocalDate from = (LocalDate) fromComponent.getValue();

    if (from == null) {
      return;
    }

    Date val = (Date) value;
    LocalDate through =
        Instant.ofEpochMilli(val.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    if (through.isBefore(from)) {
      throw new ValidationException("Не может быть раньше, чем \"С\"");
    }
  }

    @Subscribe(id = "exp", subject = "clickListener")
    public void onExpClick(final ClickEvent<JmixButton> event) {
        LocalDate from = (LocalDate) fromComponent.getValue();
        LocalDate through = (LocalDate) throughComponent.getValue();

        BigDecimal totalExp = operationService.geTotal(OperationType.РАСХОД, from, through);
        List<CategoryGridData> categoriesExpense =
                categoryService.getCategories(OperationType.РАСХОД, from, through);

        BigDecimal totalInc = operationService.geTotal(OperationType.ПРИХОД, from, through);

        List<CategoryGridData> categoriesIncome =
                categoryService.getCategories(OperationType.ПРИХОД, from, through);

        ReportDto report = new ReportDto(from, through, totalExp, totalInc, categoriesExpense, categoriesIncome);



        try {
            String json = objectMapper.writeValueAsString(report);
            byte[] content = json.getBytes(StandardCharsets.UTF_8);
            downloader.download(
                    content,
                    "report.json",
                    new DownloadFormat("application/json", "json")
            );
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @Subscribe("fileUploadField")
    public void onFileUploadFieldFileUploadSucceeded(FileUploadSucceededEvent<FileUploadField> event) {
        String fileName = event.getFileName();
        byte[] content = event.getSource().getValue();

        try {
            // Parse the JSON content into your ReportDto class
            ReportDto report = objectMapper.readValue(content, ReportDto.class);
            showReportDto(report);
            // Now you can work with the parsed report object
            System.out.println("Report loaded: " + report);

            // For UI feedback, you may use a notification (if needed)
            // notifications.create("Report uploaded successfully").show();

        } catch (StreamReadException e) {
            e.printStackTrace();
        }
        catch (MismatchedInputException e) {
            // Handle parsing errors
            e.printStackTrace();
        } catch (DatabindException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showReportDto(ReportDto report){
      fromComponent.setValue(report.getFrom());
      throughComponent.setValue(report.getThrough());
        showReport(report.getTotalExpense(),
                report.getCategoriesExpense(),
                report.getTotalIncome(),
                report.getCategoriesExpense());
    }
}
