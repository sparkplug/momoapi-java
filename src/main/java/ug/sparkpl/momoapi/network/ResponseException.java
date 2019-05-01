package ug.sparkpl.momoapi.network;

import okhttp3.Response;

public class ResponseException extends RuntimeException {
  private final Response response;

  /**
   * ResponseException.
   *
   * @param response Response
   */
  public ResponseException(final Response response) {
    this.response = response;
  }

  /**
   * response.
   *
   * @return Response
   */
  public Response response() {
    return response;
  }
}
