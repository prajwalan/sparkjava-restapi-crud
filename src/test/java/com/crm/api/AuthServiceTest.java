package com.crm.api;

import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.lang.JoseException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.crm.api.exception.InvalidCredentialsException;
import com.crm.api.exception.InvalidTokenException;
import com.crm.util.AuthServiceUtil;
import com.crm.util.Constants;

public class AuthServiceTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testUsername() throws JoseException, InvalidCredentialsException, MalformedClaimException, InterruptedException {

        String username = AuthServiceUtil.getUsername(AuthServiceUtil.getToken("testuser1"));
        Assert.assertEquals("testuser1", username);
    }

    @Test
    public void testUsernameAfterExpiry()
            throws JoseException, InvalidCredentialsException, MalformedClaimException, InterruptedException {

        long originalTTL = Constants.TOKEN_TTL_MS;
        Constants.TOKEN_TTL_MS = 10;

        String token = AuthServiceUtil.getToken("testuser1");
        Thread.sleep(100);
        String username = AuthServiceUtil.getUsername(token);

        Assert.assertNull(username);

        Constants.TOKEN_TTL_MS = originalTTL;
    }

    @Test
    public void extendToken() throws JoseException, MalformedClaimException, InterruptedException, InvalidTokenException {
        long originalTTL = Constants.TOKEN_TTL_MS;
        Constants.TOKEN_TTL_MS = 1000;

        String token = AuthServiceUtil.getToken("testuser1");
        Thread.sleep(5);
        token = AuthServiceUtil.extendToken(token);
        String username = AuthServiceUtil.getUsername(token);

        Assert.assertEquals("testuser1", username);

        Constants.TOKEN_TTL_MS = originalTTL;
    }
}
