package ug.sparkpl.momoapi.models;

public class AccessToken {
    private String access_token;
    private String token_type;
    private Integer expires_in;

    public AccessToken(String access_token, String token_type, Integer expires_in) {
        this.access_token = access_token;
        this.token_type = token_type;
        this.expires_in = expires_in;
    }

    public String getToken() {
        return this.access_token;
    }
}
