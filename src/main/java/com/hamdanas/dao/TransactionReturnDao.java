/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hamdanas.dao;

import com.hamdanas.dao.interfaces.TransactionReturnImp;
import com.hamdanas.database.Database;
import com.hamdanas.models.Med;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.hamdanas.models.TransactionReturn;

/**
 *
 * @author hamdan
 */
public class TransactionReturnDao implements TransactionReturnImp{
    Connection conn;
    
    final String insert = "insert into transaction_returns values (null,?,?,?,?)";
    final String all = "select * from transaction_returns";
    final String med = "select * from med";
    final String medByName = "select * from med where name like ?";
    final String medById = "select * from med where id like ?";
    
    public TransactionReturnDao(){
        conn = Database.connection();
    }

    @Override
    public void insert(TransactionReturn t) {
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(insert, PreparedStatement.RETURN_GENERATED_KEYS);
            stat.setString(1, t.getDate());
            stat.setString(2, t.getReason());
            stat.setInt(3, t.getTotal());
            stat.setString(4, t.getTransactionCode());
            stat.executeUpdate();
            
            ResultSet rs = stat.getGeneratedKeys();
            
            while(rs.next()){
                t.setId(rs.getInt(PreparedStatement.RETURN_GENERATED_KEYS));
            }
        } catch (SQLException e){
            System.err.println(e);
        } finally {
            try {
                stat.close();
            } catch (SQLException e){
                System.err.println(e);
            }
        }
    }

    @Override
    public List<TransactionReturn> all() {
        List<TransactionReturn> lt = null;
        try {
            lt = new ArrayList<>();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(all);
            while(res.next()){
                TransactionReturn t = new TransactionReturn();
                t.setId(res.getInt(1));
                t.setDate(res.getString(2));
                t.setReason(res.getString(3));
                t.setTransactionCode(res.getString(4));
                lt.add(t);
            }
        } catch (SQLException e){
            System.err.println(e);
        }
        
        return lt;
    }

    @Override
    public List<Med> getMed() {
        List<Med> lm = null;
        try {
            lm = new ArrayList<>();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(med);
            while(res.next()){
                Med m = new Med();
                m.setId(res.getInt(1));
                m.setName(res.getString(2));
                lm.add(m);
            }
        } catch (SQLException e){
            System.err.println(e);
        }
        
        return lm;
    }

    @Override
    public List<Med> getMedByName(String name) {
        List<Med> lm = null;
        try {
            lm = new ArrayList<>();
            PreparedStatement stat = conn.prepareStatement(medByName);
            stat.setString(1, "%" + name + "%");
            ResultSet res = stat.executeQuery();
            while(res.next()){
                Med m = new Med();
                m.setId(res.getInt(1));
                m.setName(res.getString(2));
                m.setDesctription(res.getString(3));
                m.setBasePrice(res.getInt(4));
                m.setPrice(res.getInt(5));
                m.setStock(res.getInt(6));
                lm.add(m);
            }
        } catch (SQLException e){
            System.err.println(e);
        }
        
        return lm;
    }

    @Override
    public List<Med> getMedById(String id) {
        List<Med> lm = null;
        try {
            lm = new ArrayList<>();
            PreparedStatement stat = conn.prepareStatement(medById);
            stat.setString(1, "%" + id + "%");
            ResultSet res = stat.executeQuery();
            while(res.next()){
                Med m = new Med();
                m.setId(res.getInt(1));
                m.setName(res.getString(2));
                m.setDesctription(res.getString(3));
                m.setBasePrice(res.getInt(4));
                m.setPrice(res.getInt(5));
                m.setStock(res.getInt(6));
                lm.add(m);
            }
        } catch (SQLException e){
            System.err.println(e);
        }
        
        return lm;
    }
}
