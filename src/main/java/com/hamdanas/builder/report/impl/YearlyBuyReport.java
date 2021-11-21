/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hamdanas.builder.report.impl;

import com.hamdanas.builder.report.abs.SellReport;

/**
 *
 * @author Gawrgura
 */
public class YearlyBuyReport extends SellReport{

    @Override
    public String folderName() {
        return "buy";
    }

    @Override
    public String name() {
        return "Laporan Pembelian Tahunan";
    }

    @Override
    public String type() {
        return "yearly";
    }

    @Override
    public String fileName() {
        return "laporan_detail_pembelian_tahunan.jrxml";
    }
    
}
