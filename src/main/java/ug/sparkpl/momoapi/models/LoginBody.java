package ug.sparkpl.momoapi.models;

public class LoginBody {
    private String user_id;
    private String api_key;

    public LoginBody(String user_id, String api_key) {
        this.user_id = user_id;
        this.api_key = api_key;
    }
}