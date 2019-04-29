package ug.sparkpl.momoapi.models;

import com.google.gson.annotations.SerializedName;

public class NewUser {
    @SerializedName("providerCallbackHost")
    private String providerCallbackHost;

    public NewUser(String providerCallbackHost) {
        this.providerCallbackHost = providerCallbackHost;
    }
}
