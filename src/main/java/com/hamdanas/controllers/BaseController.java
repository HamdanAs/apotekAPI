package com.hamdanas.controllers;

import com.google.gson.Gson;
import com.hamdanas.exception.InvalidCredentialsException;
import com.hamdanas.exception.InvalidPayloadException;
import com.hamdanas.exception.NotFoundException;
import com.hamdanas.response.Response;
import com.hamdanas.utilities.Constants;

import org.eclipse.jetty.http.HttpStatus;

import spark.Request;

import static spark.Spark.*;

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
        handleExceptions(jsonConverter);
    }

    protected void validateContentType(Request req) throws InvalidPayloadException{
        if(req.contentType() == null || !req.contentType().toLowerCase().contains("application/json")){
            throw new InvalidPayloadException("Invalid content type: " + req.contentType());
        }
    }

    protected void handleExceptions(final Gson jsonConverter){
        exception(InvalidCredentialsException.class, (ex, req, res) -> {
            res.status(HttpStatus.FORBIDDEN_403);
            res.body(jsonConverter.toJson(new Response(Constants.RESPONSE_ERROR, ex.getMessage())));
        });
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
