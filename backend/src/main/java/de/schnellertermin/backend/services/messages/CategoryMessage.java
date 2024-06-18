package de.schnellertermin.backend.services.messages;

public class CategoryMessage {
    private CategoryMessage() {}
    public static final String CATEGORY_NAME_REQUIRED = "Category name is required";
    public static final String CATEGORY_NAME_EXISTS = "Category name already exists";
    public static final String CATEGORY_NOT_FOUND = "Category not found";
}
