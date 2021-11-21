/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hamdanas.builder;

import com.hamdanas.builder.initializer.ReportInitializer;
import com.hamdanas.builder.report.impl.DailyBuyReport;
import com.hamdanas.builder.report.impl.DailySellReport;
import com.hamdanas.builder.report.impl.MonthlyBuyReport;
import com.hamdanas.builder.report.impl.MonthlySellReport;
import com.hamdanas.builder.report.impl.YearlyBuyReport;
import com.hamdanas.builder.report.impl.YearlySellReport;

/**
 *
 * @author Gawrgura
 */
public class ReportBuilder {
    private static final ReportBuilder OBJ = new ReportBuilder();
    private ReportBuilder(){}
    
    public static ReportBuilder getInstance(){
        return OBJ;
    }
    
    public ReportInitializer prepareSellReport(){
        ReportInitializer reportInitializer = new ReportInitializer();
        
        reportInitializer.addReport(new DailySellReport());
        reportInitializer.addReport(new MonthlySellReport());
        reportInitializer.addReport(new YearlySellReport());
        
        return reportInitializer;
    }
    
    public ReportInitializer prepareBuyReport(){
        ReportInitializer reportInitializer = new ReportInitializer();
        
        reportInitializer.addReport(new DailyBuyReport());
        reportInitializer.addReport(new MonthlyBuyReport());
        reportInitializer.addReport(new YearlyBuyReport());
        
        return reportInitializer;
    }
}
