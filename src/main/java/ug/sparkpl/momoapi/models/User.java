package ug.sparkpl.momoapi.models;

import com.google.gson.annotations.SerializedName;

public class User {
    private static AccessToken collectionsToken;
    private static AccessToken disbursementsToken;

    @SerializedName("apiKey")
    private String apiKey;

    public String getApiKey() {
        return this.apiKey;
    }

}
