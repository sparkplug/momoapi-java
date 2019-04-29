package ug.sparkpl.momoapi;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import org.apache.commons.cli.*;
import org.joda.time.DateTime;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ug.sparkpl.momoapi.Utils.DateTimeTypeConverter;
import ug.sparkpl.momoapi.models.NewUser;
import ug.sparkpl.momoapi.models.User;
import ug.sparkpl.momoapi.network.collections.CollectionsApiService;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class MomoApi {


    MomoApi() {


    }

    public static void main(String[] args) throws ParseException, IOException {

        String BASE_URL = "https://ericssonbasicapi2.azure-api.net";
        CollectionsApiService apiService;

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
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        apiService = retrofitClient.create(CollectionsApiService.class);

        CommandLineParser parser = new DefaultParser();
        final Options options = new Options();
        options.addOption(new Option("h", "providerCallBackHost", true, "providerCallBackHost"));
        options.addOption(new Option("k", "primaryKey", true, "Ocp-Apim-Subscription-Key"));
        CommandLine cmd = parser.parse(options, args);


        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("MomoApi", options);

        String token = UUID.randomUUID().toString();

        Response<Void> res = apiService
                .provisonUser(cmd.getOptionValue("k"), token, new NewUser(cmd.getOptionValue("h"))).execute();

        Response<User> user = apiService.getUser(token, cmd.getOptionValue("k")).execute();


        System.out.print("{'apiKey':" + user.body().getApiKey() + ", 'UserId': " + token + "}");


    }

}
