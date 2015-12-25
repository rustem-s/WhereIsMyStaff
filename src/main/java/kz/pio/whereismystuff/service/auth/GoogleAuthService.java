package kz.pio.whereismystuff.service.auth;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * Helper class for Google's OAuth2 authentication API.
 * @version 20140614
 * @author Rustem S
 */
@Service
public final class GoogleAuthService {
    @Autowired
    private MessageSource messageSource;
    /**
     * Value for the Google API CLIENT_ID
     */
    private static final String CLIENT_ID = "803821118760-vmapvmp7bj0suqltc10jhcpagfk87td9.apps.googleusercontent.com";
    /**
     * Value for the Google API CLIENT_SECRET
     */
    private static final String CLIENT_SECRET = "ijm2pvHPd02jkr1VIJkw6bRP";
    /**
     * Callback URI that google will redirect to after successful authentication
     */
    private static final String CALLBACK_URI = "http://localhost:8080/WhereIsMyStuff";
    /**
     * google authentication constants
     */
    private static final Iterable<String> SCOPE = Arrays.asList("https://www.googleapis.com/auth/userinfo.profile;https://www.googleapis.com/auth/userinfo.email".split(";"));
    private static final String USER_INFO_URL = "https://www.googleapis.com/oauth2/v1/userinfo";
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    private String stateToken;
    private final GoogleAuthorizationCodeFlow flow;
    /**
     * Constructor initializes the Google Authorization Code Flow with CLIENT ID, SECRET, and SCOPE
     */
    public GoogleAuthService() {
        flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, CLIENT_ID, CLIENT_SECRET, SCOPE).build();
        generateStateToken();
    }
    /**
     * Builds a login URL based on client ID, secret, callback URI, and scope
     */
    public String buildLoginUrl() {
        final GoogleAuthorizationCodeRequestUrl url = flow.newAuthorizationUrl();
        return url.setRedirectUri(CALLBACK_URI).setState(stateToken).build();
    }
    /**
     * Generates a secure state token
     */
    private void generateStateToken(){
        SecureRandom sr1 = new SecureRandom();
        stateToken = "google;"+sr1.nextInt();
    }
    /**
     * Accessor for state token
     */
    public String getStateToken(){
        return stateToken;
    }
    /**
     * Expects an Authentication Code, and makes an authenticated request for the user's profile information
     * @return JSON formatted user profile information
     * @param authCode authentication code provided by google
     */
    public String getUserInfoJson(final String authCode) {
        String jsonIdentity = "";
        final GoogleTokenResponse response;
        try {
            response = flow.newTokenRequest(authCode).setRedirectUri(CALLBACK_URI).execute();
            final Credential credential = flow.createAndStoreCredential(response, null);
            final HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(credential);
            // Make an authenticated request
            final GenericUrl url = new GenericUrl(USER_INFO_URL);
            final HttpRequest request = requestFactory.buildGetRequest(url);
            request.getHeaders().setContentType("application/json");
            jsonIdentity = request.execute().parseAsString();
        } catch (IOException e) {
            System.out.println("Error on get user info from Google");
        }
        return jsonIdentity;
    }
}