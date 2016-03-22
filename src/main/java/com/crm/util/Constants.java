package com.crm.util;

public class Constants {

    public static int RESPONSE_OK = 0;
    public static int RESPONSE_ERROR = -1;

    public static String STANDARD_RESPONSE_CONTENTTYPE = "application/json;charset=UTF-8";

    public static String URL_PARAM_TOKEN = "token";

    // -- In real application this should not be constant in source code like this.
    // -- This way of putting secret in constant is strictly only for demo purpose.
    public static String TOKEN_SECRET = "572247f3042d4add9397333a172680143d7363ace4";
    public static long TOKEN_TTL_MS = 8 * 60 * 60 * 1000; // 8 hours
}
