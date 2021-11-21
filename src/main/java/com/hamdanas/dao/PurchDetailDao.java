package com.hamdanas.dao;

import com.hamdanas.dao.interfaces.PurchDetailImp;
import com.hamdanas.database.Database;
import com.hamdanas.models.PurchaseDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author NESAS
 */
public class PurchDetailDao implements PurchDetailImp{

    Connection conn;
    
    private final String insert = "insert into purchase_details values (null,?,?,?)";
    
    public PurchDetailDao(){
        conn = Database.connection();
    }
    
    @Override
    public void insert(PurchaseDetail td) {
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(insert, PreparedStatement.RETURN_GENERATED_KEYS);
            stat.setInt(1, td.getMedId());
            stat.setInt(2, td.getQty());
            stat.setInt(3, td.getPurchaseId());
            stat.executeUpdate();
            
            ResultSet rs = stat.getGeneratedKeys();
            while(rs.next()){
                td.setId(rs.getInt(1));
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

}
