package com.greatestquotes.models;

import java.util.HashSet;

public class Roles extends HashSet<Role> {
    // TODO Сделать так чтобы эти значения парсило при загрузке
    public static final Role USER = new Role(1, "USER");
    public static final Role MODERATOR = new Role(3, "MODERATOR");
    public static final Role SUPERUSER = new Role(2, "SUPERUSER");
    public static final Role GUEST = new Role(4, "GUEST");

    public String createTemplate4Query() {
        StringBuilder builder = new StringBuilder();
        builder.append('(');
        for (Role role : this)
            if (role.id() != Roles.MODERATOR.id())
                builder.append("?,");
        builder.deleteCharAt(builder.length()-1);
        builder.append(')');
        return builder.toString();
    }

    public boolean contain(Role role) {
        return this.stream().toList().contains(role);
    }
}
