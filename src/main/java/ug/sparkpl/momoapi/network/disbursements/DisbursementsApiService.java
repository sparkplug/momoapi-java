package ug.sparkpl.momoapi.network.disbursements;

import ug.sparkpl.momoapi.models.AccessToken;
import ug.sparkpl.momoapi.models.Account;
import ug.sparkpl.momoapi.models.Balance;
import ug.sparkpl.momoapi.models.Transaction;
import ug.sparkpl.momoapi.models.Transfer;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DisbursementsApiService {

  /**
   * transfer request.
   *
   * @param body Transfer
   * @param ref  String
   * @return Void
   */
  @POST("/disbursement/v1_0/transfer")
  @Headers("Content-Type: application/json")
  Call<Void> transfer(@Body Transfer body, @Header("X-Reference-Id") String ref);


  /**
   * getToken.
   *
   * @param credentials     String
   * @param subscriptionKey String
   * @return AccessToken
   */
  @POST("/disbursement/token/")
  @Headers("Content-Type: application/json")
  Call<AccessToken> getToken(@Header("Authorization") String credentials,
                             @Header("Ocp-Apim-Subscription-Key") String subscriptionKey);

  /**
   * Get Account Balance.
   *
   * @return Balance
   */
  @Headers("Content-Type: application/json")
  @GET("/disbursement/v1_0/account/balance")
  Call<Balance> getBalance();


  /**
   * getTransactionStatus.
   *
   * @param transactionId String
   * @return Transaction
   */
  @GET("/disbursement/v1_0/transfer/{transactionId}")
  @Headers("Content-Type: application/json")
  Call<Transaction> getTransactionStatus(@Path("transactionId") String transactionId);


  /**
   * isActive.
   *
   * @param accountHolderIdType String
   * @param accountHolderId     String
   * @return Account
   */
  @GET("/v1_0/accountholder/{accountHolderIdType}/{accountHolderId}/active")
  @Headers("Content-Type: application/json")
  Call<Account> isActive(@Path("accountHolderIdType") String accountHolderIdType,
                         @Path("accountHolderId") String accountHolderId);

}
