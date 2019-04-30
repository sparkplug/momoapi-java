package ug.sparkpl.momoapi.network.remittances;

import java.util.prefs.Preferences;

public class RemittancesSession {
  String TOKEN_NAME = "REMITTANCE_TOKEN";
  private String token;
  private Preferences prefs;

  /**
   *
   */
  public RemittancesSession() {
    this.prefs = Preferences.userRoot().node(this.getClass().getName());
  }


  /**
   * @param token
   */
  public void saveToken(String token) {
    prefs.put(TOKEN_NAME, token);
  }


  /**
   * @return
   */
  public String getToken() {
    // return the token that was saved earlier
    return prefs.get(TOKEN_NAME, "dummy");
  }


}
