package com.crm.api;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.crm.api.CustomerService;
import com.crm.api.DemoStorage;
import com.crm.api.exception.NotFoundException;
import com.crm.db.model.Customer;

public class CustomerServiceTest {

    private CustomerService service;

    @Before
    public void setUp() throws Exception {
        DemoStorage.reset();
        service = new CustomerService();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetAll() {
        List<Customer> result = service.getAll();

        Assert.assertEquals(2, result.size());
        Assert.assertTrue(result.contains(DemoStorage.customerMap.get(1)));
        Assert.assertTrue(result.contains(DemoStorage.customerMap.get(2)));
    }

    @Test
    public void testGet() throws NotFoundException {
        Customer customer = service.get(1);

        Assert.assertNotNull(customer);
        Assert.assertEquals(1, customer.getId());
    }

    @Test
    public void testGetInvalid() {
        try {
            service.get(100);
        }
        catch (NotFoundException e) {
            return;
        }

        Assert.fail("NotFoundException was not thrown");
    }

    @Test
    public void testAdd() throws NotFoundException {
        Customer entity = new Customer(-1, "testuser3", "testlastname3", "testaddress", "testcity", "testcountry",
                "user@domain.com", "12345678", 1005);

        service.add(entity);

        Customer result = service.get(3);
        Assert.assertNotNull(result);
        Assert.assertEquals("testuser3", result.getFirstName());
    }

    @Test
    public void testUpdate() throws NotFoundException {
        Customer entity = new Customer(1, "editedFistname", "editedLastname", "testaddress", "testcity", "testcountry",
                "user@domain.com", "12345678", 1005);

        service.update(1, entity);

        Customer result = service.get(1);
        Assert.assertNotNull(result);
        Assert.assertEquals("editedFistname", result.getFirstName());
        Assert.assertEquals("editedLastname", result.getLastName());
        Assert.assertEquals("testaddress", result.getAddress());
    }

    @Test
    public void testDelete() throws NotFoundException {
        service.delete(1);

        List<Customer> result = service.getAll();
        Assert.assertEquals(1, result.size());
        Assert.assertFalse(result.contains(DemoStorage.customerMap.get(1)));
        Assert.assertTrue(result.contains(DemoStorage.customerMap.get(2)));
    }

}
