package com.company.finance.view.transfer;

import com.company.finance.entity.*;
import com.company.finance.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.core.Metadata;
import io.jmix.flowui.component.valuepicker.EntityPicker;
import io.jmix.flowui.exception.ValidationException;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.awt.*;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;


@Route(value = "transfers/:id", layout = MainView.class)
@ViewController(id = "Transfer.detail")
@ViewDescriptor(path = "transfer-detail-view.xml")
@EditedEntityContainer("transferDc")
public class TransferDetailView extends StandardDetailView<Transfer> {
    @Autowired
    private DataManager dataManager;

    @ViewComponent
    private EntityPicker fromField;

    @ViewComponent
    private TextField amountField;

    @Autowired
    private Metadata metadata;

    private Wallet toWallet;

    @Install(to = "toField", subject = "validator")
    private void toFieldValidator(final UUID uuid) {
        Optional<Wallet> wallet = dataManager.load(Wallet.class).id(uuid)
                .optional();

        if (wallet.isEmpty()){
            throw new ValidationException("Такого кошелька нет: " + uuid);
        }

        toWallet = wallet.get();

        if (toWallet.equals(getEditedEntity().getFrom())){
            throw new ValidationException("Нет смысла переводить на тот же кошелек");
        }
    }


    @Subscribe
    public void onAfterSave(final AfterSaveEvent event) {
        createOperation(OperationType.ПРИХОД);
        createOperation(OperationType.РАСХОД);
    }


    private void createOperation(OperationType type) {
        Category category = getCategory(type);
        Operation operation = metadata.create(Operation.class);
        operation.setAmount(getEditedEntity().getAmount());

        operation.setCategory(category);

        Wallet wallet;
        String text = "";
        if (type == OperationType.ПРИХОД){
            text = "Поступление от ";
            wallet = toWallet;
        } else {
            Assert.isTrue(type == OperationType.РАСХОД, "Должен быть расход");
            text = "Перевод на кошелек";
            wallet = getEditedEntity().getFrom();
        }

        operation.setWallet(wallet);
        operation.setDescription(String.format("%s %s",
                text, getEditedEntity().getTo()));
        operation.setComment("Операция создана автоматически.");
        dataManager.save(operation);
    }

    private Category getCategory(OperationType type) {
        Category category = dataManager.load(Category.class).query(
                "select c from Category c where c.name = :name and c.type = :type")
                .parameter("name", "Перевод между кошельками")
                .parameter("type", type.getId()
        ).one();
        return category;
    }
}