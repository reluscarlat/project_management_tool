package com.resc.pmtool.security;

public class SecurityConstants {
    public static final String SIGN_UP_URLS = "/api/users/**";
    public static final String H2_URL = "h2-console/**";
    public static final String SECRET = "SecretKeyToGenerateJWTs";
    public static final String TOKEN_PREFIX = "Bearer "; // !!! There has to be a space at the end of the string.
    public static final String HEADER_STRING = "Authorization";
    public static final long EXPIRATION_TIME = 300_000; // <=> 30 seconds

}
