package ug.sparkpl.momoapi.network.disbursements;

import java.util.prefs.Preferences;

public class DisbursementsSession {
    String TOKEN_NAME = "DISBURSEMENT_TOKEN";
    private String token;
    private Preferences prefs;

    public DisbursementsSession() {
        this.prefs = Preferences.userRoot().node(this.getClass().getName());
    }


    public void saveToken(String token) {
        prefs.put(TOKEN_NAME, token);
    }


    public String getToken() {
        // return the token that was saved earlier
        return prefs.get(TOKEN_NAME, "rando");
    }


    public void invalidate() {

    }
}
