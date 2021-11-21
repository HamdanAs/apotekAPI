package com.hamdanas.utilities;

import java.awt.Component;
import java.awt.event.KeyEvent;

/**
 *
 * @author NESAS
 */

public class Input{
    public static final int ENTER = KeyEvent.VK_ENTER;
    public static final int DOWN = KeyEvent.VK_DOWN;
    public static final int UP = KeyEvent.VK_UP;
    public static final int LEFT = KeyEvent.VK_LEFT;
    public static final int RIGHT = KeyEvent.VK_RIGHT;
    
    public static final int[] DOWN_KEY = new int[]{DOWN, ENTER};
    
    public static void moveCursor(KeyEvent e, Component c, int key){
        if(e.getKeyCode() == key){
            c.requestFocus();
        }
    }
    
    public static void moveCursor(KeyEvent e, Component c, int[] keys){
        for(int key : keys){
            if(e.getKeyCode() == key){
                c.requestFocus();
            }
        }
    }
    
    public static void moveCursor(KeyEvent e, Component c, int[] keys, Action a){
        for(int key : keys){
            if(e.getKeyCode() == key){
                a.method();
                c.requestFocus();
            }
        }
    }
    
    public static void moveCursor(KeyEvent e, Component c, int key, Action a){
        if(e.getKeyCode() == key){
            a.method();
            c.requestFocus();
        }
    }
    
    public static void executeAction(KeyEvent e, int key, Action a){
        if(e.getKeyCode() == key){
            a.method();
        }
    }
    
    public static void executeButtonClick(KeyEvent e, Component button, int key, Action a){
        if(button.getBackground() == Colors.HOVER_COLOR){
            executeAction(e, key, a);
        }
    }
}
