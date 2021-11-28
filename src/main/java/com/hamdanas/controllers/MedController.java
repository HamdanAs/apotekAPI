/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hamdanas.controllers;

import com.google.gson.Gson;
import com.hamdanas.controllers.base.Controller;
import com.hamdanas.dao.MedDao;
import com.hamdanas.dao.interfaces.MedImp;
import com.hamdanas.exception.NotFoundException;
import com.hamdanas.models.Med;
import com.hamdanas.utilities.CommonUtils;
import com.hamdanas.utilities.Table;

import org.eclipse.jetty.http.HttpStatus;

import java.util.List;
import com.hamdanas.response.Response;

import spark.Request;
import static spark.Spark.*;

/**
 *
 * @author hamdan
 */
public class MedController extends BaseController {
    MedImp medImp;
    List<Med> lm;
    Table table;
    Gson gson;

    public MedController(final Gson jsonConverter) {
        medImp = new MedDao();
        lm = medImp.all();
        gson = new Gson();
        initializeController(jsonConverter);
    }

    public void initializeController(final Gson jsonConverter) {
        get("/med", (req, res) -> {
            return getAll();
        }, CommonUtils.getJsonTransformer());

        get("/med/:id", (req, res) -> {
            return find(req);
        }, CommonUtils.getJsonTransformer());

        post("/med", (req, res) -> {
            return insert(jsonConverter, req, res);
        }, CommonUtils.getJsonTransformer());

        put("/med/:id", (req, res) -> {
            return update(jsonConverter, req, res);
        }, CommonUtils.getJsonTransformer());

        delete("/med/:id", (req, res) -> {
            return deleteById(req, res);
        }, CommonUtils.getJsonTransformer());
    }

    public Response getAll() {
        return new Response(lm);
    }

    public Response find(Request request) throws NotFoundException {
        int id = Integer.parseInt(request.params(":id"));

        return new Response(medImp.find(id));
    }

    public Response insert(final Gson jsonConverter, Request request, spark.Response res) {

        String payload = request.body();
        Med medToAdd = jsonConverter.fromJson(payload, Med.class);
        medImp.insert(medToAdd);

        res.status(HttpStatus.CREATED_201);
        return new Response(medToAdd);
    }

    public Response update(final Gson jsonConverter, Request request, spark.Response res) throws NotFoundException {

        String payload = request.body();
        Med medToUpdate = jsonConverter.fromJson(payload, Med.class);
        medImp.update(medToUpdate);

        return new Response(medToUpdate);
    }

    public Response deleteById(Request request, spark.Response response) throws NotFoundException {

        int id = Integer.parseInt(request.params(":id"));
        medImp.delete(id);

        return new Response(0, "");
    }
}
