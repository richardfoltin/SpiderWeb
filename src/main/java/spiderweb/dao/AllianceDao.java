/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spiderweb.dao;

import java.time.LocalDate;
import java.util.List;
import spiderweb.entity.Alliance;
import spiderweb.entity.House;
import spiderweb.jdbcdao.dbexception.SpiderReadException;
import spiderweb.jdbcdao.dbexception.SpiderWriteException;

/**
 *
 * @author eandgna
 */
public interface AllianceDao extends CRUDDao<Alliance, Integer> {
    
    public void closeAlliance(Alliance alliance, LocalDate endDate) throws SpiderWriteException;
    
    public List<Alliance> findOpenWith(House house) throws SpiderReadException;
}