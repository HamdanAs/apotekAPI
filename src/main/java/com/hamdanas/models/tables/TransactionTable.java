/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hamdanas.models.tables;

import com.hamdanas.models.Transaction;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author hamdan
 */
public class TransactionTable extends AbstractTableModel{
    List<Transaction> lt = null;
    
    public TransactionTable(List<Transaction> lt){
        this.lt = lt;
    }
    
    @Override
    public int getRowCount() {
        return lt.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }
    
    @Override
    public String getColumnName(int column){
        switch (column){
            case 0:
                return "Id";
            case 1:
                return "Date";
            case 2:
                return "Invoice";
            default:
                return null;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                return lt.get(rowIndex).getId();
            case 1:
                return lt.get(rowIndex).getDate();
            case 2:
                return lt.get(rowIndex).getTransactionCode();
            default: 
                return null;
        }
    }
}
