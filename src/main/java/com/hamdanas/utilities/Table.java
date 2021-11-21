/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hamdanas.utilities;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author hamdan
 */
public class Table {
    private JTable table;
    private final DefaultTableModel model;
    
    public Table(JTable table){
        this.table = table;
        this.model = new DefaultTableModel();
        table.setModel(model);
    }
    
    public Table(JTable table, DefaultTableModel model){
        this.table = table;
        this.model = model;
        table.setModel(this.model);
    }
    
    public Table(){
       this.model = new DefaultTableModel();
    }
    
    public void setTable(JTable table){
        this.table = table;
        table.setModel(model);
    }
    
    public void setColumn(String[] colName){
        int colLength = colName.length;
        for (int i = 0; i < colLength; i++){
            model.addColumn(colName[i]);
        }
    }
    
    public void setColumnWidth(int tablePreferredWidth, double... percentages){
        double total = 0;
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            total += percentages[i];
        }

        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth((int)
                    (tablePreferredWidth * (percentages[i] / total)));
        }
    }
    
    public void addRow(Object[] data){
        model.addRow(data);
    }
    
    public int getRowIndex(){
        return table.getSelectedRow();
    }
    
    public int getColumnIndex(){
        return table.getSelectedColumn();
    }
    
    public int getRowCount(){
        return table.getRowCount();
    }
    
    public int getColumnCount(){
        return table.getColumnCount();
    }
    
    public Object getColumnValue(int row, int column){
        return model.getValueAt(row, column);
    }
    
    public void removeRow(int row){
        model.removeRow(row);
    }
    
    public void clearRow(){        
        model.setRowCount(0);
    }
    
    public void textCenter(int col){
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        table.getTableHeader().getColumnModel().getColumn(col).setHeaderRenderer(renderer);
        table.getColumnModel().getColumn(col).setCellRenderer(renderer);
    }
    
    public void textLeft(int col){
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(JLabel.LEFT);
        table.getTableHeader().getColumnModel().getColumn(col).setHeaderRenderer(renderer);
        table.getColumnModel().getColumn(col).setCellRenderer(renderer);
    }
    
    public void textRight(int col){
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(JLabel.RIGHT);
        table.getTableHeader().getColumnModel().getColumn(col).setHeaderRenderer(renderer);
        table.getColumnModel().getColumn(col).setCellRenderer(renderer);
    }
}
