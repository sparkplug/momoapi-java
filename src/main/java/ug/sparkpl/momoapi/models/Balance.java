package ug.sparkpl.momoapi.models;

public class Balance {
    String description;

    String availableBalance;

    String currency;

    public Balance(String description, String availableBalance, String currency) {
        this.description = description;
        this.currency = currency;
        this.availableBalance = availableBalance;
    }

    public String getBalance() {
        return this.availableBalance;
    }
}
