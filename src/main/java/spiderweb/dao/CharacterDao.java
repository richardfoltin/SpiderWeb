/*
 * ----------------------SpiderWeb----------------------
 * | Leírás:   Adatbázis alkalmazás Lord Varys számára |
 * | Tantárgy: ELTE - Programozási Technológia 2.      |
 * | Szerző:   Foltin Csaba Richárd (I37M02)           |
 * -----------------------------------------------------
 */
package spiderweb.dao;

import java.util.List;
import spiderweb.entity.Character;
import spiderweb.entity.House;
import spiderweb.jdbcdao.dbexception.SpiderReadException;

/**
 * Karakterek betöltéséért és mentéséért felelős interface
 * 
 * @author Foltin Csaba Richárd
 */
public interface CharacterDao extends CRUDDao<Character, Integer> {
   
    /**
     * Keresés karakter neve alapján
     * 
     * @param name
     * @return 
     * @throws spiderweb.jdbcdao.dbexception.SpiderReadException 
     */
    public Character findOneByName(String name) throws SpiderReadException;
    
    /**
     * Az adott házhoz tartozó karakterek példányainak visszaadása
     * 
     * @param house
     * @return
     * @throws SpiderReadException 
     */
    public List<Character> findAll(House house) throws SpiderReadException;
}