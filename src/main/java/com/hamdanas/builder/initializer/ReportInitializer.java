/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hamdanas.builder.initializer;

import com.hamdanas.builder.report.Report;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gawrgura
 */
public class ReportInitializer {
    List<Report> reports = new ArrayList<>();
    
    public void addReport(Report report){
        reports.add(report);
    }
    
    public List<String> getReportName(){
        List<String> reportNameList = new ArrayList<>();
        reports.forEach((report) -> {
            reportNameList.add(report.name());
        });
        
        return reportNameList;
    }
    
    public List<Report> getReport(){
        return reports;
    }
}
