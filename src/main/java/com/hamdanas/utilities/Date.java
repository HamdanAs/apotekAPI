/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hamdanas.utilities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author hamdan
 */
public class Date {
    private static final LocalDateTime date = LocalDateTime.now();
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static String formatted = date.format(format);
    
    public static String now(){
        return formatted;
    }
    
    public static String now(String delimiter){
        DateTimeFormatter nowFormat = DateTimeFormatter.ofPattern("yyyy" + delimiter + "MM" + delimiter + "dd");
        String nowFormatted = date.format(nowFormat);
        return nowFormatted;
    }
    
    public static String plusTwo(){
        LocalDateTime addTwo = date.plusDays(2);
        Date.formatted = addTwo.format(format);
        
        return formatted;
    }
}
