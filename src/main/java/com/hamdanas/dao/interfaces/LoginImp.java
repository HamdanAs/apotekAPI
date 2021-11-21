/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hamdanas.dao.interfaces;

import java.util.List;
import com.hamdanas.models.Login;

/**
 *
 * @author Gawrgura
 */
public interface LoginImp {
    public List<Login> getInfo(String username, String password);
    public void insert(Login l);
    public void update(Login l);
    public void delete(int id);
    public List<Login> all();
    public List<Login> find(String name);
    public List<Login> getPassword(int id);
}
