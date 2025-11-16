package com.company.finance.smoke;

import static org.assertj.core.api.Assertions.assertThat;

import com.company.finance.FinanceApplication;
import com.company.finance.entity.Category;
import com.company.finance.entity.Transfer;
import com.company.finance.entity.Wallet;
import com.company.finance.view.category.CategoryDetailView;
import com.company.finance.view.category.CategoryListView;
import com.company.finance.view.login.LoginView;
import com.company.finance.view.main.MainView;
import com.company.finance.view.transfer.TransferDetailView;
import com.company.finance.view.transfer.TransferListView;
import com.company.finance.view.wallet.WalletDetailView;
import com.company.finance.view.wallet.WalletListView;
import io.jmix.core.DataManager;
import io.jmix.flowui.ViewNavigators;
import io.jmix.flowui.testassist.FlowuiTestAssistConfiguration;
import io.jmix.flowui.testassist.UiTest;
import io.jmix.flowui.testassist.UiTestUtils;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@UiTest
@SpringBootTest(classes = {FinanceApplication.class, FlowuiTestAssistConfiguration.class})
class LoginViewUiTest {
  @Autowired DataManager dataManager;
  @Autowired private ViewNavigators viewNavigators;

  @Test
  void test_loginViewAvailable() {
    // Open the LoginView using navigation.
    // Pass UiTestUtils.getCurrentView() as the parent
    viewNavigators.view(UiTestUtils.getCurrentView(), LoginView.class).navigate();

    // Get the currently opened view
    LoginView loginView = UiTestUtils.getCurrentView();

    assertThat(loginView).isNotNull();
  }

  @Test
  void test_categoriesViewAvailable() {
    // Open the LoginView using navigation.
    // Pass UiTestUtils.getCurrentView() as the parent
    viewNavigators.view(UiTestUtils.getCurrentView(), CategoryListView.class).navigate();

    // Get the currently opened view
    CategoryListView listView = UiTestUtils.getCurrentView();

    assertThat(listView).isNotNull();
  }

  @Test
  void test_categoryIncomeViewAvailable() {
    // 1. Load or create the Category entity for your test (use existing or create as needed)
    Category category =
        dataManager
            .load(Category.class)
            .id(UUID.fromString("019a6a15-be46-7227-a03a-e90a4e0163c4"))
            .optional()
            .orElseThrow(() -> new IllegalStateException("Test Category not found"));

    // 2. Navigate to the DetailView for that entity
    viewNavigators
        .detailView(UiTestUtils.getCurrentView(), Category.class)
        .editEntity(category)
        .withViewClass(CategoryDetailView.class) // optional if only one detail view
        .navigate();

    // 3. Get the detail view instance and assert
    CategoryDetailView categoryDetailView = UiTestUtils.getCurrentView();
    assertThat(categoryDetailView).isNotNull();

    // now you can interact with fields in the view, etc.
  }

  @Test
  void test_categoryExpenseViewAvailable() {
    // 1. Load or create the Category entity for your test (use existing or create as needed)
    Category category =
        dataManager
            .load(Category.class)
            .id(UUID.fromString("019a6a15-d8d2-73b9-b60f-7b7e1170a434"))
            .optional()
            .orElseThrow(() -> new IllegalStateException("Test Category not found"));

    // 2. Navigate to the DetailView for that entity
    viewNavigators
        .detailView(UiTestUtils.getCurrentView(), Category.class)
        .editEntity(category)
        .withViewClass(CategoryDetailView.class) // optional if only one detail view
        .navigate();

    // 3. Get the detail view instance and assert
    CategoryDetailView categoryDetailView = UiTestUtils.getCurrentView();
    assertThat(categoryDetailView).isNotNull();

    // now you can interact with fields in the view, etc.
  }

  @Test
  void test_transferAvailable() {
    // 1. Load or create the Category entity for your test (use existing or create as needed)
    Transfer transfer =
        dataManager
            .load(Transfer.class)
            .id(UUID.fromString("019a8bca-f3cc-7c93-8fe6-3e9d58691dd8"))
            .optional()
            .orElseThrow(() -> new IllegalStateException("Test Transfer not found"));

    // 2. Navigate to the DetailView for that entity
    viewNavigators
        .detailView(UiTestUtils.getCurrentView(), Transfer.class)
        .editEntity(transfer)
        .withViewClass(TransferDetailView.class) // optional if only one detail view
        .navigate();

    // 3. Get the detail view instance and assert
    TransferDetailView transferDetailView = UiTestUtils.getCurrentView();
    assertThat(transferDetailView).isNotNull();

    // now you can interact with fields in the view, etc.
  }

  @Test
  void test_transfersViewAvailable() {
    // Open the LoginView using navigation.
    // Pass UiTestUtils.getCurrentView() as the parent
    viewNavigators.view(UiTestUtils.getCurrentView(), TransferListView.class).navigate();

    // Get the currently opened view
    TransferListView listView = UiTestUtils.getCurrentView();

    assertThat(listView).isNotNull();
  }

  @Test
  void test_walletDetailViewAvailable() {
    // 1. Load or create the Category entity for your test (use existing or create as needed)
    Wallet wallet =
        dataManager
            .load(Wallet.class)
            .id(UUID.fromString("019a6849-7c07-7809-9ed1-8c8092f4b8a1"))
            .optional()
            .orElseThrow(() -> new IllegalStateException("Test Wallet not found"));

    // 2. Navigate to the DetailView for that entity
    viewNavigators
        .detailView(UiTestUtils.getCurrentView(), Wallet.class)
        .editEntity(wallet)
        .withViewClass(WalletDetailView.class) // optional if only one detail view
        .navigate();

    // 3. Get the detail view instance and assert
    WalletDetailView walletDetailView = UiTestUtils.getCurrentView();
    assertThat(walletDetailView).isNotNull();
  }

  @Test
  void test_walletListViewAvailable() {
    // Open the LoginView using navigation.
    // Pass UiTestUtils.getCurrentView() as the parent
    viewNavigators.view(UiTestUtils.getCurrentView(), WalletListView.class).navigate();

    // Get the currently opened view
    WalletListView listView = UiTestUtils.getCurrentView();

    assertThat(listView).isNotNull();
  }

  @Test
  void test_mainViewAvailable() {
    viewNavigators.view(UiTestUtils.getCurrentView(), MainView.class).navigate();

    // Get the currently opened view
    MainView mainView = UiTestUtils.getCurrentView();

    assertThat(mainView).isNotNull();
  }
}
