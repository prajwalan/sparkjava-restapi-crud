package com.crm.api.integrationtest;

import java.io.IOException;
import java.lang.reflect.Type;

import org.eclipse.jetty.http.HttpStatus;
import org.jose4j.lang.JoseException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.crm.api.DemoStorage;
import com.crm.api.ServiceMain;
import com.crm.api.response.EntityResponse;
import com.crm.api.response.ListResponse;
import com.crm.db.model.Customer;
import com.google.gson.reflect.TypeToken;

import spark.Spark;

public class CustomerControllerIT extends BaseIntegrationTest {

    private Type listType = new TypeToken<ListResponse<Customer>>() {
    }.getType();
    private Type customerEntityType = new TypeToken<EntityResponse<Customer>>() {
    }.getType();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        BaseIntegrationTest.setUpBeforeClass();
        ServiceMain.main(null);
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        Spark.stop();
    }

    @Before
    public void setUp() throws Exception {
        DemoStorage.reset();
    }

    @Test
    public void testGetCustomerWithouLogin() throws IOException {
        TestHttpResponse res = request("GET", "/customers", null);

        Assert.assertEquals(HttpStatus.FORBIDDEN_403, res.status);
    }

    @Test
    public void testGetCustomer() throws IOException, JoseException {
        String token = login();
        TestHttpResponse res = request("GET", "/customers?token=" + token, null);

        Assert.assertEquals(HttpStatus.OK_200, res.status);
        ListResponse<Customer> response = jsonConverter.fromJson(res.body, listType);
        Assert.assertEquals(2, response.getResult().size());
    }

    @Test
    public void testAddCustomer() throws IOException, JoseException {
        String token = login();
        Customer entity = new Customer(-1, "testuserA", "testlastnameA", "testaddress", "testcity", "testcountry",
                "user@domain.com", "12345678", 1005);

        TestHttpResponse res = request("POST", "/customers?token=" + token, jsonConverter.toJson(entity));

        Assert.assertEquals(HttpStatus.CREATED_201, res.status);
        EntityResponse<Customer> response = jsonConverter.fromJson(res.body, customerEntityType);
        Assert.assertEquals(3, response.getResult().getId());
        Assert.assertEquals("testuserA", response.getResult().getFirstName());
    }

}
