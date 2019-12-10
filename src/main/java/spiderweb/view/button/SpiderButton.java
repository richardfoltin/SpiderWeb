/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spiderweb.view.button;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import spiderweb.view.constant.SpiderColor;

/**
 *
 * @author pokemonterkep
 */

public abstract class SpiderButton extends JButton{
    
    public static final int BIGBUTTON_HEIGHT = 44;
    public static final int BIGBUTTON_WIDTH = 180;
    public static final int BUTTON_HEIGHT = 30;
    public static final int BUTTON_WIDTH = 140;
    
    public SpiderButton(String title, Dimension size, Color bgColor, Font font) {
        super(title.toUpperCase());
        setPreferredSize(size);
        setMaximumSize(size);
        setAlignmentX(Component.CENTER_ALIGNMENT);  
        setBackground(bgColor);
        setForeground(SpiderColor.BLACK);
        setFocusPainted(false);
        //setOpaque(false);
        //setContentAreaFilled(false);
        //setBorderPainted(false);

        setFont(font);
    }


}
