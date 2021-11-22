/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hamdanas.controllers;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.hamdanas.dao.InvoiceDao;
import com.hamdanas.dao.MedDao;
import com.hamdanas.dao.PurchDetailDao;
import com.hamdanas.dao.PurchaseDao;
import com.hamdanas.dao.interfaces.InvoiceImp;
import com.hamdanas.dao.interfaces.MedImp;
import com.hamdanas.dao.interfaces.PurchDetailImp;
import com.hamdanas.dao.interfaces.PurchaseImp;
import java.util.ArrayList;
import java.util.List;

import com.hamdanas.models.Invoice;
import com.hamdanas.models.PurchaseDetail;
import com.hamdanas.models.Purchase;
import com.hamdanas.utilities.CommonUtils;
import com.hamdanas.utilities.Date;
import com.hamdanas.utilities.InvoiceCode;

import org.eclipse.jetty.http.HttpStatus;

import spark.Request;
import static spark.Spark.*;

import com.hamdanas.response.ListResponse;
import com.hamdanas.response.Response;

/**
 *
 * @author hamdan
 */
public class BuyController extends BaseController{
    private final PurchaseImp tImp;
    private final PurchDetailImp tdImp;
    private final MedImp mImp;
    private final InvoiceImp iImp;
    
    public BuyController(final Gson jsonConverter){
        super(jsonConverter);
        tImp = new PurchaseDao();
        tdImp = new PurchDetailDao();
        iImp = new InvoiceDao();
        mImp = new MedDao();
        initializeController(jsonConverter);
    }

    public void initializeController(final Gson jsonConverter){
        post("/purchase/save", (req, res) -> {
            ListResponse<Response> list = new ListResponse<>();
            List<Response> responseList = new ArrayList<>();
            responseList.add(savePurchase(jsonConverter, req, res));
            responseList.add(savePurchaseDetails(jsonConverter, req, res));
            responseList.add(insertInvoice(jsonConverter, req, res));

            list.setCode(200);
            list.setMessage("Pembelian baru telah ditambahkan!");
            list.setResult(responseList);
            
            return list;
        }, CommonUtils.getJsonTransformer());
    }
    
    public Response savePurchase(final Gson jsonConverter, Request request, spark.Response res){
            
        String payload = request.body();
        Purchase p = jsonConverter.fromJson(payload, Purchase.class);
        p.setPurchaseCode(InvoiceCode.generate("PM", "purchase"));

        tImp.insert(p);

        return new Response(p);
            
    }

    public Response savePurchaseDetails(final Gson jsonConverter, Request request, spark.Response res){
        String payload = request.body();
        PurchaseDetail p = jsonConverter.fromJson(payload, PurchaseDetail.class);

        mImp.addStock(p.getQty(), p.getMedId());
        tdImp.insert(p);

        return new Response(p);
    }

    public Response insertInvoice(final Gson jsonConverter, Request request, spark.Response res){
        Invoice i = new Invoice();
        Response r = new Response();

        i.setDate(Date.now());
        i.setSeq(InvoiceCode.getSequenceNum("purchase"));

        iImp.insert(i, "purchase");

        r.setCode(HttpStatus.OK_200);
        r.setMessage("Invoice baru telah ditambahkan");

        return r;
    }
}
