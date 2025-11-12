package com.company.finance.security;

import com.company.finance.entity.*;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityflowui.role.annotation.MenuPolicy;
import io.jmix.securityflowui.role.annotation.ViewPolicy;

@ResourceRole(name = "User", code = UserRole.CODE)
public interface UserRole {
  String CODE = "user";

  @EntityAttributePolicy(
      entityClass = Category.class,
      attributes = "*",
      action = EntityAttributePolicyAction.MODIFY)
  @EntityPolicy(entityClass = Category.class, actions = EntityPolicyAction.ALL)
  void category();

  @EntityAttributePolicy(
      entityClass = Operation.class,
      attributes = "*",
      action = EntityAttributePolicyAction.MODIFY)
  @EntityPolicy(entityClass = Operation.class, actions = EntityPolicyAction.ALL)
  void operation();

  @EntityAttributePolicy(
      entityClass = Transfer.class,
      attributes = "*",
      action = EntityAttributePolicyAction.MODIFY)
  @EntityPolicy(entityClass = Transfer.class, actions = EntityPolicyAction.ALL)
  void transfer();

  @EntityAttributePolicy(
      entityClass = Wallet.class,
      attributes = "*",
      action = EntityAttributePolicyAction.MODIFY)
  @EntityPolicy(entityClass = Wallet.class, actions = EntityPolicyAction.ALL)
  void wallet();

  @MenuPolicy(
      menuIds = {
        "Category.list",
        "Transfer.list",
        "Wallet.list",
        "Operation.list",
        "CategoryHelp",
        "TransferHelp",
        "OperationHelp",
        "WalletHelp"
      })
  @ViewPolicy(
      viewIds = {
        "Category.list",
        "Transfer.list",
        "Wallet.list",
        "Operation.list",
        "Wallet.detail",
        "Transfer.detail",
        "Operation.detail",
        "Category.detail",
        "MainView",
        "CategoryHelp",
        "TransferHelp",
        "OperationHelp",
        "WalletHelp"
      })
  void screens();

  @EntityAttributePolicy(
      entityClass = User.class,
      attributes = "*",
      action = EntityAttributePolicyAction.VIEW)
  @EntityPolicy(entityClass = User.class, actions = EntityPolicyAction.READ)
  void user();

  @EntityAttributePolicy(
      entityClass = CategoryGridData.class,
      attributes = "*",
      action = EntityAttributePolicyAction.VIEW)
  @EntityPolicy(entityClass = CategoryGridData.class, actions = EntityPolicyAction.ALL)
  void categoryGridData();
}
