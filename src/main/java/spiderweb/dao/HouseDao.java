/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spiderweb.dao;

import java.awt.Image;
import spiderweb.entity.House;
import spiderweb.jdbcdao.dbexception.SpiderReadException;

/**
 *
 * @author eandgna
 */
public interface HouseDao extends CRUDDao<House, Integer> {
   
    /**
     * Keresés cím alapján
     * 
     * @param name
     * @return 
     */
    public House findOneByName(String name) throws SpiderReadException;
    
}
