package univ.paris13.lee.app.controller;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.Base64;

public class ControllerUtils {

    private static final String CLIENT_ID = "lee";
    private static final String CLIENT_SECRET = "lee-secret";
    private static final String REDIRECT_URI = "https://localhost:8443/code";
    private static final String RESOURCE_SERVER_BASE_URI = "https://localhost:8888";

    public static void setSessionAttribute(String key, String value) {
        final ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        final HttpSession session = attr.getRequest().getSession(true);
        session.setAttribute(key, value);
    }

    static String authentication() {
        final String credentials = CLIENT_ID + ":" + CLIENT_SECRET;
        return "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());
    }

    static String authorizeURL() {
        return String.format("%s/oauth/authorize?response_type=code&client_id=%s&redirect_uri=%s",
                RESOURCE_SERVER_BASE_URI, CLIENT_ID, REDIRECT_URI);
    }

    static String tokenURL(String code) {
        return String.format("%s/oauth/token?code=%s&grant_type=authorization_code&redirect_uri=%s",
                RESOURCE_SERVER_BASE_URI, code, REDIRECT_URI);
    }

}
