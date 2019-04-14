package ug.sparkpl.momoapi.network.collections;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.joda.time.DateTime;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ug.sparkpl.momoapi.Utils.DateTimeTypeConverter;
import ug.sparkpl.momoapi.models.AccessToken;
import ug.sparkpl.momoapi.models.LoginBody;
import ug.sparkpl.momoapi.network.RequestOptions;

import java.util.concurrent.TimeUnit;

public class CollectionsClient {


    RequestOptions opts;
    Gson gson;
    private CollectionSession session;
    private CollectionsApiService apiService;
    private OkHttpClient httpClient;
    private Retrofit retrofitClient;
    private Retrofit client;


    public CollectionsClient(RequestOptions opts) {
        this.opts = opts;
        this.gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(DateTime.class, new DateTimeTypeConverter())
                .create();

        final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        final OkHttpClient.Builder okhttpbuilder = new OkHttpClient.Builder();

        // Only log in debug mode to avoid leaking sensitive information.

        okhttpbuilder.addInterceptor(httpLoggingInterceptor);

        okhttpbuilder.addInterceptor(new TokenRenewInterceptor(this.session));
        okhttpbuilder.addInterceptor(new CollectionsAuthorizationInterceptor(this.session, this.opts));


        okhttpbuilder.connectTimeout(30, TimeUnit.SECONDS);
        okhttpbuilder.readTimeout(30, TimeUnit.SECONDS);
        okhttpbuilder.writeTimeout(30, TimeUnit.SECONDS);


        this.httpClient = okhttpbuilder
                .build();


        this.retrofitClient = new Retrofit.Builder()
                .client(this.httpClient)
                .baseUrl(opts.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        this.apiService = this.retrofitClient.create(CollectionsApiService.class);


    }


    // use a storage option to store the
    // credentails and user info
    // i.e: SQLite, SharedPreference etc.
    public CollectionSession getSession() {

        CollectionSession session = new CollectionSession();

        return session;
    }


    Call<AccessToken> getToken() {
        return apiService
                .getToken(new LoginBody(this.opts.getCollectionUserId(), this.opts.getDisbursementApiSecret()));
    }


   /*

    Call<RequesttopayResponse> requestToPay(String mobile, String amount, String external_id, String payee_note, String payer_message, String currency) {
        RequestToPay rBody = new RequestToPay(mobile, amount, external_id, payee_note, payer_message, currency);
        return service.requestToPay(rBody);

    }


    Call<Balance> getBalance(final @NonNull String username, final @NonNull String password) {
        return service
                .getBalance();

    }


    */


}
