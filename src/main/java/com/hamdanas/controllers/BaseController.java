package com.hamdanas.controllers;

import com.google.gson.Gson;
import com.hamdanas.utilities.Constants;

import static spark.Spark.after;

public class BaseController {
    public BaseController(final Gson jsonConverter) {

        // -- Check the authentication
        // before((req, res) -> {
        //     validateLogin(jsonConverter, req);
        // });

        // -- Set proper content-type to all responses
        after((req, res) -> {
            res.type(Constants.STANDARD_RESPONSE_CONTENTTYPE);
        });

        // -- Handle the exceptions
        // handleExceptions(jsonConverter);
    }
}
