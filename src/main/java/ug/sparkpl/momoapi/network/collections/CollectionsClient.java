package ug.sparkpl.momoapi.network.collections;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.joda.time.DateTime;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import ug.sparkpl.momoapi.Utils.DateTimeTypeConverter;
import ug.sparkpl.momoapi.models.AccessToken;
import ug.sparkpl.momoapi.models.Balance;
import ug.sparkpl.momoapi.models.RequestToPay;
import ug.sparkpl.momoapi.models.Transaction;
import ug.sparkpl.momoapi.network.RequestOptions;

import java.io.IOException;
import java.util.HashMap;
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
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        final OkHttpClient.Builder okhttpbuilder = new OkHttpClient.Builder();

        // Only log in debug mode to avoid leaking sensitive information.


        okhttpbuilder.addInterceptor(new CollectionsAuthorizationInterceptor(this.session, this.opts));
        okhttpbuilder.addInterceptor(httpLoggingInterceptor);


        okhttpbuilder.connectTimeout(30, TimeUnit.SECONDS);
        okhttpbuilder.readTimeout(30, TimeUnit.SECONDS);
        okhttpbuilder.writeTimeout(30, TimeUnit.SECONDS);


        this.httpClient = okhttpbuilder
                .build();


        this.retrofitClient = new Retrofit.Builder()
                .client(this.httpClient)
                .baseUrl(opts.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        this.apiService = this.retrofitClient.create(CollectionsApiService.class);


    }


    public AccessToken getToken() throws IOException {
        String credentials = Credentials.basic(this.opts.getCollectionUserId(), this.opts.getCollectionApiSecret());
        Response<AccessToken> token = this.apiService
                .getToken(credentials, this.opts.getCollectionPrimaryKey()).execute();

        return token.body();
    }


    public Balance getBalance() throws IOException {
        Response<Balance> balance = this.apiService
                .getBalance().execute();
        return balance.body();

    }

    public Transaction getTransactionStatus(String ref) throws IOException {
        Response<Transaction> transaction = this.apiService
                .getTransactionStatus(ref).execute();
        return transaction.body();

    }


    public String requestToPay(String mobile, String amount, String external_id, String payee_note, String payer_message, String currency) throws IOException {
        RequestToPay rBody = new RequestToPay(mobile, amount, external_id, payee_note, payer_message, currency);
        String ref = UUID.randomUUID().toString();
        this.apiService.requestToPay(rBody, ref).execute();
        return ref;

    }

    public String requestToPay(HashMap<String, String> opts) throws IOException {
        RequestToPay rBody = new RequestToPay(opts.get("mobile"), opts.get("amount"), opts.get("external_id"), opts.get("payee_note"), opts.get("payer_message"), opts.get("currency"));
        String ref = UUID.randomUUID().toString();
        this.apiService.requestToPay(rBody, ref).execute();
        return ref;

    }


}
