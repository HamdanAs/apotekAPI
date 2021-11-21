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
public class MonthlySellReport extends SellReport{

    @Override
    public String folderName() {
        return "sell";
    }

    @Override
    public String name() {
        return "Laporan Penjualan Bulanan";
    }

    @Override
    public String type() {
        return "monthly";
    }

    @Override
    public String fileName() {
        return "laporan_detail_penjualan_bulanan.jrxml";
    }
    
}
