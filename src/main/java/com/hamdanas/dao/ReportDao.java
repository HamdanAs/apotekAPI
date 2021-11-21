package com.hamdanas.dao;

import com.hamdanas.dao.interfaces.ReportImp;
import com.hamdanas.database.Database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NESAS
 */
public class ReportDao implements ReportImp{

    private final Connection conn;
    
    private final String loadDateSell = "select distinct date as day from transactions";
    private final String loadMonthSell = "select distinct month(date) as month from transactions";
    private final String loadYearSell = "select distinct year(date) as month from transactions";
    private final String loadDateBuy = "select distinct date as day from purchases";
    private final String loadMonthBuy = "select distinct month(date) as month from purchases";
    private final String loadYearBuy = "select distinct year(date) as month from purchases";
    
    public ReportDao(){
        conn = Database.connection();
    }
    
    @Override
    public List<String> loadDateSell() {
        List<String> lm = null;
        
        try {
            lm = new ArrayList<>();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(loadDateSell);
            while(res.next()){
                lm.add(res.getString(1));
            }
            
        } catch(SQLException e){
            System.err.println(e);
        }
        
        return lm;
    }

    @Override
    public List<String> loadMonthSell() {
        List<String> lm = null;
        
        try {
            lm = new ArrayList<>();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(loadMonthSell);
            while(res.next()){
                lm.add(res.getString(1));
            }
            
        } catch(SQLException e){
            System.err.println(e);
        }
        
        return lm;
    }

    @Override
    public List<String> loadYearSell() {
        List<String> lm = null;
        
        try {
            lm = new ArrayList<>();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(loadYearSell);
            while(res.next()){
                lm.add(res.getString(1));
            }
            
        } catch(SQLException e){
            System.err.println(e);
        }
        
        return lm;
    }

    @Override
    public List<String> loadDateBuy() {
        List<String> lm = null;
        
        try {
            lm = new ArrayList<>();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(loadDateBuy);
            while(res.next()){
                lm.add(res.getString(1));
            }
            
        } catch(SQLException e){
            System.err.println(e);
        }
        
        return lm;
    }

    @Override
    public List<String> loadMonthBuy() {
        List<String> lm = null;
        
        try {
            lm = new ArrayList<>();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(loadMonthBuy);
            while(res.next()){
                lm.add(res.getString(1));
            }
            
        } catch(SQLException e){
            System.err.println(e);
        }
        
        return lm;
    }

    @Override
    public List<String> loadYearBuy() {
        List<String> lm = null;
        
        try {
            lm = new ArrayList<>();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(loadYearBuy);
            while(res.next()){
                lm.add(res.getString(1));
            }
            
        } catch(SQLException e){
            System.err.println(e);
        }
        
        return lm;
    }

}
