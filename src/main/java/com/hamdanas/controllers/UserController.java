package com.hamdanas.controllers;

//import dao.LoginDao;
//import dao.interfaces.LoginImp;
//import java.util.List;
//import javax.swing.JOptionPane;
//import models.Login;
//import models.tables.MedTable;
//import models.tables.UserTable;
//import utilities.Table;
//import views.MainFrm;
//
///**
// *
// * @author NESAS
// */
//public class UserController {
//    private final MainFrm frame;
//    private final LoginImp loginImp;
//    private List<Login> list;
//    private final Table table;
//    
//    public UserController(MainFrm frame){
//        this.frame = frame;
//        loginImp = new LoginDao();
//        
//        table = new Table(frame.getUser_table());
//        table.setColumn(new String[]{"ID", "Username", "Level"});
//        table.setColumnWidth(567, 10, 80, 10);
//        table.textCenter(0);
//        table.textLeft(1);
//        table.textCenter(2);
//    }
//    
//    public void fillTable(){
//        list = loginImp.all();
//        UserTable mt = new UserTable(list);
//        frame.getUser_table().setModel(mt);
//        frame.getUser_find().setText("");
//    }
//    
//    public void fillFindTable(){
//        list = loginImp.find(frame.getUser_find().getText());
//        UserTable mt = new UserTable(list);
//        frame.getUser_table().setModel(mt);
//    }
//    
//    public void find(){
//        if(!frame.getUser_find().getText().trim().isEmpty()){
//            loginImp.find(frame.getUser_find().getText());
//            fillFindTable();
//        } else {
//            JOptionPane.showMessageDialog(null, "Silahkan pilih data!");
//        }
//    }
//    
//    public void clear(){
//        frame.getCp_new().setText("");
//        frame.getCp_old().setText("");
//        frame.getCp_confirm().setText("");
//    }
//    
//    public void changePassword(){
//        Login l = new Login();
//        list = loginImp.getPassword(frame.getId());
//        
//        String oldPassword = new String(frame.getCp_old().getPassword());
//        String newPassword = new String(frame.getCp_new().getPassword());
//        String confirmPassword = new String(frame.getCp_confirm().getPassword());
//        
//        System.out.println(oldPassword);
//        System.out.println(newPassword);
//        System.out.println(confirmPassword);
//        
//        l.setId(frame.getId());
//        l.setPassword(newPassword);
//        
//        if(!newPassword.equals(confirmPassword)){
//            JOptionPane.showMessageDialog(null, "Konfirmasi password salah!");
//        } else {
//            if(!oldPassword.equals(list.get(0).getPassword())){
//                JOptionPane.showMessageDialog(null, "Password lama yang anda masukan salah!");
//            } else {
//                loginImp.update(l);
//        
//                JOptionPane.showMessageDialog(null, "Password berhasil diubah");
//            }
//        }
//    }
//    
//    public void delete(){
//        if (JOptionPane.showConfirmDialog(null, "Apakah anda yakin ingin menghapus data ini?", "Hapus User", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
//                int id = Integer.parseInt(frame.getUser_table().getValueAt(frame.getUser_table().getSelectedRow(), 0).toString());
//                loginImp.delete(id);
//        }
//    }
//}
