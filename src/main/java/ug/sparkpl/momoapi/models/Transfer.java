package ug.sparkpl.momoapi.models;

import com.google.gson.annotations.SerializedName;

public class Transfer {
  @SerializedName("payee")
  private Payer payee;
  @SerializedName("payeeNote")
  private String payeeNote;
  @SerializedName("payerMessage")
  private String payerMessage;
  @SerializedName("externalId")
  private String externalId;
  private String currency;
  private String amount;

  /**
   * Transfer operation is used to transfer an
   * amount from the owner’s account to a payee account.
   *
   * @param mobile       String Mobile number to transfer to
   * @param amount       Amount that will be debited from the payer account.
   * @param externalId   External id is used as a reference to the transaction.
   *                     External id is used for reconciliation. The external id
   *                     will be included in transaction history report.
   *                     External id is not required to be unique.
   * @param payeeNote    Message that will be written in the payee
   *                     transaction history note field.
   * @param payerMessage Message that will be written in the payer
   *                     transaction history message field.
   * @param currency     ISO4217 Currency
   */
  public Transfer(String mobile,
                  String amount,
                  String externalId,
                  String payeeNote,
                  String payerMessage,
                  String currency) {
    this.payee = new Payer(mobile, "MSISDN");
    this.amount = amount;
    this.externalId = externalId;
    this.payerMessage = payerMessage;
    this.payeeNote = payeeNote;
    this.currency = currency;

  }
}



