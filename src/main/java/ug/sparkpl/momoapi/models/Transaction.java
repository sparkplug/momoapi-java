package ug.sparkpl.momoapi.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class Transaction {

  private float amount;
  private String currency;
  @SerializedName("financialTransactionId")
  private String financialTransactionId;
  @SerializedName("externalId")
  private String externalId;
  private Payer payer;
  private String status;
  private Reason reason;


  /**
   * Get Transaction Status.
   *
   * @return String
   */
  public String getStatus() {
    return this.status;
  }

  /**
   * Get Transaction amount.
   *
   * @return String amount
   */
  public float getAmount() {
    return this.amount;
  }

  /**
   * Get transaction currency.
   *
   * @return String currency
   */
  public String getCurrency() {
    return this.currency;
  }

  /**
   * Get Transaction Id.
   *
   * @return String transaction id
   */
  public String getFinancialTransactionId() {
    return this.financialTransactionId;
  }

  /**
   * Get External Ref.
   *
   * @return String
   */

  public String getExternalId() {
    return this.externalId;
  }

  @Override
  public String toString() {
    Gson gson = new Gson();

    return gson.toJson(this);
  }

  class Reason {

    private String code;
    private String message;


  }
}
