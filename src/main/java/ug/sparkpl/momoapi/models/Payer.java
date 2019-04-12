package ug.sparkpl.momoapi.models;

public class Payer {
     private String partyIdType;
     private String partyId;

     public Payer(String partyId, String partyIdType){
          this.partyId=partyId;
          this.partyIdType=partyIdType;
     }
}
