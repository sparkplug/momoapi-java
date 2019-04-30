package ug.sparkpl.momoapi.network.collections;

import ug.sparkpl.momoapi.models.AccessToken;
import ug.sparkpl.momoapi.models.Account;
import ug.sparkpl.momoapi.models.Balance;
import ug.sparkpl.momoapi.models.NewUser;
import ug.sparkpl.momoapi.models.RequestToPay;
import ug.sparkpl.momoapi.models.Transaction;
import ug.sparkpl.momoapi.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface CollectionsApiService {


  /**
   * Is Active.
   *
   * @param accountHolderIdType String
   * @param accountHolderId     String
   * @return Account
   */
  @GET("/v1_0/accountholder/{accountHolderIdType}/{accountHolderId}/active")
  @Headers("Content-Type: application/json")
  Call<Account> isActive(@Path("accountHolderIdType") String accountHolderIdType,
                         @Path("accountHolderId") String accountHolderId);

  /**
   * requestToPay.
   *
   * @param body RequestBody
   * @param ref  String
   * @return Void
   */
  @POST("/collection/v1_0/requesttopay")
  @Headers("Content-Type: application/json")
  Call<Void> requestToPay(@Body RequestToPay body,
                          @Header("X-Reference-Id") String ref);

  /**
   * getToken.
   *
   * @param credentials     String
   * @param subscriptionKey String
   * @return AccessToken
   */
  @POST("/collection/token/")
  @Headers("Content-Type: application/json")
  Call<AccessToken> getToken(@Header("Authorization") String credentials,
                             @Header("Ocp-Apim-Subscription-Key") String subscriptionKey);

  /**
   * Get Account Balance.
   *
   * @return Balance Object
   */
  @Headers("Content-Type: application/json")
  @GET("/collection/v1_0/account/balance")
  Call<Balance> getBalance();


  /**
   * Get Transaction Status.
   *
   * @param transactionId String
   * @return Transaction object
   */
  @GET("/collection/v1_0/requesttopay/{transactionId}")
  @Headers("Content-Type: application/json")
  Call<Transaction> getTransactionStatus(@Path("transactionId") String transactionId);


  /**
   * provision User Account.
   *
   * @param key   String
   * @param token String
   * @param body  NewUser
   * @return Void
   */
  @POST("https://ericssonbasicapi2.azure-api.net/v1_0/apiuser")
  @Headers("Content-Type: application/json")
  Call<Void> provisonUser(@Header("Ocp-Apim-Subscription-Key") String key,
                          @Header("X-Reference-Id") String token,
                          @Body NewUser body);


  /**
   * getUser.
   *
   * @param token String
   * @param key   String
   * @return User
   */
  @POST("https://ericssonbasicapi2.azure-api.net/v1_0/apiuser/{token}/apikey")
  @Headers("Content-Type: application/json")
  Call<User> getUser(@Path("token") String token,
                     @Header("Ocp-Apim-Subscription-Key") String key);

}
