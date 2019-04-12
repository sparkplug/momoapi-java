package ug.sparkpl.momoapi.models;

import lombok.Getter;
import lombok.Setter;

public class RequestToPay {

    public RequestToPay( String mobile,
                          String amount,
                          String external_id,
                          String payee_note,
                          String payer_message,
                          String currency){
        this.payer = new Payer(mobile,"MSIDN");
        this.amount=amount;
        this.externalId=external_id;
        this.payerMessage=payer_message;
        this.payeeNote=payee_note;
        this.currency=currency;


    }

    @Getter @Setter private Payer payer;
    @Getter @Setter private String payeeNote;
    @Getter @Setter private String payerMessage;
    @Getter @Setter private String externalId;
    @Getter @Setter private String currency;
    @Getter @Setter private String amount;



}
