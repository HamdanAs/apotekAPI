package com.hamdanas;

import com.google.gson.Gson;
import com.hamdanas.controllers.AuthController;
import com.hamdanas.controllers.MedController;
import com.hamdanas.exception.InvalidCredentialsException;
import com.hamdanas.exception.InvalidPayloadException;
import com.hamdanas.exception.NotFoundException;
import com.hamdanas.response.Response;
import com.hamdanas.utilities.AuthServiceUtil;
import com.hamdanas.utilities.CommonUtils;
import com.hamdanas.utilities.Constants;

import org.eclipse.jetty.http.HttpStatus;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.lang.JoseException;

import spark.Filter;
import spark.Request;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        options("/*", (request, response) -> {

            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        // -- Check the authentication
        before((req, res) -> {
            validateLogin(CommonUtils.getJsonConvertor(), req);

            System.out.println("API Endpoint accessed");
        });

        after((Filter) (req, res) -> {
            res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Methods", "GET,PUT,PATCH,POST,DELETE");
            res.header("Access-Control-Allow-Headers", "Origin, Content-Type, X-Auth-Token");
            res.header("Access-Control-Allow-Credentials", "true");

            res.type(Constants.STANDARD_RESPONSE_CONTENTTYPE);
        });

        // -- Handle the exceptions
        handleExceptions(CommonUtils.getJsonConvertor());

        new AuthController(CommonUtils.getJsonConvertor());

        MedController medController = new MedController(CommonUtils.getJsonConvertor());
        Thread medThread = new Thread(medController);
        medThread.start();

    }

    private static void validateLogin(final Gson jsonConverter, Request req)
            throws JoseException, MalformedClaimException {

        if (req.pathInfo().equalsIgnoreCase("/login")) {
            return;
        }

        // -- Get token
        String token = req.queryParams(Constants.URL_PARAM_TOKEN);
        String username = AuthServiceUtil.getUsername(token);

        if (username == null) {
            halt(HttpStatus.FORBIDDEN_403,
                    jsonConverter.toJson(new Response(Constants.RESPONSE_ERROR, "Invalid/expired authentication")));
        }

        // -- Add the username as attribute so that subsequent requests can know who is
        // currently logged in
        req.attribute("principal", username);
    }

    protected static void validateContentType(Request req) throws InvalidPayloadException {
        if (req.contentType() == null || !req.contentType().toLowerCase().contains("application/json")) {
            throw new InvalidPayloadException("Invalid content type: " + req.contentType());
        }
    }

    protected static void handleExceptions(final Gson jsonConverter) {
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
