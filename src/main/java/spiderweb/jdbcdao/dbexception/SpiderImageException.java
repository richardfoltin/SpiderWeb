/*
 * ----------------------SpiderWeb----------------------
 * | Leírás:   Adatbázis alkalmazás Lord Varys számára |
 * | Tantárgy: ELTE - Programozási Technológia 2.      |
 * | Szerző:   Foltin Csaba Richárd (I37M02)           |
 * -----------------------------------------------------
 */
package spiderweb.jdbcdao.dbexception;

import java.io.IOException;

/**
 * Kép betöltésével kapcsolatos kivétel
 * 
 * @author Foltin Csaba Richárd
 */
public class SpiderImageException extends IOException {

    public SpiderImageException(String msg) {
        super(msg);
    }
}
