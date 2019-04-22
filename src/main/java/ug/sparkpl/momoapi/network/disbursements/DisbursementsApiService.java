package ug.sparkpl.momoapi.network.disbursements;

import retrofit2.Call;
import retrofit2.http.*;
import ug.sparkpl.momoapi.models.*;

public interface DisbursementsApiService {

    @POST("/disbursement/v1_0/transfer/")
    @Headers("Content-Type: application/json")
    Call<Void> transfer(@Body Transfer body, @Header("X-Reference-Id") String ref);


    @POST("/disbursement/token/")
    @Headers("Content-Type: application/json")
    Call<AccessToken> getToken(@Header("Authorization") String credentials, @Header("Ocp-Apim-Subscription-Key") String subscriptionKey);

    /**
     * Get Account Balance
     */
    @Headers("Content-Type: application/json")
    @GET("/disbursement/v1_0/account/balance")
    Call<Balance> getBalance();


    @GET("/disbursement/v1_0/transfer/{transaction_id}")
    @Headers("Content-Type: application/json")
    Call<Transaction> getTransactionStatus(@Path("transaction_id") String transaction_id);


    @GET("/v1_0/accountholder/{accountHolderIdType}/{accountHolderId}/active")
    @Headers("Content-Type: application/json")
    Call<Account> isActive(@Path("accountHolderIdType") String accountHolderIdType, @Path("accountHolderId") String accountHolderId);

}
