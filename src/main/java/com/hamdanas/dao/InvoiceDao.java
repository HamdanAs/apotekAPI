package com.hamdanas.dao;

import com.hamdanas.dao.interfaces.InvoiceImp;
import com.hamdanas.database.Database;
import com.hamdanas.models.Invoice;
import com.hamdanas.utilities.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NESAS
 */
public class InvoiceDao implements InvoiceImp{

    Connection conn;
    
    private final String insert = "insert into invoice values (?, ?, ?)";
    private final String get = "select max(seq) from invoice where date = ? and section = ?";
    private final String getSeq = "SELECT isnull(max(seq) > 0) FROM `invoice` WHERE date = ? and section = ?";
    
    public InvoiceDao(){
        conn = Database.connection();
    }
    
    @Override
    public void insert(Invoice i, String table) {
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(insert);
            stat.setString(1, table);
            stat.setString(2, i.getDate());
            stat.setInt(3, i.getSeq());
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
    public List<Invoice> get(String table) {
        List<Invoice> li = null;
        
        PreparedStatement stat;
        try {
            li = new ArrayList<>();
            stat = conn.prepareStatement(get);
            stat.setString(1, Date.now());
            stat.setString(2, table);
            ResultSet res = stat.executeQuery();
            while(res.next()){
                Invoice m = new Invoice();
                m.setSeq(res.getInt(1));
                
                li.add(m);
            }
        } catch (SQLException e){
            System.err.println(e);
        } 
        
        return li;
    }

    @Override
    public boolean newSeq(String table) {
        boolean result = false;
        
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(getSeq);
            stat.setString(1, Date.now());
            stat.setString(2, table);
            ResultSet res = stat.executeQuery();
            while(res.next()){
                result = res.getBoolean(1);
                return result;
            }
        } catch (SQLException e){
            System.err.println(e);
        }
            
        return result;
    }

}
