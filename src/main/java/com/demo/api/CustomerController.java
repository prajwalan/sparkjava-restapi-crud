package com.demo.api;

import static spark.Spark.*;

import org.eclipse.jetty.http.HttpStatus;

import com.demo.api.exception.InvalidPayloadException;
import com.demo.api.exception.NotFoundException;
import com.demo.api.response.Response;
import com.demo.db.model.Customer;
import com.demo.util.CommonUtil;
import com.demo.util.Constants;
import com.google.gson.Gson;

import spark.Request;

public class CustomerController {

    public CustomerController(final CustomerService service, final Gson jsonConverter) {
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
            validate(req);

            return addEntity(service, jsonConverter, req, res);
        } , CommonUtil.getJsonTransformer());

        // -- Update an entity
        post("/customers/:id", (req, res) -> {
            validate(req);

            return updateEntity(service, jsonConverter, req, res);
        } , CommonUtil.getJsonTransformer());

        // -- Delete one specific entity
        delete("/customers/:id", (req, res) -> {
            return deleteOneEntity(service, req, res);
        } , CommonUtil.getJsonTransformer());

        // -- Set proper content-type to all responses
        after((req, res) -> {
            res.type(Constants.STANDARD_RESPONSE_CONTENTTYPE);
        });

        // -- Handle the exceptions
        handleExceptions(jsonConverter);
    }

    public void validate(Request req) throws InvalidPayloadException {
        if (req.contentType() == null || !req.contentType().equalsIgnoreCase("application/json")) {
            throw new InvalidPayloadException("Invalid content type: " + req.contentType());
        }
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

    public void handleExceptions(final Gson jsonConverter) {
        exception(InvalidPayloadException.class, (ex, req, res) -> {
            res.status(HttpStatus.BAD_REQUEST_400);
            res.body(jsonConverter.toJson(new Response(Constants.RESPONSE_ERROR, ex.getMessage())));
        });
        exception(NotFoundException.class, (ex, req, res) -> {
            res.status(HttpStatus.NOT_FOUND_404);
            res.body(jsonConverter.toJson(new Response(Constants.RESPONSE_ERROR, ex.getMessage())));
        });
        exception(Exception.class, (ex, req, res) -> {
            res.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            res.body(jsonConverter.toJson(new Response(Constants.RESPONSE_ERROR, ex.getMessage())));
        });
    }
}
