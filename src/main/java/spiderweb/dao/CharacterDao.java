/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spiderweb.dao;

import java.util.List;
import spiderweb.entity.Character;
import spiderweb.entity.House;
import spiderweb.jdbcdao.dbexception.SpiderReadException;

/**
 *
 * @author eandgna
 */
public interface CharacterDao extends CRUDDao<Character, Integer> {
   
    /**
     * Keresés cím alapján
     * 
     * @param name
     * @return 
     */
    public Character findOneByName(String name) throws SpiderReadException;
    
    public List<Character> findAll(House house) throws SpiderReadException;
}