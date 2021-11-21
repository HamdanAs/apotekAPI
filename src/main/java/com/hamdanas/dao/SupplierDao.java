/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hamdanas.dao;

import com.hamdanas.dao.interfaces.SupplierImp;
import com.hamdanas.database.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.hamdanas.models.Supplier;

/**
 *
 * @author hamdan
 */
public class SupplierDao implements SupplierImp{
    
    Connection conn;
    
    final String insert = "insert into supplier values (null,?,?,?,?,?,?,?)";
    final String update = "update supplier set name=?, address=?, city=?, province=?, post_code=?, phone=?, contact=? where id=?";
    final String delete = "delete from supplier where id = ?";
    final String all = "select * from supplier";
    final String find = "select * from supplier where name like ?";
    final String aFind = "select * from supplier where name = ?";
    
    public SupplierDao(){
        conn = Database.connection();
    }

    @Override
    public void insert(Supplier m) {
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(insert);
            stat.setString(1, m.getName());
            stat.setString(2, m.getAddress());
            stat.setString(3, m.getCity());
            stat.setString(4, m.getProvince());
            stat.setString(5, m.getPostCode());
            stat.setString(6, m.getPhone());
            stat.setString(7, m.getContact());
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
    public void update(Supplier m) {
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(update);
            stat.setString(1, m.getName());
            stat.setString(2, m.getAddress());
            stat.setString(3, m.getCity());
            stat.setString(4, m.getProvince());
            stat.setString(5, m.getPostCode());
            stat.setString(6, m.getPhone());
            stat.setString(7, m.getContact());
            stat.setInt(8, m.getId());
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
    public List<Supplier> all() {
        List<Supplier> lm = null;
        try {
            lm = new ArrayList<>();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(all);
            while(res.next()){
                Supplier m = new Supplier();
                m.setId(res.getInt(1));
                m.setName(res.getString(2));
                m.setAddress(res.getString(3));
                m.setCity(res.getString(4));
                m.setProvince(res.getString(5));
                m.setPostCode(res.getString(6));
                m.setPhone(res.getString(7));
                m.setContact(res.getString(8));
                lm.add(m);
            }
        } catch (SQLException e){
            System.err.println(e);
        }
        
        return lm;
    }

    @Override
    public List<Supplier> find(String name) {
        List<Supplier> lm = null;
        try {
            lm = new ArrayList<>();
            PreparedStatement stat = conn.prepareStatement(find);
            stat.setString(1, "%" + name + "%");
            ResultSet res = stat.executeQuery();
            while(res.next()){
                Supplier m = new Supplier();
                m.setId(res.getInt(1));
                m.setName(res.getString(2));
                m.setAddress(res.getString(3));
                m.setCity(res.getString(4));
                m.setProvince(res.getString(5));
                m.setPostCode(res.getString(6));
                m.setPhone(res.getString(7));
                m.setContact(res.getString(8));
                lm.add(m);
            }
        } catch (SQLException e){
            System.err.println(e);
        }
        
        return lm;
    }
    
    @Override
    public List<Supplier> actualFind(String name) {
        List<Supplier> lm = null;
        try {
            lm = new ArrayList<>();
            PreparedStatement stat = conn.prepareStatement(aFind);
            stat.setString(1, name);
            ResultSet res = stat.executeQuery();
            while(res.next()){
                Supplier m = new Supplier();
                m.setId(res.getInt(1));
                m.setName(res.getString(2));
                m.setAddress(res.getString(3));
                m.setCity(res.getString(4));
                m.setProvince(res.getString(5));
                m.setPostCode(res.getString(6));
                m.setPhone(res.getString(7));
                m.setContact(res.getString(8));
                lm.add(m);
            }
        } catch (SQLException e){
            System.err.println(e);
        }
        
        return lm;
    }
    
}
