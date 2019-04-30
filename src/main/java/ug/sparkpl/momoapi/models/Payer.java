package ug.sparkpl.momoapi.models;

import com.google.gson.annotations.SerializedName;

public class Payer {
  @SerializedName("partyIdType")
  private String partyIdType;
  @SerializedName("partyId")
  private String partyId;

  /**
   * Payer
   * MSISDN - Mobile Number validated according to ITU-T E.164. Validated with IsMSISDN
   * EMAIL - Validated to be a valid e-mail format. Validated with IsEmail
   * PARTY_CODE - UUID of the party. Validated with IsUuid.
   *
   * @param partyId     String  enum[MSISDN, EMAIL, PARTY_CODE]
   * @param partyIdType String
   */
  public Payer(String partyId, String partyIdType) {
    this.partyId = partyId;
    this.partyIdType = partyIdType;
  }
}
