/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hamdanas.controllers;

import com.google.gson.Gson;
import com.hamdanas.dao.InvoiceDao;
import com.hamdanas.dao.MedDao;
import com.hamdanas.dao.PurchReturnDetailDao;
import com.hamdanas.dao.PurchaseReturnDao;
import com.hamdanas.dao.interfaces.InvoiceImp;
import com.hamdanas.dao.interfaces.MedImp;
import com.hamdanas.dao.interfaces.PurchReturnDetailImp;
import com.hamdanas.dao.interfaces.PurchaseReturnImp;
import com.hamdanas.models.Invoice;
import com.hamdanas.response.ListResponse;
import com.hamdanas.response.Response;
import com.hamdanas.utilities.CommonUtils;
import com.hamdanas.utilities.Date;
import com.hamdanas.utilities.InvoiceCode;
import com.hamdanas.utilities.Table;

import org.eclipse.jetty.http.HttpStatus;

import spark.Request;

import static spark.Spark.*;

import java.util.ArrayList;
import java.util.List;

import com.hamdanas.models.PurchaseReturn;
import com.hamdanas.models.PurchaseReturnDetail;

/**
 *
 * @author hamdan
 */
public class BuyReturnController extends BaseController {
    PurchaseReturnImp tImp;
    PurchReturnDetailImp tdImp;
    InvoiceImp iImp;
    MedImp mImp;
    Table table;

    public BuyReturnController(final Gson jsonConverter) {
        tImp = new PurchaseReturnDao();
        tdImp = new PurchReturnDetailDao();
        iImp = new InvoiceDao();
        mImp = new MedDao();
        initializeController(jsonConverter);
    }

    public void initializeController(final Gson jsonConverter) {
        post("/purchasereturn/save", (req, res) -> {
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
        PurchaseReturn p = jsonConverter.fromJson(payload, PurchaseReturn.class);
        p.setPurchaseCode(InvoiceCode.generate("PJ", "transaction"));

        tImp.insert(p);

        return new Response(p);
    }

    public Response saveReturnDetails(final Gson jsonConverter, Request request, spark.Response res) {
        String payload = request.body();
        PurchaseReturnDetail p = jsonConverter.fromJson(payload, PurchaseReturnDetail.class);

        mImp.subStock(p.getQty(), p.getMedId());
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
