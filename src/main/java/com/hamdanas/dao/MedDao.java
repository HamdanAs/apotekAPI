/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hamdanas.dao;

import com.hamdanas.dao.interfaces.MedImp;
import com.hamdanas.database.Database;
import com.hamdanas.models.Med;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hamdan
 */
public class MedDao implements MedImp{
    
    Connection conn;
    
    final String insert = "insert into med values (null,?,?,?,?,?)";
    final String update = "update med set name=?, description=?, base_price=?, price=? where id=?";
    final String addStock = "update med set stock = stock + ? where id=?";
    final String subStock = "update med set stock = stock - ? where id=?";
    final String delete = "delete from med where id = ?";
    final String all = "select * from med";
    final String find = "select * from med where name like ?";
    final String aFind = "select * from med where name = ?";
    final String idFind = "select * from med where id = ?";
    
    public MedDao(){
        conn = Database.connection();
    }

    @Override
    public void insert(Med m) {
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(insert);
            stat.setString(1, m.getName());
            stat.setString(2, m.getDesctription());
            stat.setInt(3, m.getBasePrice());
            stat.setInt(4, m.getPrice());
            stat.setInt(5, 0);
            stat.executeUpdate();
            
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
    public void update(Med m) {
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(update);
            stat.setString(1, m.getName());
            stat.setString(2, m.getDesctription());
            stat.setInt(3, m.getBasePrice());
            stat.setInt(4, m.getPrice());
            stat.setInt(5, m.getId());
            stat.executeUpdate();
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
    public void addStock(int amout, int id){
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(addStock);
            stat.setInt(1, amout);
            stat.setInt(2, id);
            stat.executeUpdate();
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
    public void subStock(int amout, int id){
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(subStock);
            stat.setInt(1, amout);
            stat.setInt(2, id);
            stat.executeUpdate();
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
    public void delete(int id) {
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(delete);
            stat.setInt(1, id);
            stat.executeUpdate();
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
    public List<Med> all() {
        List<Med> lm = null;
        try {
            lm = new ArrayList<>();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(all);
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
    public List<Med> find(String name) {
        List<Med> lm = null;
        try {
            lm = new ArrayList<>();
            PreparedStatement stat = conn.prepareStatement(find);
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
    public List<Med> actualFind(String name) {
        List<Med> lm = null;
        try {
            lm = new ArrayList<>();
            PreparedStatement stat = conn.prepareStatement(aFind);
            stat.setString(1, name);
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
    public List<Med> find(int id) {
        List<Med> lm = null;
        try {
            lm = new ArrayList<>();
            PreparedStatement stat = conn.prepareStatement(idFind);
            stat.setInt(1, id);
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
