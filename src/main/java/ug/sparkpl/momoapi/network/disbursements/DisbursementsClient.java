package ug.sparkpl.momoapi.network.disbursements;

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
import ug.sparkpl.momoapi.models.AccessToken;
import ug.sparkpl.momoapi.models.Balance;
import ug.sparkpl.momoapi.models.Transaction;
import ug.sparkpl.momoapi.models.Transfer;
import ug.sparkpl.momoapi.network.RequestOptions;
import ug.sparkpl.momoapi.utils.DateTimeTypeConverter;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class DisbursementsClient {
  RequestOptions opts;
  Gson gson;
  private DisbursementsSession session;
  private DisbursementsApiService apiService;
  private OkHttpClient httpClient;
  private Retrofit retrofitClient;

  public DisbursementsClient(RequestOptions opts) {
    this.opts = opts;
    this.gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .registerTypeAdapter(DateTime.class, new DateTimeTypeConverter())
            .create();

    this.session = new DisbursementsSession();

    final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


    final OkHttpClient.Builder okhttpbuilder = new OkHttpClient.Builder();

    // Only log in debug mode to avoid leaking sensitive information.


    okhttpbuilder.addInterceptor(new DisbursementsAuthorizationInterceptor(this.session, this.opts));
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

    this.apiService = this.retrofitClient.create(DisbursementsApiService.class);


  }


  public AccessToken getToken() throws IOException {
    String credentials = Credentials.basic(this.opts.getDisbursementUserId(), this.opts.getDisbursementApiSecret());
    Response<AccessToken> token = this.apiService
            .getToken(credentials, this.opts.getDisbursementPrimaryKey()).execute();
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


  public String transfer(HashMap<String, String> opts) throws IOException {
    Transfer rBody = new Transfer(opts.get("mobile"), opts.get("amount"), opts.get("externalId"), opts.get("payeeNote"), opts.get("payerMessage"), this.opts.getCurrency());
    String ref = UUID.randomUUID().toString();
    this.apiService.transfer(rBody, ref).execute();
    return ref;

  }

}
