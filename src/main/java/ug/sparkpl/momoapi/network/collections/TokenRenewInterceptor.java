package ug.sparkpl.momoapi.network.collections;


import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;

public class TokenRenewInterceptor implements Interceptor {
    private CollectionSession session;

    public TokenRenewInterceptor(CollectionSession session) {
        this.session = session;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());

        // if 'x-auth-token' is available into the response header
        // save the new token into session.The header key can be
        // different upon implementation of backend.
        String newToken = response.header("x-auth-token");
        if (newToken != null) {
            session.saveToken(newToken);
        }

        return response;
    }
}