package ug.sparkpl.momoapi.network.remittances;

import retrofit2.Call;
import retrofit2.http.*;
import ug.sparkpl.momoapi.models.AccessToken;
import ug.sparkpl.momoapi.models.Balance;
import ug.sparkpl.momoapi.models.Transaction;
import ug.sparkpl.momoapi.models.Transfer;

public interface RemittancesApiService {

    @POST("/remittance/v1_0/transfer/")
    @Headers("Content-Type: application/json")
    Call<Transaction> transfer(@Body Transfer body, @Header("X-Reference-Id") String ref);


    @POST("/remittance/token/")
    @Headers("Content-Type: application/json")
    Call<AccessToken> getToken(@Header("Authorization") String credentials, @Header("Ocp-Apim-Subscription-Key") String subscriptionKey);

    /**
     * Get Account Balance
     */
    @Headers("Content-Type: application/json")
    @GET("/remittance/v1_0/account/balance")
    Call<Balance> getBalance();


    @GET("/remittance/v1_0/transfer/{transaction_id}")
    @Headers("Content-Type: application/json")
    Call<Transaction> getTransactionStatus(@Path("transaction_id") String transaction_id);

}
