/*
 * ----------------------SpiderWeb----------------------
 * | Leírás:   Adatbázis alkalmazás Lord Varys számára |
 * | Tantárgy: ELTE - Programozási Technológia 2.      |
 * | Szerző:   Foltin Csaba Richárd (I37M02)           |
 * -----------------------------------------------------
 */
package spiderweb.jdbcdao;

import java.util.*;
import java.sql.*;
import java.time.LocalDate;

import spiderweb.dao.AllianceDao;
import spiderweb.entity.Alliance;
import spiderweb.entity.House;
import spiderweb.jdbcdao.dbexception.SpiderReadException;
import spiderweb.jdbcdao.dbexception.SpiderWriteException;

/**
 * JDBC adatábiziskapcsolatot használó, szövetségek mentését és betöltését vezérlő osztály
 * 
 * @author Foltin Csaba Richárd
 */
public class JDBCAllianceDao extends JDBCSuperDao<Alliance> implements AllianceDao{

    public JDBCAllianceDao() {
        super("alliance", "alliance_id");
    }
    
    /**
     * Szövetség törlése
     * 
     * @param key
     * @throws SpiderWriteException 
     */
    @Override
    public void delete(Integer key) throws SpiderWriteException {
        super.delete(key);
    }

    /**
     * Minden szövetség listázása
     * 
     * @return
     * @throws SpiderReadException 
     */
    @Override
    public List<Alliance> findAll() throws SpiderReadException {
        String sql = "SELECT alliance_id, start_date, end_date, h1.house_name house1, h1.house_id house1_id, h2.house_name as house2, h2.house_id as house2_id " +
                     "FROM \"ROOT\".\"alliance\" a " +
                     "LEFT JOIN \"ROOT\".\"house\" h1 ON a.house1 = h1.house_id " +                     
                     "LEFT JOIN \"ROOT\".\"house\" h2 ON a.house2 = h2.house_id ";
        return super.findAll(sql, (ResultSet r) -> setAlliance(r));
    }
    
    /**
     * A megadott házhoz tartozó minden szövetség listázása
     * 
     * @param house
     * @return
     * @throws SpiderReadException 
     */
    @Override
    public List<Alliance> findOpenWith(House house) throws SpiderReadException {
        String sql = "SELECT alliance_id, start_date, end_date, h1.house_name as house1, h1.house_id as house1_id, h2.house_name as house2, h2.house_id as house2_id " +
                     "FROM \"ROOT\".\"alliance\" a " +
                     "LEFT JOIN \"ROOT\".\"house\" h1 ON a.house1 = h1.house_id " +                     
                     "LEFT JOIN \"ROOT\".\"house\" h2 ON a.house2 = h2.house_id " +
                     "WHERE end_date IS NULL AND (house1 = " + house.getId() + " OR house2 = " + house.getId() + ")";
        return super.findAll(sql, (ResultSet r) -> setAllianceWithFirstHouse(r, house));
    }

    /**
     * Egy szövetség megkeresése kulcs alapján
     * 
     * @param key
     * @return
     * @throws SpiderReadException 
     */
    @Override
    public Alliance findOneById(Integer key) throws SpiderReadException {
        String sql = "SELECT alliance_id, start_date, end_date, h1.house_name as house1, h1.house_id as house1_id, h2.house_name as house2, h2.house_id as house2_id " +
                     "FROM \"ROOT\".\"alliance\" a " +
                     "LEFT JOIN \"ROOT\".\"house\" h1 ON a.house1 = h1.house_id " +                     
                     "LEFT JOIN \"ROOT\".\"house\" h2 ON a.house2 = h2.house_id " +
                     "WHERE alliance_id = ?";
        return super.findOne(sql, (ResultSet r) -> setAlliance(r), key);
    }

    /**
     * Egy szövetség elmentése az adatbázisba
     * 
     * @param alliance
     * @return
     * @throws SpiderWriteException 
     */
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
        
        int id = super.save(columns.toArray(new String[columns.size()]), values.toArray());
        alliance.setId(id);
        
        return alliance;
    }

    /**
     * Az adatbázis érétkeinek frissítése a szövetség entitás alapján
     * 
     * @param alliance 
     */
    @Override
    public void update(Alliance alliance) {
        throw new UnsupportedOperationException();
    }
    
    /**
     * Egy szövetség lezárása a megadott dátummal
     * 
     * @param alliance
     * @param endDate
     * @throws SpiderWriteException 
     */
    @Override
    public void closeAlliance(Alliance alliance, LocalDate endDate) throws SpiderWriteException {
        String[] columns = new String[] {"end_date"};
        String[] values = new String[] {endDate.toString()};
        
        super.update(alliance.getId(), columns, values);
    }
    
    /**
     * Adatbázis adataiból egy szövetség entitás létrehozoása
     * 
     * @param resultSet
     * @return
     * @throws SQLException 
     */
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
        
        Alliance alliance = new Alliance(house1, house2, startDate, endDate);
        alliance.setId(resultSet.getInt("alliance_id"));
        return alliance;
    }
    
    /**
     * Adatbázis adataiból egy karakter entitás létrehozoása és a megadott ház beállítása elsőnek
     * 
     * @param resultSet
     * @param firstHouse
     * @return
     * @throws SQLException 
     */
    private Alliance setAllianceWithFirstHouse(ResultSet resultSet, House firstHouse) throws SQLException {
        Alliance alliance = setAlliance(resultSet);
        alliance.setFirstHouse(firstHouse);
        return alliance;
    }
}
