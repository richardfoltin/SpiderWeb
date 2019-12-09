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

import java.util.*;
import java.sql.*;
import java.time.LocalDate;
import spiderweb.dao.AllianceDao;
import spiderweb.entity.Alliance;
import spiderweb.entity.House;
import spiderweb.jdbcdao.dbexception.SpiderReadException;
import spiderweb.jdbcdao.dbexception.SpiderWriteException;

public class JDBCAllianceDao extends JDBCSuperDao<Alliance> implements AllianceDao{

    public JDBCAllianceDao() {
        super("alliance", "alliance_id");
    }
    
    @Override
    public void delete(Integer key) throws SpiderWriteException {
        super.delete(key);
    }

    @Override
    public List<Alliance> findAll() throws SpiderReadException {
        String sql = "SELECT alliance_id, start_date, end_date, h1.house_name house1, h1.house_id house1_id, h2.house_name as house2, h2.house_id as house2_id " +
                     "FROM \"ROOT\".\"alliance\" a " +
                     "LEFT JOIN \"ROOT\".\"house\" h1 ON a.house1 = h1.house_id " +                     
                     "LEFT JOIN \"ROOT\".\"house\" h2 ON a.house2 = h2.house_id ";
        return super.findAll(sql, (ResultSet r) -> setAlliance(r));
    }
    
    @Override
    public List<Alliance> findOpenWith(House house) throws SpiderReadException {
        String sql = "SELECT alliance_id, start_date, end_date, h1.house_name as house1, h1.house_id as house1_id, h2.house_name as house2, h2.house_id as house2_id " +
                     "FROM \"ROOT\".\"alliance\" a " +
                     "LEFT JOIN \"ROOT\".\"house\" h1 ON a.house1 = h1.house_id " +                     
                     "LEFT JOIN \"ROOT\".\"house\" h2 ON a.house2 = h2.house_id " +
                     "WHERE end_date IS NULL AND (house1 = " + house.getId() + " OR house2 = " + house.getId() + ")";
        return super.findAll(sql, (ResultSet r) -> setAlliance(r, house));
    }

    @Override
    public Alliance findOneById(Integer key) throws SpiderReadException {
        String sql = "SELECT alliance_id, start_date, end_date, h1.house_name as house1, h1.house_id as house1_id, h2.house_name as house2, h2.house_id as house2_id " +
                     "FROM \"ROOT\".\"alliance\" a " +
                     "LEFT JOIN \"ROOT\".\"house\" h1 ON a.house1 = h1.house_id " +                     
                     "LEFT JOIN \"ROOT\".\"house\" h2 ON a.house2 = h2.house_id " +
                     "WHERE alliance_id = ?";
        return super.findOne(sql, (ResultSet r) -> setAlliance(r), key);
    }

    @Override
    public Alliance save(Alliance alliance) throws SpiderWriteException {
        ArrayList<String> columns = new ArrayList<String>(Arrays.asList("house1", "house2", "start_date"));
        ArrayList<Object> values = new ArrayList<>(Arrays.asList(
            alliance.getHouse1().getId(),
            alliance.getHouse2().getId(),
            alliance.getStartDate()));
        
        if (alliance.isClosed()) {
            columns.add("end_date");
            values.add(alliance.getEndDate());
        }
        
        return super.save(alliance, columns.toArray(new String[columns.size()]), values.toArray());
    }

    @Override
    public void update(Alliance alliance) {
        throw new UnsupportedOperationException();
    }
    
    private Alliance setAlliance(ResultSet resultSet) throws SQLException {
        House house1 = new House(
                resultSet.getString("house1"), 
                resultSet.getInt("house1_id"));
        
        House house2 = new House(
                resultSet.getString("house2"), 
                resultSet.getInt("house2_id"));
        
        LocalDate startDate = resultSet.getDate("start_date").toLocalDate();
        java.sql.Date sqlEndDate = resultSet.getDate("end_date");
        LocalDate endDate = (sqlEndDate == null) ? null : sqlEndDate.toLocalDate();
        
        return new Alliance(house1, house2, startDate, endDate);
    }
    
    private Alliance setAlliance(ResultSet resultSet, House baseHouse) throws SQLException {
        
        House house1 = new House(
                resultSet.getString("house1"), 
                resultSet.getInt("house1_id"));
        
        House house2 = new House(
                resultSet.getString("house2"), 
                resultSet.getInt("house2_id"));
        
        if (house1.getId() != baseHouse.getId()) {
            house2 = house1;
            house1 = baseHouse;
        }
        
        return new Alliance(house1, house2);
    }
        

    @Override
    public void closeAlliance(Alliance alliance, LocalDate endDate) throws SpiderWriteException {
        String[] columns = new String[] {"end_date"};
        String[] values = new String[] {endDate.toString()};
        
        super.update(alliance, columns, values);
    }


}
