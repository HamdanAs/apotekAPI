/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hamdanas.models.tables;

import com.hamdanas.models.Med;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import com.hamdanas.models.Supplier;

/**
 *
 * @author hamdan
 */
public class SupplierTable extends AbstractTableModel{
    
    List<Supplier> lm = null;
    
    public SupplierTable(List<Supplier> lm){
        this.lm = lm;
    }
    
    @Override
    public int getRowCount() {
        return lm.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }
    
    @Override
    public String getColumnName(int column){
        switch (column){
            case 0:
                return "Id";
            case 1:
                return "Nama";
            case 2:
                return "Alamat";
            case 3:
                return "Kota";
            case 4:
                return "Provinsi";
            case 5:
                return "Kode Pos";
            case 6:
                return "No Telepon";
            case 7:
                return "No Kontak";
            default:
                return null;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                return lm.get(rowIndex).getId();
            case 1:
                return lm.get(rowIndex).getName();
            case 2:
                return lm.get(rowIndex).getAddress();
            case 3:
                return lm.get(rowIndex).getCity();
            case 4:
                return lm.get(rowIndex).getProvince();
            case 5:
                return lm.get(rowIndex).getPostCode();
            case 6:
                return lm.get(rowIndex).getPhone();
            case 7:
                return lm.get(rowIndex).getContact();
            default: 
                return null;
        }
    }
    
}
