/*
 * ----------------------SpiderWeb----------------------
 * | Leírás:   Adatbázis alkalmazás Lord Varys számára |
 * | Tantárgy: ELTE - Programozási Technológia 2.      |
 * | Szerző:   Foltin Csaba Richárd (I37M02)           |
 * -----------------------------------------------------
 */
package spiderweb.dao;

import java.time.LocalDate;
import java.util.List;
import spiderweb.entity.Alliance;
import spiderweb.entity.House;
import spiderweb.jdbcdao.dbexception.SpiderReadException;
import spiderweb.jdbcdao.dbexception.SpiderWriteException;

/**
 * Szövetéségek betöltéséért és mentéséért felelős interface
 * 
 * @author Foltin Csaba Richárd
 */
public interface AllianceDao extends CRUDDao<Alliance, Integer> {
    
    /**
     * Szövetség lezárása egy végdátummal
     * 
     * @param alliance
     * @param endDate
     * @throws SpiderWriteException 
     */
    public void closeAlliance(Alliance alliance, LocalDate endDate) throws SpiderWriteException;
    
    /**
     * Az adott házhoz tartozó szövetségek példányainak visszaadása
     * 
     * @param house
     * @return
     * @throws SpiderReadException 
     */
    public List<Alliance> findOpenWith(House house) throws SpiderReadException;
}