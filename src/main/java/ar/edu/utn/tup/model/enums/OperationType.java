package ar.edu.utn.tup.model.enums;

public enum OperationType {
    MONEY_DEPOSIT("depositIcon.png"),
    MONEY_WITHDRAWAL("withdrawallIcon.png"),
    MONEY_TRANSFER("transferIcon.png"),
    CHECK_BALANCE("accountDetailIcon.png");
    public String route;

    OperationType(String route) {
        this.route = route;
    }

    public String getRoute() {
        return route;
    }
}
