/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hamdanas.controllers;

import com.google.gson.Gson;
import com.hamdanas.dao.SupplierDao;
import com.hamdanas.dao.interfaces.SupplierImp;
import com.hamdanas.exception.NotFoundException;

import com.hamdanas.models.Supplier;
import com.hamdanas.response.Response;
import com.hamdanas.utilities.CommonUtils;
import com.hamdanas.utilities.Table;
import java.util.List;

import org.eclipse.jetty.http.HttpStatus;

import static spark.Spark.*;
import spark.*;

/**
 *
 * @author hamdan
 */
public class SupplierController extends BaseController{
    SupplierImp supplierImp;
    List<Supplier> lm;
    Table table;
    
    public SupplierController(final Gson jsonConverter){
        super(jsonConverter);
        supplierImp = new SupplierDao();
        lm = supplierImp.all();
        initializeController(jsonConverter);
    }

    public void initializeController(final Gson jsonConverter){
        get("/supplier", (req, res) -> {
            return getAll();
        }, CommonUtils.getJsonTransformer());

        get("/supplier/:id", (req, res) -> {
            return find(req);
        }, CommonUtils.getJsonTransformer());

        post("/supplier", (req, res) -> {
            return insert(jsonConverter, req, res);
        }, CommonUtils.getJsonTransformer());

        put("/supplier/:id", (req, res) -> {
            return update(jsonConverter, req, res);
        }, CommonUtils.getJsonTransformer());

        delete("/supplier/:id", (req, res) -> {
            return deleteById(req, res);
        }, CommonUtils.getJsonTransformer());
    }
    
    public Response getAll(){
        return new Response(lm);
    }

    public Response find(Request request) throws NotFoundException{
        int id = Integer.parseInt(request.params(":id"));

        return new Response(supplierImp.find(id));
    }
    
    public Response insert(final Gson jsonConverter, Request request, spark.Response res){

        String payload = request.body();
        Supplier SupplierToAdd = jsonConverter.fromJson(payload, Supplier.class);
        supplierImp.insert(SupplierToAdd);

        res.status(HttpStatus.CREATED_201);
        return new Response(SupplierToAdd);
    }
    
    public Response update(final Gson jsonConverter, Request request, spark.Response res) throws NotFoundException{
        
        String payload = request.body();
        Supplier SupplierToUpdate = jsonConverter.fromJson(payload, Supplier.class);
        supplierImp.update(SupplierToUpdate);

        return new Response(SupplierToUpdate);
    }
    
    public Response deleteById(Request request, spark.Response response) throws NotFoundException{

        int id = Integer.parseInt(request.params(":id"));
        supplierImp.delete(id);

        return new Response(0, "");
    }
}
