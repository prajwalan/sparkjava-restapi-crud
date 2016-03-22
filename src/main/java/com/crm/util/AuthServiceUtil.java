package com.crm.util;

import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.keys.HmacKey;
import org.jose4j.lang.JoseException;

import com.crm.api.DemoStorage;
import com.crm.api.exception.InvalidCredentialsException;
import com.crm.api.exception.InvalidTokenException;
import com.crm.model.Token;
import com.google.gson.Gson;

public abstract class AuthServiceUtil {

    private static Gson jsonConverter = CommonUtil.getJsonConvertor();

    public AuthServiceUtil() {
    }

    public static String login(String username, String password) throws InvalidCredentialsException, JoseException {
        if (!AuthServiceUtil.isValid(username, password)) {
            throw new InvalidCredentialsException("Username or password invalid");
        }

        return AuthServiceUtil.getToken(username);
    }

    public static String getToken(String username) throws JoseException {
        return AuthServiceUtil.generateToken(username);
    }

    public static String extendToken(String previousToken) throws JoseException, InvalidTokenException, MalformedClaimException {

        String username = AuthServiceUtil.getUsername(previousToken);
        if (username == null) {
            throw new InvalidTokenException();
        }

        return AuthServiceUtil.generateToken(username);
    }

    public static String getUsername(String token) throws JoseException, MalformedClaimException {

        if (token == null) {
            return null;
        }

        JsonWebSignature receiverJws = new JsonWebSignature();
        receiverJws.setCompactSerialization(token);
        receiverJws.setKey(new HmacKey(Constants.TOKEN_SECRET.getBytes()));
        boolean signatureVerified = receiverJws.verifySignature();

        if (signatureVerified) {
            String plaintext = receiverJws.getPayload();
            Token login = jsonConverter.fromJson(plaintext, Token.class);

            if (System.currentTimeMillis() - login.getTimestamp() <= Constants.TOKEN_TTL_MS) {
                return login.getUsername();
            }
        }

        return null;
    }

    private static String generateToken(String username) throws JoseException {
        JsonWebSignature jws = new JsonWebSignature();

        Token login = new Token(username, System.currentTimeMillis());

        jws.setHeader("typ", "JWT");
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);
        jws.setPayload(jsonConverter.toJson(login));
        jws.setKey(new HmacKey(Constants.TOKEN_SECRET.getBytes()));
        jws.setDoKeyValidation(false);
        return jws.getCompactSerialization();
    }

    private static boolean isValid(String username, String password) {
        return DemoStorage.username.equalsIgnoreCase(username) && DemoStorage.password.equals(password);
    }

}
