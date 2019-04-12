package ug.sparkpl.momoapi.network.collections;

import com.google.gson.Gson;
import lombok.NonNull;
import okhttp3.OkHttpClient;
import rx.Observable;
import rx.schedulers.Schedulers;
import ug.sparkpl.momoapi.Utils.rx.ApiErrorOperator;
import ug.sparkpl.momoapi.Utils.rx.Operators;
import ug.sparkpl.momoapi.models.*;

public class CollectionsClient  {

    private CollectionsApiService service;
    private Gson gson;
    private String COLLECTION_USER_ID;
    private String COLLECTION_API_SECRET;
    private String COLLECTION_PRIMARY_KEY;



    private OkHttpClient httpClient;


    public CollectionsClient(final @NonNull CollectionsApiService service, final @NonNull Gson gson)  {



        this.gson = gson;
        this.service = service;
        this.COLLECTION_API_SECRET = System.getenv("COLLECTION_API_SECRET");
        this.COLLECTION_PRIMARY_KEY = System.getenv("COLLECTION_PRIMARY_KEY");
        this.COLLECTION_USER_ID = System.getenv("COLLECTION_USER_ID");

        /*

        if(this.COLLECTION_USER_ID == null)
        {
            throw new ImproperlyConfiguredException("Please ensure the environment variable COLLECTION_USER_ID is set");
        }

        if(this.COLLECTION_PRIMARY_KEY == null)
        {
            throw new ImproperlyConfiguredException("Please ensure the environment variable COLLECTION_PRIMARY_KEY is set");
        }

        if(this.COLLECTION_API_SECRET == null)
        {
            throw new ImproperlyConfiguredException("Please ensure the environment variable COLLECTION_API_SECRET is set");
        }

        */



    }


    public @NonNull
    Observable<AccessToken> getToken() {
        return service
                .getToken(new LoginBody(this.COLLECTION_USER_ID, this.COLLECTION_API_SECRET))
                .lift(apiErrorOperator())
                .subscribeOn(Schedulers.io());
    }


    public @NonNull
    Observable<RequesttopayResponse> requestToPay(String mobile,String amount,String external_id,String payee_note,String payer_message,String currency) {
        RequestToPay rBody = new RequestToPay(mobile,amount,external_id,payee_note,payer_message,currency);
        return service
                .requestToPay(rBody)
                .lift(apiErrorOperator())
                .subscribeOn(Schedulers.io());
    }

    public @NonNull
    Observable<Balance> getBalance(final @NonNull String username, final @NonNull String password) {
        return service
                .getBalance()
                .lift(apiErrorOperator())
                .subscribeOn(Schedulers.io());
    }


    private @NonNull
    <T> ApiErrorOperator<T> apiErrorOperator() {
        return Operators.apiError(gson);
    }

}
