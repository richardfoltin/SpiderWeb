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
 * Program ablakában használt nagy gomb
 * 
 * @author Foltin Csaba Richárd
 */
public class SpiderBigButton extends SpiderButton {

    public SpiderBigButton(String title) {
        super(
            title,
            new Dimension(BIGBUTTON_WIDTH, BIGBUTTON_HEIGHT),
            SpiderColor.HIGHTLIGHT_LOW,
            SpiderFont.BUTTONBIG
        );
    }
}
