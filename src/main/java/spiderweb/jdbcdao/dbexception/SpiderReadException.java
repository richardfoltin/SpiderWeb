/*
 * ----------------------SpiderWeb----------------------
 * | Leírás:   Adatbázis alkalmazás Lord Varys számára |
 * | Tantárgy: ELTE - Programozási Technológia 2.      |
 * | Szerző:   Foltin Csaba Richárd (I37M02)           |
 * -----------------------------------------------------
 */
package spiderweb.jdbcdao.dbexception;

import java.sql.SQLException;

/**
 * Adatbázis olvasással kapcsolatos kivétel
 * 
 * @author Foltin Csaba Richárd
 */
public class SpiderReadException extends SQLException {
    
    public SpiderReadException(String msg) {
        super(msg);
    }
}
