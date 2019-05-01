package ug.sparkpl.network;

import java.io.IOException;

import com.google.common.base.Charsets;
import com.google.common.base.Throwables;

import okhttp3.mockwebserver.MockWebServer;

import static org.jclouds.util.Strings2.toStringAndClose;

public class BaseTest {
  public MockWebServer server;

  /**
   * Create a MockWebServer.
   *
   * @return instance of MockWebServer
   * @throws IOException if unable to start/play server
   */
  public static MockWebServer mockWebServer() throws IOException {
    final MockWebServer server = new MockWebServer();
    server.start();
    return server;
  }


  /**
   * Get the String representation of some resource to be used as payload.
   *
   * @param resource String representation of a given resource
   * @return payload in String form
   */
  public String payloadFromResource(final String resource) {
    try {
      return new String(toStringAndClose(getClass().getResourceAsStream(resource))
          .getBytes(Charsets.UTF_8));
    } catch (IOException e) {
      throw Throwables.propagate(e);
    }
  }

}
