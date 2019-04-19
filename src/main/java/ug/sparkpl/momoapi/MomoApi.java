package ug.sparkpl.momoapi;


import retrofit2.Response;
import ug.sparkpl.momoapi.models.RequesttopayResponse;
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
            String ref = client.requestToPay("256794631873", "456", "234", "dd", "rty", "EUR");


            System.out.println(ref);

            Response<RequesttopayResponse> re = client.getTransactionStatus(ref).execute();
            RequesttopayResponse b = re.body();
            System.out.println(b.getStatus());

        } catch (IOException e) {

        }


    }


}
