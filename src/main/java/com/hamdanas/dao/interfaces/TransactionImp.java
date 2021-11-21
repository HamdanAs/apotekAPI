/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hamdanas.dao.interfaces;

import com.hamdanas.models.Med;
import com.hamdanas.models.Transaction;
import java.util.List;

/**
 *
 * @author hamdan
 */
public interface TransactionImp {
    public void insert(Transaction t);
    public List<Med> getMed();
    public List<Med> getMedByName(String name);
    public List<Med> getMedById(String id);
    public List<Transaction> all();
    
}
