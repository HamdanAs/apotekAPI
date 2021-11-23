/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hamdanas.dao.interfaces;

import java.util.List;
import com.hamdanas.models.Supplier;

/**
 *
 * @author hamdan
 */
public interface SupplierImp {
    public void insert(Supplier m);
    public void update(Supplier m);
    public void delete(int id);
    public List<Supplier> all();
    public List<Supplier> find(int id);    
    public List<Supplier> actualFind(String name);
}
