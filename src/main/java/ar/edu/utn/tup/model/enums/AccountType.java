package ar.edu.utn.tup.model.enums;

public enum AccountType {
    EMPTY("EMPTY"),
    CURRENT_ACCOUNT("Current Account"),
    SAVING_BANK("Saving Bank");
    private final String description;

    AccountType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name().replace("_", " ");
    }

    public static AccountType fromDescription(String description) {
        for (AccountType accountType : AccountType.values()) {
            if (accountType.getDescription().equalsIgnoreCase(description)) {
                return accountType;
            }
        }

        throw new IllegalArgumentException("No matching AccountType for description: " + description);
    }
}
