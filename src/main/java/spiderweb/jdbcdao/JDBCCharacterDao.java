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
import spiderweb.dao.CharacterDao;
import spiderweb.entity.Character;
import spiderweb.entity.House;
import spiderweb.jdbcdao.dbexception.SpiderReadException;
import spiderweb.jdbcdao.dbexception.SpiderWriteException;

public class JDBCCharacterDao extends JDBCSuperDao<Character> implements CharacterDao{

    public JDBCCharacterDao() {
        super("character", "character_id");
    }
    
    @Override
    public void delete(Integer key) throws SpiderWriteException {
        super.delete(key);
    }

    @Override
    public List<Character> findAll() throws SpiderReadException {
        String sql = "SELECT * FROM \"ROOT\".\"character\" c " +
                     "LEFT JOIN \"ROOT\".\"house\" h ON c.house_id = h.house_id";
        return super.findAll(sql, (ResultSet r) -> setCharacter(r));
    }
    
    @Override
    public List<Character> findAll(House house) throws SpiderReadException {
        String sql = "SELECT character_id, character_name, army_size, status, c.house_id, house_name, house_crest FROM \"ROOT\".\"character\" c " +
                     "LEFT JOIN \"ROOT\".\"house\" h ON c.house_id = h.house_id " +
                     "WHERE c.house_id = " + house.getId();
        return super.findAll(sql, (ResultSet r) -> setCharacter(r));
    }

    @Override
    public Character findOneById(Integer key) throws SpiderReadException {
        String sql = "SELECT * FROM \"ROOT\".\"character\" c " +
                     "LEFT JOIN \"ROOT\".\"house\" h ON c.house_id = h.house_id " +
                     "WHERE character_id = ?";
        return super.findOne(sql, (ResultSet r) -> setCharacter(r), key);
    }

    @Override
    public Character save(Character character) throws SpiderWriteException {
        String[] columns = new String[] {"character_name", "army_size", "status", "house_id"};
        Object[] values = new Object[] {
            character.getName(), 
            character.getArmySize(), 
            character.isLiving(), 
            (character.hasHouse()) ? character.getHouse().getId() : null};
        
        return super.save(character, columns, values);
    }

    @Override
    public void update(Character character) throws SpiderWriteException {
        String[] columns = new String[] {"character_name", "army_size", "status", "house_id"};
        Object[] values = new Object[] {
            character.getName(), 
            character.getArmySize(), 
            character.isLiving(), 
            (character.hasHouse()) ? character.getHouse().getId() : null};
        
        super.update(character, columns, values);
    }

    @Override
    public Character findOneByName(String name) throws SpiderReadException {
        String sql = "SELECT * FROM \"ROOT\".\"character\" c " +
                "LEFT JOIN \"ROOT\".\"house\" h ON c.house_id = h.house_id " +
                "WHERE character_name = ?";
        return super.findOne(sql, (ResultSet r) -> setCharacter(r), name);
    }
    
    private Character setCharacter(ResultSet resultSet) throws SQLException{
        String houseName = resultSet.getString("house_name");
        House house = (houseName == null) ? null : new House(houseName, resultSet.getInt("house_id"), resultSet.getBytes("house_crest"));
        
        Character.Status status = Character.statusFromBoolean(resultSet.getBoolean("status"));
        Character character = new Character(
            resultSet.getString("character_name"),
            resultSet.getInt("army_size"),
            status,
            house);
        
        character.setId(resultSet.getInt("character_id"));

        return character;
    }


}
