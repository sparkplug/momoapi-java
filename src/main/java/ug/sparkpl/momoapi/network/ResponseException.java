package ug.sparkpl.momoapi.network;

import lombok.NonNull;

public class ResponseException extends RuntimeException {
  private final okhttp3.Response response;

  public ResponseException(final @NonNull okhttp3.Response response) {
    this.response = response;
  }

  public @NonNull
  okhttp3.Response response() {
    return response;
  }
}
