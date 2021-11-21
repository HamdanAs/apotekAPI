/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hamdanas.controllers;

import com.hamdanas.dao.SupplierDao;
import com.hamdanas.dao.interfaces.SupplierImp;
import java.util.Arrays;
import java.util.HashMap;
import com.hamdanas.models.Supplier;
import com.hamdanas.utilities.Table;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import com.hamdanas.utilities.validator.Validator;
import com.hamdanas.views.SupplierView;

/**
 *
 * @author hamdan
 */
public class SupplierController {
    SupplierView frame;
    SupplierImp supplierImp;
    List<Supplier> lm;
    Table table;
    
    public SupplierController(SupplierView frame){
        this.frame = frame;
        supplierImp = new SupplierDao();
        lm = supplierImp.all();
        
        initTable();
    }
    
    private void initTable(){
        table = new Table(frame.getTblData());
        table.setColumn(new String[]{"ID", "Nama Supplier"});
        table.setColumnWidth(320, 10, 90);
        table.textCenter(0);
        table.textLeft(1);
    }
    
    public void reset(){
        frame.gettSearch().setText("");
        frame.gettId().setText("");
        frame.gettName().setText("");
        frame.gettAddress().setText("");
        frame.gettCity().setText("");
        frame.gettProvince().setText("");
        frame.gettPost().setText("");
        frame.gettPhone().setText("");
        frame.gettContact().setText("");
    }
    
    public void fillTable(){
        table.clearRow();
        
        lm = supplierImp.all();
        
        lm.forEach((Supplier m) -> {
            Object[] o = new Object[]{
                m.getId(),
                m.getName(),
            };
            
            table.addRow(o);
        });
    }
    
    public void fillField(int row){
        frame.gettId().setText(Integer.toString(lm.get(row).getId()));
        frame.gettName().setText(lm.get(row).getName());
        frame.gettAddress().setText(lm.get(row).getAddress());
        frame.gettCity().setText(lm.get(row).getCity());
        frame.gettProvince().setText(lm.get(row).getProvince());
        frame.gettPost().setText(lm.get(row).getPostCode());
        frame.gettPhone().setText(lm.get(row).getPhone());
        frame.gettContact().setText(lm.get(row).getContact());
    }
    
    public void insert(){
        HashMap<JComponent, String> rules = new HashMap<>();
        rules.put(frame.gettName(), "required");
        rules.put(frame.gettAddress(), "required");
        
        Validator validator = new Validator(rules);
        
        validator.validateHash();
        
        System.out.println(Arrays.toString(validator.getIsFail()));
        
        while(validator.fails()){
            JOptionPane.showMessageDialog(frame, validator.getErrorMessage(), "Error", JOptionPane.WARNING_MESSAGE);
            
            System.out.println(validator.fails());
            
            validator.validateHash();
            
            return;
        }
        
        Supplier m = new Supplier();
        m.setName(frame.gettName().getText());
        m.setAddress(frame.gettAddress().getText());
        m.setCity(frame.gettCity().getText());
        m.setProvince(frame.gettProvince().getText());
        m.setPostCode(frame.gettPost().getText());
        m.setPhone(frame.gettPhone().getText());
        m.setContact(frame.gettContact().getText());

        lm = supplierImp.actualFind(m.getName());
        
        if(lm.isEmpty()){
            supplierImp.insert(m);

            JOptionPane.showMessageDialog(frame, "Data tersimpan!", "Menejemen Obat", JOptionPane.INFORMATION_MESSAGE);

            reset();
        } else {
            JOptionPane.showMessageDialog(frame, "Supplier sudah ada didalam database!", "Menejemen Obat", JOptionPane.WARNING_MESSAGE);
        }
        
    }
    
    public void update(){
        if(frame.gettId().getText().equals("")){
            JOptionPane.showMessageDialog(frame, "Silahkan pilih data!", "Menejemen Obat", JOptionPane.WARNING_MESSAGE);
        } else {
            Supplier m = new Supplier();
            m.setName(frame.gettName().getText());
            m.setAddress(frame.gettAddress().getText());
            m.setCity(frame.gettCity().getText());
            m.setProvince(frame.gettProvince().getText());
            m.setPostCode(frame.gettPost().getText());
            m.setPhone(frame.gettPhone().getText());
            m.setContact(frame.gettContact().getText());
            m.setId(Integer.parseInt(frame.gettId().getText()));

            supplierImp.update(m);

            JOptionPane.showMessageDialog(null, "Data tersimpan!", "Menejemen Obat", JOptionPane.INFORMATION_MESSAGE);

            reset();
        }
    }
    
    public void delete(){
        if(!frame.gettId().getText().trim().isEmpty()){
            if (JOptionPane.showConfirmDialog(null, "Apakah anda yakin ingin menghapus data ini?") == JOptionPane.YES_OPTION) {
                int id = Integer.parseInt(frame.gettId().getText());
                supplierImp.delete(id);
                
                reset();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Silahkan pilih data yang ingin dihapus");
        }
    }
    
    public void fillFindTable(){
        table.clearRow();
        
        lm = supplierImp.find(frame.gettSearch().getText());
        lm.forEach((Supplier m) -> {
            Object[] o = new Object[]{
                m.getId(),
                m.getName(),
            };
            
            table.addRow(o);
        });
    }
    
    public void find(){
        if(!frame.gettSearch().getText().trim().isEmpty()){
            supplierImp.find(frame.gettSearch().getText());
            fillFindTable();
        } else {
            JOptionPane.showMessageDialog(null, "Silahkan pilih data!");
        }
    }
}
