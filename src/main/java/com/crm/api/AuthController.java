package com.crm.api;

import static spark.Spark.*;

import com.crm.api.response.Response;
import com.crm.model.UserLogin;
import com.crm.util.AuthServiceUtil;
import com.crm.util.CommonUtil;
import com.crm.util.Constants;
import com.google.gson.Gson;

public class AuthController extends BaseController {

    public AuthController(final Gson jsonConverter) {
        super(jsonConverter);
        initializeController(jsonConverter);
    }

    public void initializeController(Gson jsonConverter) {

        // -- login
        post("/login", (req, res) -> {
            validateContentType(req);

            String payload = req.body();
            UserLogin userlogin = jsonConverter.fromJson(payload, UserLogin.class);
            return new Response(AuthServiceUtil.login(userlogin.getUsername(), userlogin.getPassword()));

        } , CommonUtil.getJsonTransformer());

        // -- extend
        get("/extend", (req, res) -> {
            return new Response(AuthServiceUtil.extendToken(req.queryParams(Constants.URL_PARAM_TOKEN)));
        } , CommonUtil.getJsonTransformer());
    }
}
