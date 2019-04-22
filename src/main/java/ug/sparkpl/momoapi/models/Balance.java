package ug.sparkpl.momoapi.models;

import com.google.gson.annotations.SerializedName;

public class Balance {
    String description;

    @SerializedName("availableBalance")
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
