/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spiderweb.dao;

import java.util.List;
import spiderweb.jdbcdao.dbexception.*;

public interface CRUDDao<E, K> {

    /**
     * Egy kulcsértékkel megadott entitás törlése
     *
     * @param key
     */
    void delete(K key) throws SpiderWriteException;

    /**
     * Egy entitás plédányainak visszaadása a megadott osztály szerint
     *
     * @return
     */
    List<E> findAll() throws SpiderReadException;

    /**
     * Egy entitás megkeresése kulcs alapján
     *
     * @param key
     * @return
     */
    E findOneById(K key) throws SpiderReadException;

    /**
     * Egy új entitás elmentése
     *
     * @param entity
     */
    E save(E entity) throws SpiderImageException, SpiderWriteException;

    /**
     * Egy entitás módosítása
     *
     * @param entity
     */
    void update(E entity) throws SpiderImageException, SpiderWriteException;
}
