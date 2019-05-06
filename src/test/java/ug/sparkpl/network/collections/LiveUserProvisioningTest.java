package ug.sparkpl.network.collections;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import ug.sparkpl.momoapi.models.NewUser;
import ug.sparkpl.momoapi.models.User;
import ug.sparkpl.momoapi.network.collections.CollectionsApiService;
import ug.sparkpl.momoapi.utils.DateTimeTypeConverter;

import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LiveUserProvisioningTest {


  /**
   * Test user account provisioning.
   *
   * @throws IOException when network error.
   */
  @Test
  public void testUserProvisioning() throws IOException {

    String baseUrl = "https://ericssonbasicapi2.azure-api.net";
    final CollectionsApiService apiService;
    Gson gson = new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .registerTypeAdapter(DateTime.class, new DateTimeTypeConverter())
        .create();


    final OkHttpClient.Builder okhttpbuilder = new OkHttpClient.Builder();

    okhttpbuilder.connectTimeout(30, TimeUnit.SECONDS);
    okhttpbuilder.readTimeout(30, TimeUnit.SECONDS);
    okhttpbuilder.writeTimeout(30, TimeUnit.SECONDS);


    OkHttpClient httpClient = okhttpbuilder
        .build();


    Retrofit retrofitClient = new Retrofit.Builder()
        .client(httpClient)
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();

    apiService = retrofitClient.create(CollectionsApiService.class);

    String token = UUID.randomUUID().toString();

    Response<Void> res = apiService
        .provisonUser(System.getenv("COLLECTION_PRIMARY_KEY"), token, new NewUser("ubuntudata.com"))
        .execute();

    Response<User> user = apiService.getUser(token, System.getenv("COLLECTION_PRIMARY_KEY")).execute();
    assertNotNull(user);


    assertNotNull(user.body().getApiKey());


  }
}
