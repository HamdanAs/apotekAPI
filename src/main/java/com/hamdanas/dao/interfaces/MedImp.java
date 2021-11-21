/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hamdanas.dao.interfaces;

import com.hamdanas.models.Med;
import java.util.List;

/**
 *
 * @author hamdan
 */
public interface MedImp {
    public void insert(Med m);
    public void update(Med m);
    public void addStock(int amount, int id);
    public void subStock(int amount, int id);
    public void delete(int id);
    public List<Med> all();
    public List<Med> find(String name);    
    public List<Med> find(int id);    
    public List<Med> actualFind(String name);
}
