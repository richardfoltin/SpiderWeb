/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spiderweb.view.button;

import java.awt.Dimension;
import javax.swing.JPanel;
import spiderweb.view.constant.SpiderColor;
import spiderweb.view.constant.SpiderFont;

/**
 *
 * @author pokemonterkep
 */
public class SpiderSmallButton extends SpiderButton {
    
    public SpiderSmallButton(String title) {
        super(
            title,
            new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT),
            SpiderColor.GREY,
            SpiderFont.BUTTONSMALL
        );
    }
}
