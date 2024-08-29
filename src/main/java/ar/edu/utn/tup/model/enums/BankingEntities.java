package ar.edu.utn.tup.model.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public enum BankingEntities {
    EMPTY(0, 0,0, new HashMap<>(), "", ""),
    BANCO_NACION_ARGENTINA(generateEntityCode(), generateBranchCode(),175000.00, generateInterestRates(), ".bna", "Banco Nacion Argentina"),
    BANCO_PCIA_BUENOS_AIRES(generateEntityCode(), generateBranchCode(), 125000.00, generateInterestRates(), ".bpcia", "Banco Provincia Buenos Aires"),
    BANCO_MACRO(generateEntityCode(), generateBranchCode(), 205000.00, generateInterestRates(), ".mcr", "Banco Macro"),
    HSBC(generateEntityCode(), generateBranchCode(), 350000.00, generateInterestRates(), ".hsbc", "Banco HSBC"),
    SANTANDER_RIO(generateEntityCode(), generateBranchCode(), 300000.00, generateInterestRates(), ".sr", "Banco Santander Rio"),
    BBVA(generateEntityCode(), generateBranchCode(), 320000.00, generateInterestRates(), ".bbva", "Banco BBVA");

    private final int entityCode;
    private final int branchCode;
    private double bonusInit;
    private final Map<String, Double> interestRates;
    private final String abbreviation;
    private final String description;

    BankingEntities(int entityCode, int branchCode, double bonusInit, Map<String, Double> interestRates, String abbreviation, String description) {
        this.entityCode = entityCode;
        this.branchCode = branchCode;
        this.bonusInit = bonusInit;
        this.interestRates = interestRates;
        this.abbreviation = abbreviation;
        this.description = description;
        //System.out.println("Initialized BankingEntity: " + this.name() + " with EntityCode: " + entityCode + " and BranchCode: " + branchCode);
    }

    public int getEntityCode() {
        return entityCode;
    }

    public int getBranchCode() {
        return branchCode;
    }

    public double getBonusInit() {
        return bonusInit;
    }
    public String getAbbreviation() {
        return abbreviation;
    }
    public String getDescription() {
        return description;
    }

 /*   @Override
    public String toString() {
        return name().replace("_", " ");
    }*/

    private static int generateEntityCode() {
        return ThreadLocalRandom.current().nextInt(100,1000);
    }

    private static int generateBranchCode() {
        return ThreadLocalRandom.current().nextInt(1000,10000);
    }

    private static Map<String, Double> generateInterestRates() {
        Map<String, Double> rates = new HashMap<>();
        rates.put("1 month", ThreadLocalRandom.current().nextDouble(1,2));
        rates.put("3 months", ThreadLocalRandom.current().nextDouble(2,3));
        rates.put("6 months", ThreadLocalRandom.current().nextDouble(3,4));
        rates.put("12 months", ThreadLocalRandom.current().nextDouble(4,5));

        return rates;
    }

    public Map<String, Double> getInterestRates() {
        return interestRates;
    }

    public void adjustBonusInit(double exchangeRate) {
        if (exchangeRate != 1.0) {
            this.bonusInit = this.bonusInit * exchangeRate;
        }
    }

    public static BankingEntities fromDescription(String description) {
        for (BankingEntities entity : BankingEntities.values()) {
            if (entity.getDescription().equalsIgnoreCase(description)) {
                return entity;
            }
        }

        throw new IllegalArgumentException("No matching BankEntity for description: " + description);
    }

    public static BankingEntities fromUrlDescription(String urlDescription) {
        String normalizedDescription = urlDescription.replace("-", "_").toUpperCase();

        return BankingEntities.valueOf(normalizedDescription);
    }
}
