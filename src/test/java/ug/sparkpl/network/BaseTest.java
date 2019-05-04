package ug.sparkpl.network;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import ug.sparkpl.momoapi.utils.DateTimeTypeConverter;

import org.joda.time.DateTime;

import com.google.common.base.Charsets;
import com.google.common.base.Throwables;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static org.jclouds.util.Strings2.toStringAndClose;

public class BaseTest {
  public MockWebServer server;

  /**
   * Create a MockWebServer.
   *
   * @return instance of MockWebServer
   * @throws IOException if unable to start/play server
   */
  public static MockWebServer mockWebServer() throws IOException {
    final MockWebServer server = new MockWebServer();
    server.start();
    return server;
  }


  /**
   * Get the String representation of some resource to be used as payload.
   *
   * @param resource String representation of a given resource
   * @return payload in String form
   */
  public String payloadFromResource(final String resource) {
    try {
      return new String(toStringAndClose(getClass().getResourceAsStream(resource))
          .getBytes(Charsets.UTF_8));
    } catch (IOException e) {
      throw Throwables.propagate(e);
    }
  }


  /**
   * Get gson.
   *
   * @return Gson
   */
  public Gson getGson() {
    return new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .registerTypeAdapter(DateTime.class, new DateTimeTypeConverter())
        .create();
  }

  /**
   * Get retrofit token
   *
   * @param url String
   * @return Retrofit
   */
  public Retrofit getRetrofit(String url) {
    final OkHttpClient.Builder okhttpbuilder = new OkHttpClient.Builder();


    okhttpbuilder.connectTimeout(30, TimeUnit.SECONDS);
    okhttpbuilder.readTimeout(30, TimeUnit.SECONDS);
    okhttpbuilder.writeTimeout(30, TimeUnit.SECONDS);

    OkHttpClient httpClient = okhttpbuilder
        .build();


    return new Retrofit.Builder()
        .client(httpClient)
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create(getGson()))
        .addConverterFactory(ScalarsConverterFactory.create())
        .build();
  }

}
