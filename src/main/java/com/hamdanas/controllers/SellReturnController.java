/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hamdanas.controllers;

import com.hamdanas.dao.InvoiceDao;
import com.hamdanas.dao.MedDao;
import com.hamdanas.dao.TransReturnDetailDao;
import com.hamdanas.dao.TransactionReturnDao;
import com.hamdanas.dao.interfaces.InvoiceImp;
import com.hamdanas.dao.interfaces.MedImp;
import com.hamdanas.dao.interfaces.TransReturnDetailImp;
import com.hamdanas.dao.interfaces.TransactionReturnImp;
import com.hamdanas.models.Invoice;
import com.hamdanas.models.Med;
import com.hamdanas.utilities.Date;
import com.hamdanas.utilities.InvoiceCode;
import com.hamdanas.utilities.Table;
import java.util.List;
import javax.swing.JOptionPane;
import com.hamdanas.models.TransactionReturn;
import com.hamdanas.models.TransactionReturnDetail;
import com.hamdanas.views.ReturPenjualan;

/**
 *
 * @author hamdan
 */
public class SellReturnController {
    private final ReturPenjualan frame;
    private final TransactionReturnImp tImp;
    private final TransReturnDetailImp tdImp;
    private final InvoiceImp iImp;
    private final MedImp mImp;
    private final Table table;
    private List<Med> lm, ls;
    
    private enum State{
        isOpen,
        isNotOpen
    }
    
    private State currentState;
    private int changes;
    private boolean passChanges = false;
    
    public SellReturnController(ReturPenjualan frame){
        this.frame = frame;
        tImp = new TransactionReturnDao();
        tdImp = new TransReturnDetailDao();
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
            TransactionReturn t = new TransactionReturn();
            t.setDate(Date.now());
            t.setTotal(Integer.parseInt(frame.gettTotal().getText()));
            t.setTransactionCode(InvoiceCode.generate("PJR", "transaction_return"));

            tImp.insert(t);

            for (int i = 0; i < table.getRowCount(); i++){
                TransactionReturnDetail td = new TransactionReturnDetail();
                int medId = Integer.parseInt((String) table.getColumnValue(i, 0));
                int qty = Integer.parseInt((String) table.getColumnValue(i, 3));

                td.setMedId(medId);
                td.setQty(qty);

                td.setTransactionId(t.getId());

                tdImp.insert(td);

                mImp.addStock(qty, medId);
            }

            Invoice i = new Invoice();

            i.setDate(Date.now());
            i.setSeq(InvoiceCode.getSequenceNum("transaction_return"));

            iImp.insert(i, "transaction_return");

            JOptionPane.showMessageDialog(frame, "Transaksi berhasil, Uang kembaliannya adalah " + changes, "Return Penjualan", JOptionPane.INFORMATION_MESSAGE);
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
            JOptionPane.showMessageDialog(frame, "Qty tidak boleh kosong!", "Penjualan", JOptionPane.WARNING_MESSAGE);
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
        
        table.addRow(data);
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
