/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hamdanas.controllers;

import com.google.gson.Gson;
import com.hamdanas.dao.MedDao;
import com.hamdanas.dao.interfaces.MedImp;
import com.hamdanas.models.Med;
import com.hamdanas.utilities.Table;
import java.util.List;

import spark.Request;
import spark.Response;

/**
 *
 * @author hamdan
 */
public class MedController{
    MedImp medImp;
    List<Med> lm;
    Table table;
    Gson gson;
    
    public MedController(){
        medImp = new MedDao();
        lm = medImp.all();
        gson = new Gson();
    }
    
    public String getAll(Response response){
        response.type("application/json");

        lm = medImp.all();
        return gson.toJson(lm);
        
    }

    private int toInt(String s){
        return Integer.parseInt(s);
    }
    
    public String insert(Request request, Response response){
        return request.queryParams("basePrice");
        
        // response.type("application/json");

        // Med m = new Med();
        // m.setName(request.queryParams("name"));
        // m.setDesctription(request.queryParams("description"));
        // m.setBasePrice(toInt(request.queryParams("basePrice")));
        // m.setPrice(toInt(request.queryParams("price")));

        // lm = medImp.actualFind(m.getName());
        
        // if(lm.isEmpty()){
        //     medImp.insert(m);

        //     return "Data tersimpan!";
        // } else {
        //     return "Nama obat sudah ada didalam database!";
        // }
        
    }
    
    public String update(Request request, Response response){
        response.type("application/json");

        Med m = new Med();
        m.setName(request.queryParams("name"));
        m.setDesctription(request.queryParams("description"));
        m.setBasePrice(toInt(request.queryParams("basePrice")));
        m.setPrice(toInt(request.queryParams("price")));

        medImp.update(m);

        return "Data tersimpan!";
    }
    
    public String delete(Request request, Response response){
        response.type("application/json");

        int id = Integer.parseInt(request.params(":id"));
        medImp.delete(id);

        return "Data berhasil dihapus!";
    }
    
    public String find(Request request, Response response){
        response.type("application/json");

        int id = Integer.parseInt(request.params(":id"));

        lm = medImp.find(id);

        if(lm.isEmpty()){
            response.status(404);
            return "Data tidak ditemukan!";
        } else {
            return gson.toJson(lm);
        }
    }
}
