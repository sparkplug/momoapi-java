package ug.sparkpl.momoapi;


import ug.sparkpl.momoapi.models.Balance;
import ug.sparkpl.momoapi.network.RequestOptions;
import ug.sparkpl.momoapi.network.disbursements.DisbursementsClient;
import ug.sparkpl.momoapi.network.remittances.RemittancesClient;

import java.io.IOException;

public class MomoApi {

    MomoApi() {

    }

    public static void main(String[] args) {

        RequestOptions opts = RequestOptions.builder().build();
        DisbursementsClient client = new DisbursementsClient(opts);

        RemittancesClient rclient = new RemittancesClient(opts);


        try {
            Balance bl = rclient.getBalance();

            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&");

            System.out.println(bl.getBalance());


        } catch (IOException e) {
            System.out.println(e.toString());

        }


    }


}
