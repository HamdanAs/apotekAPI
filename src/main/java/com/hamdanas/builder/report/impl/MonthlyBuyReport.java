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
public class MonthlyBuyReport extends SellReport{

    @Override
    public String folderName() {
        return "buy";
    }

    @Override
    public String name() {
        return "Laporan pembelian Bulanan";
    }

    @Override
    public String type() {
        return "monthly";
    }

    @Override
    public String fileName() {
        return "laporan_detail_pembelian_bulanan.jrxml";
    }
    
}
