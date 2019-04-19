package ug.sparkpl.momoapi.models;

public class RequesttopayResponse {
    private float amount;
    private String currency;
    private String financialTransactionId;
    private String externalId;
    private Payer payer;
    private String status;

    public String getStatus() {
        return this.status;
    }
}
