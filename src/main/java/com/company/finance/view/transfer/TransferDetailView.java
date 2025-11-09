package com.company.finance.view.transfer;

import com.company.finance.entity.*;
import com.company.finance.view.main.MainView;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.core.Metadata;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.valuepicker.EntityPicker;
import io.jmix.flowui.exception.ValidationException;
import io.jmix.flowui.view.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Route(value = "transfers/:id", layout = MainView.class)
@ViewController(id = "Transfer.detail")
@ViewDescriptor(path = "transfer-detail-view.xml")
@EditedEntityContainer("transferDc")
public class TransferDetailView extends StandardDetailView<Transfer> {
    @Autowired
    private DataManager dataManager;

    @PersistenceContext
    private EntityManager entityManager;

    @ViewComponent
    private EntityPicker fromField;

    @ViewComponent
    private TextField amountField;

    @ViewComponent
    private TextField toField;

    @Autowired
    private Metadata metadata;

    private Wallet toWallet;

    @Autowired
    private Notifications notifications;

    @Install(to = "toField", subject = "validator")
    private void toFieldValidator(String uuid) {
        uuid = uuid.strip();
        if (uuid.isEmpty()){
            throw new ValidationException("Заполните это поле.");
        }
        UUID id;
        try{
            id = UUID.fromString(uuid);
        } catch (IllegalArgumentException e){
            throw new ValidationException("Такого кошелька нет: " + uuid);
        }

        Optional<Wallet> wallet = getWallet(id);

        if (wallet.isEmpty()){
            throw new ValidationException("Такого кошелька нет: " + uuid);
        }

        toWallet = wallet.get();

        if (toWallet.equals(getEditedEntity().getFrom())){
            throw new ValidationException("Нет смысла переводить на тот же кошелек");
        }
    }



    public Optional<Wallet> getWallet(UUID id) {
        String sql = "select * from wallet where id = ?1";
        List<Wallet> result = entityManager
                .createNativeQuery(sql, Wallet.class)
                .setParameter(1, id)
                .setMaxResults(1)
                .getResultList();
        return result.stream().findFirst();
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

    @Subscribe
    public void onBeforeSave(BeforeSaveEvent event) {
//        String toWalletUuidOrCode = toField.getValue();

//        // Например, если пользователь вводит UUID
//        Optional<Wallet> walletOpt = dataManager.load(Wallet.class)
//                .id(UUID.fromString(toWalletUuidOrCode))
//                .optional();
//
//        if (walletOpt.isEmpty()) {
//            event.preventSave(); // Прервать сохранение
//            notifications.show("Кошелек не найден: " + toWalletUuidOrCode);
//            return;
//        }

//        Wallet wallet = walletOpt.get();
//        if (wallet.equals(getEditedEntity().getFrom())) {
//            event.preventSave();
//            notifications.show("Нельзя переводить на тот же кошелек");
//            return;
//        }

        getEditedEntity().setTo(toWallet);
    }

}