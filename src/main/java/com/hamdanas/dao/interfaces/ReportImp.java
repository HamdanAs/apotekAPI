/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hamdanas.dao.interfaces;

import java.util.List;

/**
 *
 * @author NESAS
 */
public interface ReportImp {
    public List<String> loadDateSell();
    public List<String> loadMonthSell();
    public List<String> loadYearSell();
    public List<String> loadDateBuy();
    public List<String> loadMonthBuy();
    public List<String> loadYearBuy();
}
