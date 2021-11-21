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
public class DailySellReport extends SellReport{

    @Override
    public String folderName() {
        return "sell";
    }

    @Override
    public String name() {
        return "Laporan Penjualan Harian";
    }

    @Override
    public String type() {
        return "daily";
    }

    @Override
    public String fileName() {
        return "laporan_detail_penjualan_harian.jrxml";
    }
    
}
