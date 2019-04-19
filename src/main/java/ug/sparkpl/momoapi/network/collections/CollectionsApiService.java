package ug.sparkpl.momoapi.network.collections;

import retrofit2.Call;
import retrofit2.http.*;
import ug.sparkpl.momoapi.models.AccessToken;
import ug.sparkpl.momoapi.models.Balance;
import ug.sparkpl.momoapi.models.RequestToPay;
import ug.sparkpl.momoapi.models.RequesttopayResponse;

public interface CollectionsApiService {


    @POST("/collection/v1_0/requesttopay")
    @Headers("Content-Type: application/json")
    Call<RequesttopayResponse> requestToPay(@Body RequestToPay body, @Header("X-Reference-Id") String ref);


    @POST("/collection/token/")
    @Headers("Content-Type: application/json")
    Call<AccessToken> getToken(@Header("Authorization") String credentials, @Header("Ocp-Apim-Subscription-Key") String subscriptionKey);

    /**
     * Get Account Balance
     */
    @Headers("Content-Type: application/json")
    @GET("/collection/v1_0/account/balance")
    Call<Balance> getBalance();


    @GET("/collection/v1_0/requesttopay/{transaction_id}")
    @Headers("Content-Type: application/json")
    Call<RequesttopayResponse> getTransactionStatus(@Path("transaction_id") String transaction_id);

}
