package ug.sparkpl.momoapi;


import retrofit2.Response;
import ug.sparkpl.momoapi.models.Balance;
import ug.sparkpl.momoapi.network.RequestOptions;
import ug.sparkpl.momoapi.network.collections.CollectionsClient;

import java.io.IOException;

public class MomoApi {

    MomoApi() {

    }

    public static void main(String[] args) {

        RequestOptions opts = RequestOptions.builder().build();
        CollectionsClient client = new CollectionsClient(opts);


        try {
            Response<Balance> tokenCall = client.getBalance().execute();
            Balance b = tokenCall.body();
            System.out.println(b.getBalance());

        } catch (IOException e) {

        }


        System.out.println("  i ind lcp rnk  select");


    }


}
