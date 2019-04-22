package ug.sparkpl.momoapi.models;

import com.google.gson.annotations.SerializedName;

public class Account {
    @SerializedName("availableBalance")
    private String availableBalance;
    private String currency;

    Account(String availableBalance, String currency) {
        this.currency = currency;
        this.availableBalance = availableBalance;
    }
}
