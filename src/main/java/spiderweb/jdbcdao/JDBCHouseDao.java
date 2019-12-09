/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spiderweb.jdbcdao;

/**
 *
 * @author eandgna
 */

import java.io.IOException;
import java.util.*;
import java.sql.*;
import javax.sql.rowset.serial.SerialBlob;
import spiderweb.dao.HouseDao;
import spiderweb.entity.House;
import spiderweb.jdbcdao.dbexception.*;

public class JDBCHouseDao extends JDBCSuperDao<House> implements HouseDao{

    public JDBCHouseDao() {
        super("house", "house_id");
    }
    
    @Override
    public void delete(Integer key) throws SpiderWriteException {
        super.delete(key);
    }

    @Override
    public List<House> findAll() throws SpiderReadException {
        return super.findAll((ResultSet r) -> setHouse(r));
    }

    @Override
    public House findOneById(Integer key) throws SpiderReadException {
        return super.findOneById(key, (ResultSet r) -> setHouse(r));
    }

    @Override
    public House save(House house) throws SpiderImageException, SpiderWriteException {
        try {
            String[] columns = new String[] {"house_name", "motto", "house_crest"};
            Object[] values = new Object[] {house.getName(), house.getMotto(), bytesToBlob(house.getCrestBytes())};

            return super.save(house, columns, values);
        } catch (IOException ex) {
            throw (SpiderImageException)ex;
        }
    }

    @Override
    public void update(House house) throws SpiderImageException, SpiderWriteException{
        try {
            String[] columns = new String[] {"house_name", "motto", "house_crest"};
            Object[] values = new Object[] {house.getName(), house.getMotto(), bytesToBlob(house.getCrestBytes())};
            super.update(house, columns, values);
        } catch (IOException ex) {
            throw (SpiderImageException)ex;
        }
    } 

    @Override
    public House findOneByName(String name) throws SpiderReadException {
       return super.findOneByColumn("house_name", name, (ResultSet r) -> setHouse(r));
    }
    
    private House setHouse(ResultSet resultSet) throws SQLException {
        House house = new House(
            resultSet.getString("house_name"),
            resultSet.getString("motto"),
            resultSet.getBytes("house_crest"));
        
        house.setId(resultSet.getInt("house_id"));
        return house;
    }

    private static Blob bytesToBlob(byte[] bytes) throws SpiderImageException {
        try {
           return new SerialBlob(bytes); 
        } catch (SQLException ex) {
            throw new SpiderImageException("Can't create Blob.");
        }
    }

}
