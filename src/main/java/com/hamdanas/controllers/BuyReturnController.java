/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hamdanas.controllers;

import com.hamdanas.dao.InvoiceDao;
import com.hamdanas.dao.MedDao;
import com.hamdanas.dao.PurchReturnDetailDao;
import com.hamdanas.dao.PurchaseReturnDao;
import com.hamdanas.dao.interfaces.InvoiceImp;
import com.hamdanas.dao.interfaces.MedImp;
import com.hamdanas.dao.interfaces.PurchReturnDetailImp;
import com.hamdanas.dao.interfaces.PurchaseReturnImp;
import com.hamdanas.models.Invoice;
import com.hamdanas.models.Med;
import com.hamdanas.models.Transaction;
import com.hamdanas.models.TransactionDetail;
import com.hamdanas.utilities.Date;
import com.hamdanas.utilities.InvoiceCode;
import com.hamdanas.utilities.Table;
import java.util.List;
import javax.swing.JOptionPane;
import com.hamdanas.models.PurchaseReturn;
import com.hamdanas.models.PurchaseReturnDetail;
import com.hamdanas.views.ReturPembelian;

/**
 *
 * @author hamdan
 */
public class BuyReturnController {
    ReturPembelian frame;
    PurchaseReturnImp tImp;
    PurchReturnDetailImp tdImp;
    InvoiceImp iImp;
    MedImp mImp;
    Table table;
    private List<Med> lm, ls;
    
    private enum State{
        isOpen,
        isNotOpen
    }
    
    private State currentState;
    private int changes;
    private boolean passChanges = false;
    
    public BuyReturnController(ReturPembelian frame){
        this.frame = frame;
        tImp = new PurchaseReturnDao();
        tdImp = new PurchReturnDetailDao();
        iImp = new InvoiceDao();
        mImp = new MedDao();
        
        table = new Table(frame.getTblData());
        table.setColumn(new String[]{"ID Obat", "Nama Obat", "Harga", "Qty", "Total"});
        table.setColumnWidth(578, 10, 55, 15, 5, 15);
        table.textCenter(0);
        table.textLeft(1);
        table.textCenter(2);
        table.textCenter(3);
        table.textCenter(4);
        
        currentState = State.isNotOpen;
    }
    
    public void Open(){
        currentState = State.isOpen;
    }
    
    public void saveReturn(){
        if(passChanges){
            PurchaseReturn t = new PurchaseReturn();
            t.setDate(Date.now());
            t.setTotal(Integer.parseInt(frame.gettTotal().getText()));
            t.setPurchaseCode(InvoiceCode.generate("PMR", "purchase_return"));

            tImp.insert(t);

            for (int i = 0; i < table.getRowCount(); i++){
                PurchaseReturnDetail td = new PurchaseReturnDetail();
                int medId = Integer.parseInt((String) table.getColumnValue(i, 0));
                int qty = Integer.parseInt((String) table.getColumnValue(i, 3));

                td.setMedId(medId);
                td.setQty(qty);

                td.setPurchaseId(t.getId());

                tdImp.insert(td);

                mImp.subStock(qty, medId);
            }

            Invoice i = new Invoice();

            i.setDate(Date.now());
            i.setSeq(InvoiceCode.getSequenceNum("purchase_return"));

            iImp.insert(i, "purchase_return");

            JOptionPane.showMessageDialog(frame, "Transaksi berhasil, Uang kembaliannya adalah " + changes, "Pembayaran berhasil", JOptionPane.INFORMATION_MESSAGE);
            reset();
        }
    }
    
    public void reset(){
        frame.gettNama().setSelectedItem("");
        frame.gettQty().setText("");
        frame.gettTotal().setText("");
        frame.gettBayar().setText("");
        
        table.clearRow();
    }
    
    public void clearInput(){
        frame.gettQty().setText("");
    }
    
    public void fillCombo(){
        frame.gettNama().removeAllItems();
        
        lm = tImp.getMed();
                
        lm.forEach(lm1 -> {
            frame.gettNama().addItem(lm1.getName());
        });
    }
    
    public void addRow(){
        lm = tImp.getMedByName((String) frame.gettNama().getSelectedItem());
        
        String[] data = new String[5];
        
        if(frame.gettQty().getText().equals("")){
            JOptionPane.showMessageDialog(frame, "Qty tidak boleh kosong!", "Retur Pembelian", JOptionPane.WARNING_MESSAGE);
            frame.gettQty().requestFocus();
            return;
        }
        
        lm.forEach((lm1) -> {
            data[0] = Integer.toString(lm1.getId());
            data[1] = lm1.getName();
            data[2] = Integer.toString(lm1.getPrice());
            data[3] = frame.gettQty().getText();
            data[4] = Integer.toString(lm1.getPrice() * Integer.parseInt(data[3]));
        });
        
        ls = mImp.find((String) frame.gettNama().getSelectedItem());
        
        if(frame.gettNama().getSelectedIndex() < 0){
            JOptionPane.showMessageDialog(frame, "Silahkan pilih data terlebih dahulu!");
        }
        
        if(ls.get(0).getStock() < Integer.parseInt(frame.gettQty().getText())){
            JOptionPane.showMessageDialog(frame, "Stok tidak mencukupi", "Retur Pembelians", JOptionPane.WARNING_MESSAGE);
            frame.gettQty().requestFocus();
        } else {
            table.addRow(data);
        }
        
    }
    
    public void deleteRow(){
        table.removeRow(frame.getTblData().getSelectedRow());
    }
    
    public void calculateTotal(){
        int total = 0;
        
        for(int i = 0; i < table.getRowCount(); i++){
            int amount = Integer.parseInt((String) table.getColumnValue(i, 4));
            total += amount;
        }
        
        frame.gettTotal().setText(Integer.toString(total));
    }
    
    public void calculateChanges(){
        int total = Integer.parseInt(frame.gettTotal().getText());
        int pay = Integer.parseInt(frame.gettBayar().getText());
        
        if(pay <= total){
            JOptionPane.showMessageDialog(frame, "Uang anda tidak cukup, uang yang harus dibayar adalah " + total, "Penjualan", JOptionPane.WARNING_MESSAGE);
            passChanges = false;
        } else {
            setChanges(pay - total);
            passChanges = true;
        }
    }
    
    private void setChanges(int changes){
        this.changes = changes;
    }
    
    public void getMedById(){
        lm = tImp.getMedById(frame.gettId().getText());
        
        if(lm.isEmpty()){
            JOptionPane.showMessageDialog(frame, "Data obat tidak ditemukan!", "Data Obat", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        frame.gettNama().setSelectedItem(lm.get(0).getName());
    }
    
    public void getMedByName(){
        lm = tImp.getMedByName((String) frame.gettNama().getSelectedItem());
        
        if(!lm.isEmpty()){
            frame.gettNama().setSelectedItem(lm.get(0).getName());
            frame.gettStok().setText(Integer.toString(lm.get(0).getStock()));
            frame.gettId().setText(Integer.toString(lm.get(0).getId()));
        }
    }
}
