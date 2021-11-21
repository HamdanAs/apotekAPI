package com.hamdanas.controllers;

import com.hamdanas.builder.ReportBuilder;
import com.hamdanas.builder.initializer.ReportInitializer;
import com.hamdanas.builder.report.Report;
import com.hamdanas.dao.ReportDao;
import com.hamdanas.dao.interfaces.ReportImp;
import com.hamdanas.database.Database;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import javax.swing.JOptionPane;
//import net.sf.jasperreports.engine.JRException;
//import net.sf.jasperreports.engine.JRParameter;
//import net.sf.jasperreports.engine.JasperCompileManager;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.JasperReport;
//import net.sf.jasperreports.view.JasperViewer;
import views.Laporan;


/**
 *
 * @author NESAS
 */
public class ReportController {
    
    private enum ReportType{
        buy,
        sell
    }
    
    private final Laporan frame;
    private final ReportImp reportImp;
    private final Connection conn;
    private List<String> listSell;
    private final Locale id;
    
    private final ReportBuilder builder;
    private final ReportInitializer buyReport;
    private final ReportInitializer sellReport;
    
    private ReportType reportType;
    
    public ReportController(Laporan frame){
        this.frame = frame;
        this.conn = Database.connection();
        
        reportImp = new ReportDao();
        
        id = new Locale("in", "ID");
        
        builder = ReportBuilder.getInstance();
        buyReport = builder.prepareBuyReport();
        sellReport = builder.prepareSellReport();
    }
    
    public void setReportName(){
        frame.gettNama().addItem("-- Pilih Laporan --");
        
        buyReport.getReportName().forEach((report) -> {
            frame.gettNama().addItem(report);
        });

        sellReport.getReportName().forEach((report) -> {
            frame.gettNama().addItem(report);
        });
    }
    
    public void updateReport(){
        String reportName = (String) frame.gettNama().getSelectedItem();
        
        checkType(reportName);
        
        if(frame.gettNama().getSelectedIndex() == 0){
            resetCombobox();
        }
        
        if(reportType != null){
            switch (reportType){
                case buy:
                    setReport(reportName, buyReport);
                    break;
                case sell:
                    setReport(reportName, sellReport);
                    break;
                default:
                    break;
            }
        }
    }
    
    public void setSellDate(){
        frame.gettTanggal().setEnabled(true);
        
        listSell = reportImp.loadDateSell();
        
        frame.gettTanggal().addItem("Pilih tanggal");
        
        listSell.forEach(frame.gettTanggal()::addItem);
    }
    
    public void setSellMonth(){
        frame.gettTanggal().setEnabled(true);
        
        listSell = reportImp.loadMonthSell();
        
        frame.gettTanggal().addItem("Pilih bulan");
        
        listSell.forEach(frame.gettTanggal()::addItem);
    }
    
    public void setSellYear(){
        frame.gettTahun().setEnabled(true);
        
        listSell = reportImp.loadYearSell();
        
        frame.gettTahun().addItem("Pilih tahun");
        
        listSell.forEach(frame.gettTahun()::addItem);
    }
    
    public void setBuyDate(){
        frame.gettTanggal().setEnabled(true);
        
        listSell = reportImp.loadDateBuy();
        
        frame.gettTanggal().addItem("Pilih tanggal");
        
        listSell.forEach(frame.gettTanggal()::addItem);
    }
    
    public void setBuyMonth(){
        frame.gettTanggal().setEnabled(true);
        
        listSell = reportImp.loadMonthBuy();
        
        frame.gettTanggal().addItem("Pilih bulan");
        
        listSell.forEach(frame.gettTanggal()::addItem);
    }
    
    public void setBuyYear(){
        frame.gettTahun().setEnabled(true);
        
        listSell = reportImp.loadYearBuy();
        
        frame.gettTahun().addItem("Pilih tahun");
        
        listSell.forEach((list) -> {
            frame.gettTahun().addItem(list);
        });
    }
    
    public void showReport(){
        if(frame.gettNama().getSelectedIndex() == 0){
            JOptionPane.showMessageDialog(null, "Silahkan pilih laporan!", "Laporan", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                
                String date = frame.gettTanggal().getSelectedItem().toString();
                Date newDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                
                String jrxmlFile = "src/reports/laporan_detail_penjualan_harian.jrxml";
                HashMap param = new HashMap();
                param.put(JRParameter.REPORT_LOCALE, id);
                param.put("imageDir", Report.PATH);
                param.put("date", newDate);
                
                JasperReport jr = JasperCompileManager.compileReport(jrxmlFile);
                JasperPrint print = JasperFillManager.fillReport(jr, param, conn);
                JasperViewer.viewReport(print, false);
            } catch (JRException | ParseException e){
                System.err.println(e);
            }
        }
    }
    
    private void checkType(String reportName){
        reportName = reportName.toLowerCase();
        
        if(reportName.contains("penjualan")){
            reportType = ReportType.sell;
        } else if(reportName.contains("pembelian")){
            reportType = ReportType.buy;
        }
    }
    
    private void resetCombobox(){
        frame.gettTanggal().removeAllItems();
        frame.gettTahun().removeAllItems();
        frame.gettTahun().setEnabled(false);
        frame.gettTanggal().setEnabled(false);
    }
    
    private void setReport(String reportName, ReportInitializer init){
        for(Report report : init.getReport()){
            if(reportName.equals(report.name())){
                resetCombobox();

                switch(report.type()){
                    case "daily":
                        setBuyDate();
                        break;
                    case "monthly":
                        setBuyMonth();
                        setBuyYear();
                        break;
                    case "yearly":
                        setBuyYear();
                        break;
                    default:
                        resetCombobox();
                        break;
                }

                return;
            }
        }
    }
//    
//    public void getSellDailyReport(){
//        if(frame.getReport_SellDate().getSelectedIndex() == 0){
//            JOptionPane.showMessageDialog(null, "Silahkan pilih dulu tanggal", "Laporan", JOptionPane.WARNING_MESSAGE);
//        } else {
//            try {
//                
//                String date = frame.getReport_SellDate().getSelectedItem().toString();
//                Date newDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
//                
//                String jrxmlFile = "src/reports/laporan_detail_penjualan_harian.jrxml";
//                HashMap param = new HashMap();
//                param.put(JRParameter.REPORT_LOCALE, id);
//                param.put("imageDir", "src/reports/");
//                param.put("date", newDate);
//                JasperReport jr = JasperCompileManager.compileReport(jrxmlFile);
//                JasperPrint print = JasperFillManager.fillReport(jr, param, conn);
//                JasperViewer.viewReport(print, false);
//            } catch (JRException | ParseException e){
//                System.err.println(e);
//            }
//        }
//    }
//    
//    public void getSellMonthlyReport(){
//        if(frame.getReport_SellMonth().getSelectedIndex() == 0){
//            JOptionPane.showMessageDialog(null, "Silahkan pilih dulu bulan", "Laporan", JOptionPane.WARNING_MESSAGE);
//        } else {
//            try {
//                String jrxmlFile = "src/reports/laporan_detail_penjualan_bulanan.jrxml";
//                HashMap param = new HashMap();
//                param.put(JRParameter.REPORT_LOCALE, id);
//                param.put("imageDir", "src/reports/");
//                param.put("month", Integer.parseInt(frame.getReport_SellMonth().getSelectedItem().toString()));
//                param.put("year", Integer.parseInt(frame.getReport_SellMonth1().getSelectedItem().toString()));
//                JasperReport jr = JasperCompileManager.compileReport(jrxmlFile);
//                JasperPrint print = JasperFillManager.fillReport(jr, param, conn);
//                JasperViewer.viewReport(print, false);
//            } catch (JRException e){
//                System.err.println(e);
//            }
//        }
//    }
//    
//    public void getSellYearlyReport(){
//        if(frame.getReport_SellYear().getSelectedIndex() == 0){
//            JOptionPane.showMessageDialog(null, "Silahkan pilih dulu tahun", "Laporan", JOptionPane.WARNING_MESSAGE);
//        } else {
//            try {
//                String jrxmlFile = "src/reports/laporan_detail_penjualan_tahunan.jrxml";
//                HashMap param = new HashMap();
//                param.put(JRParameter.REPORT_LOCALE, id);
//                param.put("imageDir", "src/reports/");
//                param.put("year", Integer.parseInt(frame.getReport_SellYear().getSelectedItem().toString()));
//                JasperReport jr = JasperCompileManager.compileReport(jrxmlFile);
//                JasperPrint print = JasperFillManager.fillReport(jr, param, conn);
//                JasperViewer.viewReport(print, false);
//            } catch (JRException e){
//                System.err.println(e);
//            }
//        }
//    }
//    
//    public void getBuyDailyReport(){
//        if(frame.getReport_BuyDate().getSelectedIndex() == 0){
//            JOptionPane.showMessageDialog(null, "Silahkan pilih dulu tanggal", "Laporan", JOptionPane.WARNING_MESSAGE);
//        } else {
//            try {
//                
//                String date = frame.getReport_BuyDate().getSelectedItem().toString();
//                Date newDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
//                
//                String jrxmlFile = "src/reports/laporan_detail_pembelian_harian.jrxml";
//                HashMap param = new HashMap();
//                param.put(JRParameter.REPORT_LOCALE, id);
//                param.put("imageDir", "src/reports/");
//                param.put("date", newDate);
//                JasperReport jr = JasperCompileManager.compileReport(jrxmlFile);
//                JasperPrint print = JasperFillManager.fillReport(jr, param, conn);
//                JasperViewer.viewReport(print, false);
//            } catch (JRException | ParseException e){
//                System.err.println(e);
//            }
//        }
//    }
//    
//    public void getBuyMonthlyReport(){
//        if(frame.getReport_BuyMonth().getSelectedIndex() == 0){
//            JOptionPane.showMessageDialog(null, "Silahkan pilih dulu bulan", "Laporan", JOptionPane.WARNING_MESSAGE);
//        } else {
//            try {
//                String jrxmlFile = "src/reports/laporan_detail_pembelian_bulanan.jrxml";
//                HashMap param = new HashMap();
//                param.put(JRParameter.REPORT_LOCALE, id);
//                param.put("imageDir", "src/reports/");
//                param.put("month", Integer.parseInt(frame.getReport_BuyMonth().getSelectedItem().toString()));
//                param.put("year", Integer.parseInt(frame.getReport_BuyMonth1().getSelectedItem().toString()));
//                JasperReport jr = JasperCompileManager.compileReport(jrxmlFile);
//                JasperPrint print = JasperFillManager.fillReport(jr, param, conn);
//                JasperViewer.viewReport(print, false);
//            } catch (JRException e){
//                System.err.println(e);
//            }
//        }
//    }
//    
//    public void getBuyYearlyReport(){
//        if(frame.getReport_BuyYear().getSelectedIndex() == 0){
//            JOptionPane.showMessageDialog(null, "Silahkan pilih dulu tahun", "Laporan", JOptionPane.WARNING_MESSAGE);
//        } else {
//            try {
//                String jrxmlFile = "src/reports/laporan_detail_pembelian_tahunan.jrxml";
//                HashMap param = new HashMap();
//                param.put(JRParameter.REPORT_LOCALE, id);
//                param.put("imageDir", "src/reports/");
//                param.put("year", Integer.parseInt(frame.getReport_BuyYear().getSelectedItem().toString()));
//                JasperReport jr = JasperCompileManager.compileReport(jrxmlFile);
//                JasperPrint print = JasperFillManager.fillReport(jr, param, conn);
//                JasperViewer.viewReport(print, false);
//            } catch (JRException e){
//                System.err.println(e);
//            }
//        }
//    }
//
//    public void getMedReport() {
//        try {
//            String jrxmlFile = "src/reports/laporan_obat.jrxml";
//            HashMap param = new HashMap();
//            param.put("imageDir", "src/reports/");
//            param.put(JRParameter.REPORT_LOCALE, id);
//            JasperReport jr = JasperCompileManager.compileReport(jrxmlFile);
//            JasperPrint print = JasperFillManager.fillReport(jr, param, conn);
//            JasperViewer.viewReport(print, false);
//        } catch (JRException e){
//            System.err.println(e);
//        }
//    }
}
