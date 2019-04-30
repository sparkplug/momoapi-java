package ug.sparkpl.momoapi.models;

import com.google.gson.annotations.SerializedName;

public class Balance {
  String description;

  @SerializedName("availableBalance")
  String availableBalance;

  String currency;

  /**
   * The available balance of the account.
   *
   * @param description      String
   * @param availableBalance String The available balance of the account
   * @param currency         String ISO4217 Currency
   */
  public Balance(String description, String availableBalance, String currency) {
    this.description = description;
    this.currency = currency;
    this.availableBalance = availableBalance;
  }

  /**
   * Get Available Balance.
   *
   * @return String available Balances
   */
  public String getBalance() {
    return this.availableBalance;
  }
}
