/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spiderweb.view.button;

import java.awt.Dimension;
import spiderweb.view.constant.SpiderColor;
import spiderweb.view.constant.SpiderFont;

/**
 *
 * @author pokemonterkep
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
