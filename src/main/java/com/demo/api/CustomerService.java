package com.demo.api;

import java.util.ArrayList;
import java.util.List;

import com.demo.api.exception.NotFoundException;
import com.demo.db.model.Customer;

public class CustomerService {

    public List<Customer> getAll() {
        return new ArrayList<Customer>(DemoStorage.customerMap.values());
    }

    public Customer get(int id) throws NotFoundException {
        if (!DemoStorage.customerMap.containsKey(id)) {
            throw new NotFoundException("Customer with id " + id + " not found");
        }

        return DemoStorage.customerMap.get(id);
    }

    public Customer add(Customer entity) {

        entity.setId(DemoStorage.nextCustomerId);
        DemoStorage.customerMap.put(entity.getId(), entity);
        DemoStorage.nextCustomerId++;

        return entity;
    }

    public Customer update(int id, Customer entityToUpdate) throws NotFoundException {
        Customer entity = get(id);

        entity.setFirstName(entityToUpdate.getFirstName());
        entity.setLastName(entityToUpdate.getLastName());
        entity.setAddress(entityToUpdate.getAddress());
        entity.setCity(entityToUpdate.getCity());
        entity.setCountry(entityToUpdate.getCountry());
        entity.setEmail(entityToUpdate.getEmail());
        entity.setBusinessPhone(entityToUpdate.getBusinessPhone());
        entity.setZipPostalCode(entityToUpdate.getZipPostalCode());

        return entity;
    }
}
