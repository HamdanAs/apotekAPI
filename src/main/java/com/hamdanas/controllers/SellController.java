/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hamdanas.controllers;

import com.google.gson.Gson;
import com.hamdanas.dao.InvoiceDao;
import com.hamdanas.dao.MedDao;
import com.hamdanas.dao.TransDetailDao;
import com.hamdanas.dao.TransactionDao;
import com.hamdanas.dao.interfaces.InvoiceImp;
import com.hamdanas.dao.interfaces.MedImp;
import com.hamdanas.dao.interfaces.TransDetailImp;
import com.hamdanas.dao.interfaces.TransactionImp;
import com.hamdanas.models.Invoice;
import com.hamdanas.models.Transaction;
import com.hamdanas.models.TransactionDetail;
import com.hamdanas.response.ListResponse;
import com.hamdanas.response.Response;
import com.hamdanas.utilities.CommonUtils;
import com.hamdanas.utilities.Date;
import com.hamdanas.utilities.InvoiceCode;

import org.eclipse.jetty.http.HttpStatus;

import spark.Request;
import static spark.Spark.*;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author hamdan
 */
public class SellController {
    private final TransactionImp tImp;
    private final TransDetailImp tdImp;
    private final InvoiceImp iImp;
    private final MedImp mImp;
    
    public SellController(final Gson jsonConverter){
        tImp = new TransactionDao();
        tdImp = new TransDetailDao();
        iImp = new InvoiceDao();
        mImp = new MedDao();
        initializeController(jsonConverter);
    }

    public void initializeController(final Gson jsonConverter){
        post("/transaction/save", (req, res) -> {
            ListResponse<Response> list = new ListResponse<>();
            List<Response> responseList = new ArrayList<>();
            responseList.add(saveTransaction(jsonConverter, req, res));
            responseList.add(saveTransactionDetails(jsonConverter, req, res));
            responseList.add(insertInvoice(jsonConverter, req, res));

            list.setCode(200);
            list.setMessage("Pembelian baru telah ditambahkan!");
            list.setResult(responseList);
            
            return list;
        }, CommonUtils.getJsonTransformer());
    }
    
    public Response saveTransaction(final Gson jsonConverter, Request request, spark.Response res){
            
        String payload = request.body();
        Transaction p = jsonConverter.fromJson(payload, Transaction.class);
        p.setTransactionCode(InvoiceCode.generate("PJ", "transaction"));

        tImp.insert(p);

        return new Response(p);
            
    }

    public Response saveTransactionDetails(final Gson jsonConverter, Request request, spark.Response res){
        String payload = request.body();
        TransactionDetail p = jsonConverter.fromJson(payload, TransactionDetail.class);

        mImp.subStock(p.getQty(), p.getMedId());
        tdImp.insert(p);

        return new Response(p);
    }

    public Response insertInvoice(final Gson jsonConverter, Request request, spark.Response res){
        Invoice i = new Invoice();
        Response r = new Response();

        i.setDate(Date.now());
        i.setSeq(InvoiceCode.getSequenceNum("transaction"));

        iImp.insert(i, "transaction");

        r.setCode(HttpStatus.OK_200);
        r.setMessage("Invoice baru telah ditambahkan");

        return r;
    }
}
