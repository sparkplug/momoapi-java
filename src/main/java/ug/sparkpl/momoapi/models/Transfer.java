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

    public Transfer(String mobile,
                    String amount,
                    String external_id,
                    String payee_note,
                    String payer_message,
                    String currency) {
        this.payee = new Payer(mobile, "MSISDN");
        this.amount = amount;
        this.externalId = external_id;
        this.payerMessage = payer_message;
        this.payeeNote = payee_note;
        this.currency = currency;

    }
}



