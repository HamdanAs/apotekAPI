package com.hamdanas.utilities;

import java.awt.Color;
import java.awt.Component;

/**
 *
 * @author NESAS
 */
public class Colors {
    public static final Color MAIN_COLOR = new Color(254,241,230);
    public static final Color SECONDARY_COLOR = new Color(249,213,167);
    public static final Color HOVER_COLOR = new Color(255,195,133);
    public static final Color WHITE_COLOR = new Color(212,236,221);
    public static final Color BUTTON_COLOR = new Color(255,176,133);
    
    public static void changeBg(Component cp, Color c){
        cp.setBackground(c);
    }
}
