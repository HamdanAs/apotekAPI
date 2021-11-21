/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hamdanas.utilities.validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.swing.JComponent;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Gawrgura
 */
public class Validator {
    private final JComponent field;
    private final JComponent[] fields;
    private HashMap<JComponent, String> rules;
    private final String rule;
    private boolean[] isFail;
    private List<String> messages;
    
    private final static String NOT_EMPTY_REGEX = "^(?!\\s*$)"; 
    private final static String ONLY_NUMBER_REGEX = "-?\\d+";
    
    public Validator(String rule, JComponent field){
        this.rule = rule;
        this.field = field;
        this.fields = null;
    }
    
    public Validator(String rule, JComponent[] fields){
        this.rule = rule;
        this.field = null;
        this.fields = fields;
    }
    
    public Validator(HashMap<JComponent, String> rules){
        this.rules = rules;
        this.rule = null;
        this.field = null;
        this.fields = null;
        this.messages = new ArrayList<>();
        this.isFail = new boolean[rules.size()];
        setBoolean();
    }

    public void validateHash(){
        rules.forEach((JComponent c, String r) -> {
            if(r.equals("required")){
                checkPattern(NOT_EMPTY_REGEX, c);
                messages.add("Data tidak boleh kosong!");
            } else if(r.equals("number")){
                checkPattern(ONLY_NUMBER_REGEX, c);
                messages.add("Data hanya boleh diisi dengan angka!");
            }
        });
    }
    
    public void validate(){
        if(rule.equals("required")){
            checkRequired();
        } else if(rule.equals("number")){
            checkNumber();
        }
    }
    
    public boolean fails(){        
        for(boolean f : isFail){
            if(f == true){
                return true;
            }
        }
        
        return false;
    }
    
    public boolean[] getIsFail(){
        return isFail;
    }
    
    public String getErrorMessage(){
        String message = "";
        List<String> newList = messages.stream().distinct().collect(Collectors.toList());
        
        for (String m : newList) {
            message = message.concat(m).concat("\n");
        }
        
        return message;
    }
    
    private void checkPattern(String p, JComponent c){
        Pattern pattern = Pattern.compile(p);
        Matcher matcher;

        if(isPassword(c)){
            matcher = pattern.matcher( new String(((JPasswordField) c).getPassword()) );
        } else if(isTextArea(c)){
            matcher = pattern.matcher( ((JTextArea) c).getText() ); 
        } else {
            matcher = pattern.matcher(((JTextField) c).getText());
        }

        addBoolean(!matcher.find()); 
    }
    
    private void checkRequired(){
        Pattern pattern = Pattern.compile(NOT_EMPTY_REGEX);
            
        if(field == null){
            for(JComponent f : fields){
                Matcher matcher;
                
                if(isPassword(f)){
                    matcher = pattern.matcher( new String(((JPasswordField) f).getPassword()) );
                } else if(isTextArea(f)){
                    matcher = pattern.matcher( ((JTextArea) f).getText() ); 
                } else {
                    matcher = pattern.matcher(((JTextField) f).getText());
                }
                
                addBoolean(!matcher.find()); 
            }
        } else {
            Matcher matcher = pattern.matcher(((JTextField) field).getText());

            addBoolean(!matcher.find());
        }
    }
    
    private void checkNumber(){
        Pattern pattern = Pattern.compile(ONLY_NUMBER_REGEX);
            
        if(field == null){
            for(JComponent f : fields){
                Matcher matcher;
                
                if(isPassword(f)){
                    matcher = pattern.matcher( new String(((JPasswordField) f).getPassword()) );
                } else {
                    matcher = pattern.matcher(((JTextField) f).getText());
                }
                
                addBoolean(!matcher.find()); 
            }
        } else {
            Matcher matcher = pattern.matcher(((JTextField) field).getText());

            addBoolean(!matcher.find());
        }
    }
    
    private boolean isPassword(JComponent c){
        return c instanceof JPasswordField;
    }
    
    private boolean isTextArea(JComponent c){
        return c instanceof JTextArea;
    }
    
    private void addBoolean(boolean b){
        isFail = Arrays.copyOf(isFail, isFail.length + 1);
        isFail[isFail.length - 1] = b;
    }
    
    private void setBoolean(){
        for(int i = 0; i < isFail.length; i++){
            isFail[i] = false;
        }
    }
}
