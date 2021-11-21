package com.hamdanas.utilities;

import com.hamdanas.dao.InvoiceDao;
import com.hamdanas.dao.interfaces.InvoiceImp;
import com.hamdanas.models.Invoice;
import java.util.List;

/**
 *
 * @author NESAS
 */
public class InvoiceCode {
    private static InvoiceImp imp;
    private static List<Invoice> li;
    private static boolean lis;
    
    public static String generate(String prefix, String table){
        imp = new InvoiceDao();
        li = imp.get(table);
        
        String today = Date.now("");
         
        //Seq berurut
        String var3 = null;

        if(newSeqToday(table)) { //check new seq
            var3 = "001";
        } else {
            String getSeqBefore = Integer.toString(li.get(0).getSeq()); //Get from database seq max today
            int newSeq = Integer.parseInt(getSeqBefore) + 1;
            
            if(newSeq >= 100) {
                System.out.println("Out of Maximal Seq");
            } else if (newSeq >= 10) {
                var3 = "0" + newSeq;
            } else if (newSeq >= 1) {
                var3 = "00" + newSeq;
            } else {
                System.out.println("Invalid Seq");
            }               
        }
        
        return prefix + "/" + today + "/" + var3;
        
    }
    
    public static boolean newSeqToday(String table) {
         //TODO : Check database is today have seq
         //jika hari ini tidak punya seq, maka harus bikin seq baru = true

         imp = new InvoiceDao();
         lis = imp.newSeq(table);
         
         return lis;
         
     }
    
    public static int getSequenceNum(String table){
        imp = new InvoiceDao();
        li = imp.get(table);
        int num = li.get(0).getSeq();
        
        System.out.println("seq number : " + num);
        
        if(newSeqToday(table)){
            return 1;
        } else {
            return ++num;
        }
    }
}
