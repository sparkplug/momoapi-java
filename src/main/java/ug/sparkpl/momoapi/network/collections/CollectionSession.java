package ug.sparkpl.momoapi.network.collections;

import java.util.prefs.Preferences;

public class CollectionSession {
    String TOKEN_NAME = "TOKEN";
    private String token;
    private Preferences prefs;

    public CollectionSession() {
        this.prefs = Preferences.userRoot().node(this.getClass().getName());
    }


    public void saveToken(String token) {
        prefs.put(TOKEN_NAME, token);
    }


    public String getToken() {
        // return the token that was saved earlier
        return prefs.get(TOKEN_NAME, "");
    }


    public void invalidate() {

    }
}


