/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hamdanas.dao;

import com.hamdanas.dao.interfaces.LoginImp;
import com.hamdanas.database.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.hamdanas.models.Login;

/**
 *
 * @author Gawrgura
 */
public class LoginDao implements LoginImp{
    Connection conn;
    
    private final String info = "select * from user where username = ? and password = ?";
    private final String password = "select password from user where id = ?";
    private final String insert = "insert into user values(null, ?, ?, ?)";
    private final String update = "update user set password = ? where id = ?";
    private final String delete = "delete from user where id = ?";
    private final String all = "select * from user";
    private final String find = "select * from user where username like ?";
    
    public LoginDao(){
        conn = Database.connection();
    }

    @Override
    public List<Login> getInfo(String username, String password) {
        List<Login> list = null;
        try {
            list = new ArrayList<>();
            PreparedStatement stat = conn.prepareStatement(info);
            stat.setString(1, username);
            stat.setString(2, password);
            ResultSet res = stat.executeQuery();
            while(res.next()){
                Login l = new Login();
                l.setId(res.getInt(1));
                l.setUsername(res.getString(2));
                l.setPassword(res.getString(3));
                l.setLevel(res.getInt(4));
                
                list.add(l);
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        
        return list;
    }

    @Override
    public void insert(Login l) {
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(insert);
            stat.setString(1, l.getUsername());
            stat.setString(2, l.getPassword());
            stat.setInt(3, l.getLevel());
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
    public void update(Login l) {
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(update);
            stat.setString(1, l.getPassword());
            stat.setInt(2, l.getId());
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
    public List<Login> all() {
        List<Login> lm = null;
        try {
            lm = new ArrayList<>();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(all);
            while(res.next()){
                Login m = new Login();
                m.setId(res.getInt(1));
                m.setUsername(res.getString(2));
                m.setLevel(res.getInt(4));
                lm.add(m);
            }
        } catch (SQLException e){
            System.err.println(e);
        }
        
        return lm;
    }

    @Override
    public List<Login> find(String name) {
        List<Login> lm = null;
        try {
            lm = new ArrayList<>();
            PreparedStatement stat = conn.prepareStatement(find);
            stat.setString(1, "%" + name + "%");
            ResultSet res = stat.executeQuery();
            while(res.next()){
                Login m = new Login();
                m.setId(res.getInt(1));
                m.setUsername(res.getString(2));
                m.setLevel(res.getInt(4));
                lm.add(m);
            }
        } catch (SQLException e){
            System.err.println(e);
        }
        
        return lm;
    }

    @Override
    public List<Login> getPassword(int id) {
        List<Login> lm = null;
        try {
            lm = new ArrayList<>();
            PreparedStatement stat = conn.prepareStatement(password);
            stat.setInt(1, id);
            ResultSet res = stat.executeQuery();
            while(res.next()){
                Login m = new Login();
                m.setPassword(res.getString(1));
                lm.add(m);
            }
        } catch (SQLException e){
            System.err.println(e);
        }
        
        return lm;
    }
}
