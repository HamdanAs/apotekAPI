/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hamdanas.models.tables;

import com.hamdanas.models.Med;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author hamdan
 */
public class MedTable extends AbstractTableModel{
    
    List<Med> lm = null;
    
    public MedTable(List<Med> lm){
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
                return "Harga Beli";
            case 3:
                return "Harga Jual";
            case 4:
                return "Stok";
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
                return lm.get(rowIndex).getBasePrice();
            case 3:
                return lm.get(rowIndex).getPrice();
            case 4:
                return lm.get(rowIndex).getStock();
            default: 
                return null;
        }
    }
    
}
