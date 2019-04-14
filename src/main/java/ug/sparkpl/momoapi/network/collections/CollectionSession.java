package ug.sparkpl.momoapi.network.collections;

public class CollectionSession {
    private String token;


    public boolean isLoggedIn() {
        // check if token exist or not
        // return true if exist otherwise false
        // assuming that token exists
        return true;
    }


    public void saveToken(String token) {
        // save the token
    }


    public String getToken() {
        // return the token that was saved earlier
        return token;
    }


    public void invalidate() {

    }
}


