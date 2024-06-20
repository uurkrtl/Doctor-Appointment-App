package de.schnellertermin.backend.services.messages;

public class ComplaintMessage {
    private ComplaintMessage() {}


    public static final String COMPLAINT_NAME_REQUIRED = "Complaint name is required";
    public static final String CATEGORY_REQUIRED = "Category is required";
    public static final String COMPLAINT_NAME_EXISTS = "Complaint name already exists";
}
