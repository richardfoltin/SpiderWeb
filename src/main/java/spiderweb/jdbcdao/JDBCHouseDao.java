/*
 * ----------------------SpiderWeb----------------------
 * | Leírás:   Adatbázis alkalmazás Lord Varys számára |
 * | Tantárgy: ELTE - Programozási Technológia 2.      |
 * | Szerző:   Foltin Csaba Richárd (I37M02)           |
 * -----------------------------------------------------
 */
package spiderweb.jdbcdao;

import java.io.IOException;
import java.util.*;
import java.sql.*;

import spiderweb.dao.HouseDao;
import spiderweb.entity.House;
import spiderweb.jdbcdao.dbexception.*;

/**
 * JDBC adatábiziskapcsolatot használó, házak mentését és betöltését vezérlő osztály
 * 
 * @author Foltin Csaba Richárd
 */
public class JDBCHouseDao extends JDBCSuperDao<House> implements HouseDao{

    public JDBCHouseDao() {
        super("house", "house_id");
    }
    
    /**
     * Ház törlése
     * 
     * @param key
     * @throws SpiderWriteException 
     */
    @Override
    public void delete(Integer key) throws SpiderWriteException {
        super.delete(key);
    }

    /**
     * Minden ház listázása
     * 
     * @return
     * @throws SpiderReadException 
     */
    @Override
    public List<House> findAll() throws SpiderReadException {
        return super.findAll((ResultSet r) -> setHouse(r));
    }

    /**
     * Egy ház megkeresése kulcs alapján
     * 
     * @param key
     * @return
     * @throws SpiderReadException 
     */
    @Override
    public House findOneById(Integer key) throws SpiderReadException {
        return super.findOneById(key, (ResultSet r) -> setHouse(r));
    }

    /**
     * Egy ház megkeresése neve alapján
     * 
     * @param name
     * @return
     * @throws SpiderReadException 
     */
    @Override
    public House findOneByName(String name) throws SpiderReadException {
       return super.findOneByColumn("house_name", name, (ResultSet r) -> setHouse(r));
    }
    
    /**
     * Egy ház elmentése az adatbázisba
     * 
     * @param house
     * @return
     * @throws SpiderImageException
     * @throws SpiderWriteException 
     */
    @Override
    public House save(House house) throws SpiderImageException, SpiderWriteException {
        try {
            String[] columns = new String[] {"house_name", "motto", "house_crest"};
            Object[] values = new Object[] {house.getName(), house.getMotto(), bytesToBlob(house.getCrestBytes())};

            int id = super.save(columns, values);
            house.setId(id);
            return house;
        } catch (IOException ex) {
            throw (SpiderImageException)ex;
        }
    }

    /**
     * Az adatbázis érétkeinek frissítése a ház entitás alapján
     * 
     * @param house
     * @throws SpiderImageException
     * @throws SpiderWriteException 
     */
    @Override
    public void update(House house) throws SpiderImageException, SpiderWriteException{
        try {
            String[] columns = new String[] {"house_name", "motto", "house_crest"};
            Object[] values = new Object[] {house.getName(), house.getMotto(), bytesToBlob(house.getCrestBytes())};
            super.update(house.getId(), columns, values);
        } catch (IOException ex) {
            throw (SpiderImageException)ex;
        }
    } 

    /**
     * Adatbázis adataiból egy ház entitás létrehozoása
     * 
     * @param resultSet
     * @return
     * @throws SQLException 
     */
    private House setHouse(ResultSet resultSet) throws SQLException {
        House house = new House(
            resultSet.getString("house_name"),
            resultSet.getString("motto"),
            resultSet.getBytes("house_crest"));
        
        house.setId(resultSet.getInt("house_id"));
        return house;
    }
}
