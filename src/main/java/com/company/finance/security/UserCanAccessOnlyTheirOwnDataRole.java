package com.company.finance.security;

import com.company.finance.entity.Category;
import com.company.finance.entity.Operation;
import com.company.finance.entity.Transfer;
import com.company.finance.entity.Wallet;
import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;

@RowLevelRole(name = "UserCanAccessOnlyTheirOwnDataRole", code = UserCanAccessOnlyTheirOwnDataRole.CODE)
public interface UserCanAccessOnlyTheirOwnDataRole {
    String CODE = "user-can-access-only-their-own-data-role";


    @JpqlRowLevelPolicy(entityClass = Operation.class, where = "{E}.wallet.user.id = :current_user_id")
    void operation();


    @JpqlRowLevelPolicy(entityClass = Category.class, where = "{E}.user.id = :current_user_id")
    void category();


    @JpqlRowLevelPolicy(entityClass = Transfer.class, where = "{E}.from.user.id = :current_user_id")
    void transfer();


    @JpqlRowLevelPolicy(entityClass = Wallet.class, where = "{E}.user.id = :current_user_id")
    void wallet();
}