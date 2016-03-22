package com.crm.api;

import static spark.Spark.*;

import org.eclipse.jetty.http.HttpStatus;

import com.crm.api.exception.NotFoundException;
import com.crm.api.response.Response;
import com.crm.db.model.Customer;
import com.crm.util.CommonUtil;
import com.google.gson.Gson;

import spark.Request;

public class CustomerController extends BaseController {

    public CustomerController(final CustomerService service, final Gson jsonConverter) {
        super(jsonConverter);
        initializeController(service, jsonConverter);
    }

    public void initializeController(final CustomerService service, final Gson jsonConverter) {

        // -- Get all entities
        get("/customers", (req, res) -> {

            return getAll(service);
        } , CommonUtil.getJsonTransformer());

        // -- Get one specific entity
        get("/customers/:id", (req, res) -> {
            return getOneEntity(service, req, res);
        } , CommonUtil.getJsonTransformer());

        // -- Add an entity
        post("/customers", (req, res) -> {
            validateContentType(req);

            return addEntity(service, jsonConverter, req, res);
        } , CommonUtil.getJsonTransformer());

        // -- Update an entity
        post("/customers/:id", (req, res) -> {
            validateContentType(req);

            return updateEntity(service, jsonConverter, req, res);
        } , CommonUtil.getJsonTransformer());

        // -- Delete one specific entity
        delete("/customers/:id", (req, res) -> {
            return deleteOneEntity(service, req, res);
        } , CommonUtil.getJsonTransformer());

    }

    public Response getAll(final CustomerService service) {
        return new Response(service.getAll());
    }

    public Response getOneEntity(final CustomerService service, Request req, spark.Response res) throws NotFoundException {
        int id = Integer.parseInt(req.params(":id"));
        return new Response(service.get(id));
    }

    public Response addEntity(final CustomerService service, final Gson jsonConverter, Request req, spark.Response res) {
        String payload = req.body();
        Customer entityToAdd = jsonConverter.fromJson(payload, Customer.class);
        Customer addedEntity = service.add(entityToAdd);

        res.status(HttpStatus.CREATED_201);
        return new Response(addedEntity);
    }

    public Response updateEntity(final CustomerService service, final Gson jsonConverter, Request req, spark.Response res)
            throws NotFoundException {
        int id = Integer.parseInt(req.params(":id"));
        String payload = req.body();
        Customer entityToUpdate = jsonConverter.fromJson(payload, Customer.class);
        Customer udpatedEntity = service.update(id, entityToUpdate);

        return new Response(udpatedEntity);
    }

    private Object deleteOneEntity(final CustomerService service, Request req, spark.Response res) throws NotFoundException {
        int id = Integer.parseInt(req.params(":id"));
        service.delete(id);
        return new Response(0, "");
    }

}
