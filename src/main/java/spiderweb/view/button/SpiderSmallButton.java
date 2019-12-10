/*
 * ----------------------SpiderWeb----------------------
 * | Leírás:   Adatbázis alkalmazás Lord Varys számára |
 * | Tantárgy: ELTE - Programozási Technológia 2.      |
 * | Szerző:   Foltin Csaba Richárd (I37M02)           |
 * -----------------------------------------------------
 */
package spiderweb.view.button;

import java.awt.Dimension;

import spiderweb.view.constant.SpiderColor;
import spiderweb.view.constant.SpiderFont;

/**
 * Program ablakában használt kis gomb
 * 
 * @author Foltin Csaba Richárd
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
