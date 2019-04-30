package ug.sparkpl.momoapi.models;

import com.google.gson.annotations.SerializedName;

public class LoginBody {
  @SerializedName("user_id")
  private String userId;
  @SerializedName("api_key")
  private String apiKey;

  /**
   * LoginBody.
   *
   * @param userId String
   * @param apiKey String
   */
  public LoginBody(String userId, String apiKey) {
    this.userId = userId;
    this.apiKey = apiKey;
  }
}