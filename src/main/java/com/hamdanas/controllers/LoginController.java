/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hamdanas.controllers;

import com.hamdanas.dao.LoginDao;
import com.hamdanas.dao.interfaces.LoginImp;
import java.util.List;
import javax.swing.JOptionPane;
import com.hamdanas.models.Login;
import com.hamdanas.views.LoginFrm;
import com.hamdanas.views.MainMenu;

/**
 *
 * @author Gawrgura
 */
public class LoginController {
    private final LoginFrm frame;
    private final LoginImp loginImp;
    private List<Login> list;
    
    public LoginController(LoginFrm frame){
        this.frame = frame;
        loginImp = new LoginDao();
    }
    
    public void login(){
        String username = frame.gettUsername().getText();
        String password = new String(frame.gettPassword().getPassword());
        
        list = loginImp.getInfo(username, password);
        
        if(list.isEmpty()){
            JOptionPane.showMessageDialog(null, "Login Gagal! Silahkan masukan data yang benar!", "Login gagal", JOptionPane.WARNING_MESSAGE);
        } else {
            MainMenu mainMenu = new MainMenu();
            mainMenu.setVisible(true);
//            mainFrm.changeUsername(username);
//            mainFrm.setUsername(username);
//            mainFrm.setId(list.get(0).getId());
//            mainFrm.setLevel(list.get(0).getLevel());
//            mainFrm.checkLevel();
            frame.dispose();
        }
    }
}
