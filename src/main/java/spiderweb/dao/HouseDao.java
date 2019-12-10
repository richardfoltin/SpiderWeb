/*
 * ----------------------SpiderWeb----------------------
 * | Leírás:   Adatbázis alkalmazás Lord Varys számára |
 * | Tantárgy: ELTE - Programozási Technológia 2.      |
 * | Szerző:   Foltin Csaba Richárd (I37M02)           |
 * -----------------------------------------------------
 */
package spiderweb.dao;

import spiderweb.entity.House;
import spiderweb.jdbcdao.dbexception.SpiderReadException;

/**
 * Házak betöltéséért és mentéséért felelős interface
 * 
 * @author Foltin Csaba Richárd
 */
public interface HouseDao extends CRUDDao<House, Integer> {
   
    /**
     * Keresés ház neve alapján
     * 
     * @param name
     * @return 
     * @throws spiderweb.jdbcdao.dbexception.SpiderReadException 
     */
    public House findOneByName(String name) throws SpiderReadException;
    
}
