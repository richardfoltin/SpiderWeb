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

import spiderweb.dao.CharacterDao;
import spiderweb.entity.Character;
import spiderweb.entity.House;
import spiderweb.jdbcdao.dbexception.SpiderReadException;
import spiderweb.jdbcdao.dbexception.SpiderWriteException;

/**
 * JDBC adatábiziskapcsolatot használó, karakterek mentését és betöltését vezérlő osztály
 * 
 * @author Foltin Csaba Richárd
 */
public class JDBCCharacterDao extends JDBCSuperDao<Character> implements CharacterDao{

    public JDBCCharacterDao() {
        super("character", "character_id");
    }
    
    /**
     * Karakter törlése
     * 
     * @param key
     * @throws SpiderWriteException 
     */
    @Override
    public void delete(Integer key) throws SpiderWriteException {
        super.delete(key);
    }

    /**
     * Minden karakter listázása
     * 
     * @return
     * @throws SpiderReadException 
     */
    @Override
    public List<Character> findAll() throws SpiderReadException {
        String sql = "SELECT * FROM \"ROOT\".\"character\" c " +
                     "LEFT JOIN \"ROOT\".\"house\" h ON c.house_id = h.house_id";
        return super.findAll(sql, (ResultSet r) -> setCharacter(r));
    }
    
    /**
     * Egy házhoz tartozó minden karakter listázásá
     * 
     * @param house
     * @return
     * @throws SpiderReadException 
     */
    @Override
    public List<Character> findAll(House house) throws SpiderReadException {
        String sql = "SELECT character_id, character_name, army_size, status, c.house_id, house_name, house_crest FROM \"ROOT\".\"character\" c " +
                     "LEFT JOIN \"ROOT\".\"house\" h ON c.house_id = h.house_id " +
                     "WHERE c.house_id = " + house.getId();
        return super.findAll(sql, (ResultSet r) -> setCharacter(r));
    }

    /**
     * Egy karakter megkeresése kulcs alapján
     * 
     * @param key
     * @return
     * @throws SpiderReadException 
     */
    @Override
    public Character findOneById(Integer key) throws SpiderReadException {
        String sql = "SELECT * FROM \"ROOT\".\"character\" c " +
                     "LEFT JOIN \"ROOT\".\"house\" h ON c.house_id = h.house_id " +
                     "WHERE character_id = ?";
        return super.findOne(sql, (ResultSet r) -> setCharacter(r), key);
    }

    /**
     * Egy karakter megkeresése neve alapján
     * 
     * @param name
     * @return
     * @throws SpiderReadException 
     */
    @Override
    public Character findOneByName(String name) throws SpiderReadException {
        String sql = "SELECT * FROM \"ROOT\".\"character\" c " +
                "LEFT JOIN \"ROOT\".\"house\" h ON c.house_id = h.house_id " +
                "WHERE character_name = ?";
        return super.findOne(sql, (ResultSet r) -> setCharacter(r), name);
    }
    
    /**
     * Egy karakter elmentése az adatbázisba
     * 
     * @param character
     * @return
     * @throws SpiderWriteException 
     */
    @Override
    public Character save(Character character) throws SpiderWriteException {
        String[] columns = new String[] {"character_name", "army_size", "status", "house_id"};
        Object[] values = new Object[] {
            character.getName(), 
            character.getArmySize(), 
            character.isLiving(), 
            (character.hasHouse()) ? character.getHouse().getId() : null};
        
        int id = super.save(columns, values);
        character.setId(id);
        return character;
    }

    /**
     * Az adatbázis érétkeinek frissítése a karakter entitás alapján
     * 
     * @param character
     * @throws SpiderWriteException 
     */
    @Override
    public void update(Character character) throws SpiderWriteException {
        String[] columns = new String[] {"character_name", "army_size", "status", "house_id"};
        Object[] values = new Object[] {
            character.getName(), 
            character.getArmySize(), 
            character.isLiving(), 
            (character.hasHouse()) ? character.getHouse().getId() : null};
        
        super.update(character.getId(), columns, values);
    }

    /**
     * Adatbázis adataiból egy karakter entitás létrehozoása
     * 
     * @param resultSet
     * @return
     * @throws SQLException 
     */
    private Character setCharacter(ResultSet resultSet) throws SQLException{
        String houseName = resultSet.getString("house_name");
        House house = (houseName == null) ? null : new House(houseName, resultSet.getInt("house_id"), resultSet.getBytes("house_crest"));
        
        Character character = new Character(
            resultSet.getString("character_name"),
            resultSet.getInt("army_size"),
            resultSet.getBoolean("status"),
            house);
        
        character.setId(resultSet.getInt("character_id"));

        return character;
    }
}
