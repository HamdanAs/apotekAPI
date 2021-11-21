package com.hamdanas.dao.interfaces;

import com.hamdanas.models.Invoice;
import java.util.List;

/**
 *
 * @author NESAS
 */
public interface InvoiceImp {
    public void insert(Invoice i, String table);
    public List<Invoice> get(String table);
    public boolean newSeq(String table);
}
