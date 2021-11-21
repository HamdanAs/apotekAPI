/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hamdanas.controllers;

import com.hamdanas.dao.InvoiceDao;
import com.hamdanas.dao.MedDao;
import com.hamdanas.dao.TransDetailDao;
import com.hamdanas.dao.TransactionDao;
import com.hamdanas.dao.interfaces.InvoiceImp;
import com.hamdanas.dao.interfaces.MedImp;
import com.hamdanas.dao.interfaces.TransDetailImp;
import com.hamdanas.dao.interfaces.TransactionImp;
import com.hamdanas.models.Invoice;
import com.hamdanas.models.Med;
import com.hamdanas.models.Transaction;
import com.hamdanas.models.TransactionDetail;
import com.hamdanas.utilities.Date;
import com.hamdanas.utilities.InvoiceCode;
import com.hamdanas.utilities.Table;
import java.util.List;
import javax.swing.JOptionPane;
import com.hamdanas.utilities.Currency;
import com.hamdanas.views.Penjualan;

/**
 *
 * @author hamdan
 */
public class SellController {
    private final Penjualan frame;
    private final TransactionImp tImp;
    private final TransDetailImp tdImp;
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
    private int chargeTotal;
    
    public SellController(Penjualan frame){
        this.frame = frame;
        tImp = new TransactionDao();
        tdImp = new TransDetailDao();
        iImp = new InvoiceDao();
        mImp = new MedDao();
        table = new Table(frame.getTblData());
        
        initTable();
        
        currentState = State.isNotOpen;
        
        frame.gettTanggal().setText(Date.now());
        frame.gettKode().setText(InvoiceCode.generate("PJ", "transaction"));
    }
    
    private void initTable(){
        table.setColumn(new String[]{"ID Obat", "Nama Obat", "Harga", "Qty", "Total"});
        table.setColumnWidth(578, 10, 55, 15, 5, 15);
        table.textCenter(0);
        table.textLeft(1);
        table.textCenter(2);
        table.textCenter(3);
        table.textCenter(4);
    }
    
    public void Open(){
        currentState = State.isOpen;
    }
    
    public void saveTransaction(){
        if(passChanges){
            Transaction t = new Transaction();
            t.setDate(Date.now());
            t.setTotal(chargeTotal);
            t.setTransactionCode(InvoiceCode.generate("PJ", "transaction"));

            tImp.insert(t);

            System.out.println(t.getId());
            
            for (int i = 0; i < table.getRowCount(); i++){
                TransactionDetail td = new TransactionDetail();
                int medId = Integer.parseInt((String) table.getColumnValue(i, 0));
                int qty = Integer.parseInt((String) table.getColumnValue(i, 3));

                td.setMedId(medId);
                td.setQty(qty);

                td.setTransactionId(t.getId());

                tdImp.insert(td);

                mImp.subStock(qty, medId);
            }

            Invoice i = new Invoice();

            i.setDate(Date.now());
            i.setSeq(InvoiceCode.getSequenceNum("transaction"));

            iImp.insert(i, "transaction");

            JOptionPane.showMessageDialog(frame, "Transaksi berhasil, Uang kembaliannya adalah " + changes, "Pembayaran berhasil", JOptionPane.INFORMATION_MESSAGE);
            reset();
        }
    }
    
    public void reset(){
        frame.gettNama().setText("");
        frame.gettQty().setText("");
        frame.gettTotal().setText("Rp. 0,00");
        frame.gettBayar().setText("");
        frame.gettStok().setText("");
        frame.gettKode().setText(InvoiceCode.generate("PJ", "transaction"));
        
        table.clearRow();
    }
    
    public void clearInput(){
        frame.gettNama().setText("");
        frame.gettJual().setText("");
        frame.gettId().setText("");
        frame.gettQty().setText("");
        frame.gettStok().setText("");
    }
    
    public void addRow(){
        lm = tImp.getMedByName((String) frame.gettNama().getText());
        
        String[] data = new String[6];
        
        if(frame.gettId().getText().equals("")){
            JOptionPane.showMessageDialog(frame, "Data obat tidak boleh kosong!", "Pembelian", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if(frame.gettQty().getText().equals("")){
            JOptionPane.showMessageDialog(frame, "Qty tidak boleh kosong!", "Penjualan", JOptionPane.WARNING_MESSAGE);
            frame.gettQty().requestFocus();
            return;
        }
        
        int qty = Integer.parseInt(frame.gettQty().getText());
        int stok = Integer.parseInt(frame.gettStok().getText());
        
        if(qty > stok){
            JOptionPane.showMessageDialog(frame, "Stok tidak mencukupi", "Penjualan", JOptionPane.WARNING_MESSAGE);
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
        
        table.addRow(data);
        
        clearInput();
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
        chargeTotal = total;
        
        String output = Currency.make(total);
        
        frame.gettTotal().setText(output);
    }
    
    public void calculateChanges(){
        int total = (int) chargeTotal;
        int pay = Integer.parseInt(frame.gettBayar().getText());
        
        if(pay < total){
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
        
        if(!lm.isEmpty()){
            frame.gettNama().setText(lm.get(0).getName());
            frame.gettId().setText(Integer.toString(lm.get(0).getId()));
            frame.gettJual().setText(Integer.toString(lm.get(0).getPrice()));
            frame.gettStok().setText(Integer.toString(lm.get(0).getStock()));
        }
    }
    
    public void getMedByName(){
        lm = tImp.getMedByName((String) frame.gettNama().getText());
        
        if(!lm.isEmpty()){
            frame.gettNama().setText(lm.get(0).getName());
            frame.gettId().setText(Integer.toString(lm.get(0).getId()));
            frame.gettJual().setText(Integer.toString(lm.get(0).getPrice()));
            frame.gettStok().setText(Integer.toString(lm.get(0).getStock()));
        }
    }
}
