/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hamdanas.database;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author hamdan
 */
public class Database {
    static Connection conn;
    
    public static Connection connection(){
        if (conn == null){
            MysqlDataSource sql = new MysqlDataSource();
            sql.setDatabaseName("apotek");
            sql.setUser("root");
            sql.setPassword("");
            
            try {
                conn = sql.getConnection();
            } catch (SQLException e){
                JOptionPane.showMessageDialog(null, "Koneksi ke database gagal! silahkan cek koneksi terlebih dahulu!", "Koneksi Gagal", JOptionPane.WARNING_MESSAGE);
                System.exit(0);
                return null;
            }
        }

        return conn;
    }
}
