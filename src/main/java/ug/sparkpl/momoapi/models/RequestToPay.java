package ug.sparkpl.momoapi.models;

import com.google.gson.annotations.SerializedName;

public class RequestToPay {

    @SerializedName("payer")
    private Payer payer;
    @SerializedName("payeeNote")
    private String payeeNote;
    @SerializedName("payerMessage")
    private String payerMessage;
    @SerializedName("externalId")
    private String externalId;
    private String currency;
    private String amount;

    public RequestToPay(String mobile,
                        String amount,
                        String external_id,
                        String payee_note,
                        String payer_message,
                        String currency) {
        this.payer = new Payer(mobile, "MSISDN");
        this.amount = amount;
        this.externalId = external_id;
        this.payerMessage = payer_message;
        this.payeeNote = payee_note;
        this.currency = currency;


    }


}

