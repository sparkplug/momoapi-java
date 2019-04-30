package ug.sparkpl.momoapi.models;

import com.google.gson.annotations.SerializedName;

public class AccessToken {

  @SerializedName("access_token")
  private String accessToken;
  @SerializedName("token_type")
  private String tokenType;
  @SerializedName("expiresIn")
  private Integer expires_in;


  /**
   * AccessToken.
   *
   * @param accessToken String
   * @param tokenType   String
   * @param expiresIn   String
   */
  public AccessToken(String accessToken, String tokenType, Integer expiresIn) {
    this.accessToken = accessToken;
    this.tokenType = tokenType;
    this.expires_in = expiresIn;
  }

  /**
   * Get Access Token.
   *
   * @return access Token
   */
  public String getToken() {
    return this.accessToken;
  }
}
