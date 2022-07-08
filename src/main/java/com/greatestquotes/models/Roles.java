package com.greatestquotes.models;

import java.util.HashSet;

public class Roles extends HashSet<Role> {
    // TODO Сделать так чтобы эти значения парсило при загрузке
    public static final Role USER = new Role(1, "USER");
    public static final Role MODERATOR = new Role(3, "MODERATOR");
    public static final Role SUPERUSER = new Role(2, "SUPERUSER");
}
