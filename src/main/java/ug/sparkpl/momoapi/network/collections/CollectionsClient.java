package ug.sparkpl.momoapi.network.collections;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.joda.time.DateTime;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ug.sparkpl.momoapi.Utils.DateTimeTypeConverter;
import ug.sparkpl.momoapi.models.AccessToken;
import ug.sparkpl.momoapi.models.Balance;
import ug.sparkpl.momoapi.models.RequestToPay;
import ug.sparkpl.momoapi.network.RequestOptions;

import java.io.IOException;
import java.util.UUID;
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

        this.session = new CollectionSession();

        final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        final OkHttpClient.Builder okhttpbuilder = new OkHttpClient.Builder();

        // Only log in debug mode to avoid leaking sensitive information.

        okhttpbuilder.addInterceptor(httpLoggingInterceptor);


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


    public Call<AccessToken> getToken() {
        String credentials = Credentials.basic(this.opts.getCollectionUserId(), this.opts.getCollectionApiSecret());
        return apiService
                .getToken(credentials, this.opts.getCollectionPrimaryKey());
    }


    public Call<Balance> getBalance() {
        return apiService
                .getBalance();

    }


    String requestToPay(String mobile, String amount, String external_id, String payee_note, String payer_message, String currency) throws IOException {
        RequestToPay rBody = new RequestToPay(mobile, amount, external_id, payee_note, payer_message, currency);
        String ref = UUID.randomUUID().toString();
        apiService.requestToPay(rBody, ref).execute();
        return ref;

    }


}
