/*
 * ----------------------SpiderWeb----------------------
 * | Leírás:   Adatbázis alkalmazás Lord Varys számára |
 * | Tantárgy: ELTE - Programozási Technológia 2.      |
 * | Szerző:   Foltin Csaba Richárd (I37M02)           |
 * -----------------------------------------------------
 */
package spiderweb.jdbcdao;

import java.sql.SQLException;

/**
 * Adatbázis adatokból entitást létrehozó függvény interface-e
 * 
 * @author Foltin Csaba Richárd
 * @param <R> az adatbázisből visszakapott érétkkészlet osztálya 
 * @param <E> az entitás
 */
@FunctionalInterface
public interface JDBCEntityCreator<R, E> {
    E apply(R result) throws SQLException;    
}
