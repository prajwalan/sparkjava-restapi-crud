package com.crm.api.integrationtest;

import java.io.IOException;
import java.lang.reflect.Type;

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
import com.crm.util.CommonUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import spark.Spark;

public class CustomerControllerIT extends BaseIntegrationTest {

    private static Gson jsonConverter;
    private Type listType = new TypeToken<ListResponse<Customer>>() {
    }.getType();
    private Type entityType = new TypeToken<EntityResponse<Customer>>() {
    }.getType();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        jsonConverter = CommonUtil.getJsonConvertor();
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
    public void testGetCustomer() throws IOException {
        TestHttpResponse res = request("GET", "/customers", null);

        Assert.assertEquals(200, res.status);
        ListResponse<Customer> response = jsonConverter.fromJson(res.body, listType);
        Assert.assertEquals(2, response.getResult().size());
    }

    @Test
    public void testAddCustomer() throws IOException {
        Customer entity = new Customer(-1, "testuserA", "testlastnameA", "testaddress", "testcity", "testcountry",
                "user@domain.com", "12345678", 1005);

        TestHttpResponse res = request("POST", "/customers", jsonConverter.toJson(entity));

        Assert.assertEquals(201, res.status);
        EntityResponse<Customer> response = jsonConverter.fromJson(res.body, entityType);
        Assert.assertEquals(3, response.getResult().getId());
        Assert.assertEquals("testuserA", response.getResult().getFirstName());
    }

}
