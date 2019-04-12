package ug.sparkpl.momoapi.network.collections;

import retrofit2.Response;
import retrofit2.http.*;
import rx.Observable;
import ug.sparkpl.momoapi.models.*;

public interface CollectionsApiService {


    @POST("/collection/v1_0/requesttopay/")
    @FormUrlEncoded
    Observable<Response<RequesttopayResponse>> requestToPay(@Body RequestToPay body);


    @POST("/collections/token/")
    @Headers("Content-Type: application/json")
    Observable<Response<AccessToken>> getToken(@Body LoginBody params);

    /**
     * Get Account Balance
     */
    @GET("/collection/v1_0/account/balance")
    Observable<Response<Balance>> getBalance();


    @GET("/collection/v1_0/requesttopay/{transaction_id/")
    Observable<Response<User>> approveReject(@Path("transaction_id") String option);

}
