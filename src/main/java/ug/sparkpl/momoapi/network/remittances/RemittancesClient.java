package ug.sparkpl.momoapi.network.remittances;

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
import ug.sparkpl.momoapi.models.Transaction;
import ug.sparkpl.momoapi.models.Transfer;
import ug.sparkpl.momoapi.network.RequestOptions;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class RemittancesClient {
    RequestOptions opts;
    Gson gson;
    private RemittancesSession session;
    private RemittancesApiService apiService;
    private OkHttpClient httpClient;
    private Retrofit retrofitClient;
    private Retrofit client;


    public RemittancesClient(RequestOptions opts) {
        this.opts = opts;
        this.gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(DateTime.class, new DateTimeTypeConverter())
                .create();

        this.session = new RemittancesSession();

        final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        final OkHttpClient.Builder okhttpbuilder = new OkHttpClient.Builder();

        // Only log in debug mode to avoid leaking sensitive information.


        okhttpbuilder.addInterceptor(new RemittancesAuthorizationInterceptor(this.session, this.opts));
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

        this.apiService = this.retrofitClient.create(RemittancesApiService.class);


    }


    public AccessToken getToken() throws IOException {
        String credentials = Credentials.basic(this.opts.getRemittanceUserId(), this.opts.getRemittanceApiSecret());
        Response<AccessToken> token = this.apiService
                .getToken(credentials, this.opts.getRemittancePrimaryKey()).execute();
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


    public String transfer(String mobile, String amount, String external_id, String payee_note, String payer_message, String currency) throws IOException {
        Transfer rBody = new Transfer(mobile, amount, external_id, payee_note, payer_message, currency);
        String ref = UUID.randomUUID().toString();
        this.apiService.transfer(rBody, ref).execute();
        return ref;

    }

}
