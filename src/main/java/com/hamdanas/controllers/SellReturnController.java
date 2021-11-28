/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hamdanas.controllers;

import com.google.gson.Gson;
import com.hamdanas.dao.InvoiceDao;
import com.hamdanas.dao.MedDao;
import com.hamdanas.dao.TransReturnDetailDao;
import com.hamdanas.dao.TransactionReturnDao;
import com.hamdanas.dao.interfaces.InvoiceImp;
import com.hamdanas.dao.interfaces.MedImp;
import com.hamdanas.dao.interfaces.TransReturnDetailImp;
import com.hamdanas.dao.interfaces.TransactionReturnImp;
import com.hamdanas.models.Invoice;
import com.hamdanas.utilities.CommonUtils;
import com.hamdanas.utilities.Date;
import com.hamdanas.utilities.InvoiceCode;

import org.eclipse.jetty.http.HttpStatus;

import spark.Request;

import java.util.ArrayList;
import java.util.List;
import com.hamdanas.models.TransactionReturn;
import com.hamdanas.models.TransactionReturnDetail;
import com.hamdanas.response.ListResponse;
import com.hamdanas.response.Response;

import static spark.Spark.*;

/**
 *
 * @author hamdan
 */
public class SellReturnController extends BaseController {
    private final TransactionReturnImp tImp;
    private final TransReturnDetailImp tdImp;
    private final InvoiceImp iImp;
    private final MedImp mImp;

    public SellReturnController(final Gson jsonConverter) {
        tImp = new TransactionReturnDao();
        tdImp = new TransReturnDetailDao();
        iImp = new InvoiceDao();
        mImp = new MedDao();
        initializeController(jsonConverter);
    }

    public void initializeController(final Gson jsonConverter) {
        post("/transactionreturn/save", (req, res) -> {
            ListResponse<Response> list = new ListResponse<>();
            List<Response> responseList = new ArrayList<>();
            responseList.add(saveReturn(jsonConverter, req, res));
            responseList.add(saveReturnDetails(jsonConverter, req, res));
            responseList.add(insertInvoice(jsonConverter, req, res));

            list.setCode(200);
            list.setMessage("Pembelian baru telah ditambahkan!");
            list.setResult(responseList);

            return list;
        }, CommonUtils.getJsonTransformer());
    }

    public Response saveReturn(final Gson jsonConverter, Request request, spark.Response res) {
        String payload = request.body();
        TransactionReturn p = jsonConverter.fromJson(payload, TransactionReturn.class);
        p.setTransactionCode(InvoiceCode.generate("PM", "transaction"));

        tImp.insert(p);

        return new Response(p);
    }

    public Response saveReturnDetails(final Gson jsonConverter, Request request, spark.Response res) {
        String payload = request.body();
        TransactionReturnDetail p = jsonConverter.fromJson(payload, TransactionReturnDetail.class);

        mImp.addStock(p.getQty(), p.getMedId());
        tdImp.insert(p);

        return new Response(p);
    }

    public Response insertInvoice(final Gson jsonConverter, Request request, spark.Response res) {
        Invoice i = new Invoice();
        Response r = new Response();

        i.setDate(Date.now());
        i.setSeq(InvoiceCode.getSequenceNum("purchase"));

        iImp.insert(i, "transaction");

        r.setCode(HttpStatus.OK_200);
        r.setMessage("Invoice baru telah ditambahkan");

        return r;
    }
}
