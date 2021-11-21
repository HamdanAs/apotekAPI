package com.hamdanas.models.tables;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import com.hamdanas.models.Login;

/**
 *
 * @author NESAS
 */
public class UserTable extends AbstractTableModel{
    List<Login> lm = null;
    
    public UserTable(List<Login> lm){
        this.lm = lm;
    }

    @Override
    public int getRowCount() {
        return lm.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }
    
    @Override
    public String getColumnName(int column){
        switch (column){
            case 0:
                return "Id";
            case 1:
                return "Username";
            case 2:
                return "Level";
            default:
                return null;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                return lm.get(rowIndex).getId();
            case 1:
                return lm.get(rowIndex).getUsername();
            case 2:
                return lm.get(rowIndex).getLevel();
            default: 
                return null;
        }
    }
    
    
}
