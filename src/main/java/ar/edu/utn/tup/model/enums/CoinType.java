package ar.edu.utn.tup.model.enums;

public enum CoinType {
    EMPTY("EMPTY"),
    USD("USD"),
    ARS("ARS"),
    GBP("GBP"),
    EUR("EUR");

    private final String description;

    CoinType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static CoinType fromDescription(String description) {
        for (CoinType coinType : CoinType.values()) {
            if (coinType.getDescription().equalsIgnoreCase(description)) {
                return coinType;
            }
        }

        throw new IllegalArgumentException("No matching CoinType for description: " + description);
    }
}
