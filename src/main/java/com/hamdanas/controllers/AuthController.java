package com.hamdanas.controllers;

import com.google.gson.Gson;
import com.hamdanas.models.Login;
import com.hamdanas.response.Response;
import com.hamdanas.utilities.AuthServiceUtil;
import com.hamdanas.utilities.CommonUtils;
import com.hamdanas.utilities.Constants;

import static spark.Spark.*;

public class AuthController extends BaseController{
    public AuthController(final Gson jsonConverter){
        super(jsonConverter);
        initializeController(jsonConverter);
    }

    public void initializeController(Gson jsonConverter){
        post("/login", (req, res) -> {
            validateContentType(req);

            String payload = req.body();
            Login login = jsonConverter.fromJson(payload, Login.class);
            return new Response(AuthServiceUtil.login(login.getUsername(), login.getPassword()));
        }, CommonUtils.getJsonTransformer());

        get("/extend", (req, res) -> {
            return new Response(AuthServiceUtil.extendToken(req.queryParams(Constants.URL_PARAM_TOKEN)));
        }, CommonUtils.getJsonTransformer());
    }
}
