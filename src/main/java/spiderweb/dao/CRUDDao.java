/*
 * ----------------------SpiderWeb----------------------
 * | Leírás:   Adatbázis alkalmazás Lord Varys számára |
 * | Tantárgy: ELTE - Programozási Technológia 2.      |
 * | Szerző:   Foltin Csaba Richárd (I37M02)           |
 * -----------------------------------------------------
 */
package spiderweb.dao;

import java.util.List;
import spiderweb.jdbcdao.dbexception.*;

/**
 * Adat metéséért és betöltéséért felelős absztrakt interface
 * 
 * @author Foltin Csaba Richárd
 * @param <E> a visszadott entitások osztálya
 * @param <K> a entitások kulcsának osztálya
 */
public abstract interface CRUDDao<E, K> {

    /**
     * Egy kulcsértékkel megadott entitás törlése
     *
     * @param key
     * @throws spiderweb.jdbcdao.dbexception.SpiderWriteException
     */
    void delete(K key) throws SpiderWriteException;

    /**
     * Egy entitás plédányainak visszaadása a megadott osztály szerint
     *
     * @return
     * @throws spiderweb.jdbcdao.dbexception.SpiderReadException
     */
    List<E> findAll() throws SpiderReadException;

    /**
     * Egy entitás megkeresése kulcs alapján
     *
     * @param key
     * @return
     * @throws spiderweb.jdbcdao.dbexception.SpiderReadException
     */
    E findOneById(K key) throws SpiderReadException;

    /**
     * Egy új entitás elmentése
     *
     * @param entity
     * @throws spiderweb.jdbcdao.dbexception.SpiderImageException
     * @throws spiderweb.jdbcdao.dbexception.SpiderWriteException
     */
    E save(E entity) throws SpiderImageException, SpiderWriteException;

    /**
     * Egy entitás módosítása
     *
     * @param entity
     * @throws spiderweb.jdbcdao.dbexception.SpiderImageException
     * @throws spiderweb.jdbcdao.dbexception.SpiderWriteException
     */
    void update(E entity) throws SpiderImageException, SpiderWriteException;
}
