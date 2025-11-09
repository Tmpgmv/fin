package com.company.finance.security;

import com.company.finance.entity.Operation;
import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;

@RowLevelRole(name = "UserCanAccessOnlyTheirOwnDataRole", code = UserCanAccessOnlyTheirOwnDataRole.CODE)
public interface UserCanAccessOnlyTheirOwnDataRole {
    String CODE = "user-can-access-only-their-own-data-role";


    @JpqlRowLevelPolicy(entityClass = Operation.class, where = "{E}.user.id = :current_user_id")
    void operation();


}