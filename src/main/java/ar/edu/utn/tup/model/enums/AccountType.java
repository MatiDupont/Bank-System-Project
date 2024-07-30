package ar.edu.utn.tup.model.enums;

public enum AccountType {
    EMPTY,
    CURRENT_ACCOUNT,
    SAVING_BANK;

    @Override
    public String toString() {
        return name().replace("_", " ");
    }
}
