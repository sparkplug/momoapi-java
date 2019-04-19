package ug.sparkpl.momoapi.models;

import com.google.gson.annotations.SerializedName;

public class Payer {
    @SerializedName("partyIdType")
    private String partyIdType;
    @SerializedName("partyId")
    private String partyId;

    public Payer(String partyId, String partyIdType) {
        this.partyId = partyId;
        this.partyIdType = partyIdType;
    }
}
