/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spiderweb.model;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import javax.swing.ImageIcon;

import spiderweb.dao.*;
import spiderweb.jdbcdao.*;
import spiderweb.entity.Alliance;
import spiderweb.entity.Character;
import spiderweb.entity.House;
import spiderweb.jdbcdao.dbexception.*;
import spiderweb.Resource;

/**
 *
 * @author eandgna
 */
public class SpiderWebModel {
    
    private HouseDao houseDao;    
    private CharacterDao characterDao;
    private AllianceDao allianceDao;
     
    public SpiderWebModel() {
        this.houseDao = new JDBCHouseDao();
        this.characterDao = new JDBCCharacterDao();
        this.allianceDao = new JDBCAllianceDao();
    }
    
    public House addNewHouse(String name, String motto, URL url) throws SpiderImageException, SpiderWriteException {
        return addNewHouse(new House(name, motto, Resource.getBytes(url)));
    }
    
    public House addNewHouse(String name, String motto, String crestFileNameInResource) throws SpiderImageException, SpiderWriteException {
        return addNewHouse(new House(name, motto, Resource.getBytesFromResource(crestFileNameInResource)));
    }
    
    public House addNewHouse(String name, String motto, ImageIcon crest) throws SpiderImageException, SpiderWriteException {
        return addNewHouse(new House(name, motto, crest));
    }
    
    public House addNewHouse(House house) throws SpiderImageException, SpiderWriteException {
        houseDao.save(house);
        return house;
    }
    
    public Character addNewCharacter(String name, Integer armySize, Character.Status status, House house) throws SpiderImageException, SpiderWriteException {
        return addNewCharacter(new Character(name, armySize, status, house));
    }
    
    public Character addNewCharacter(Character character) throws SpiderImageException, SpiderWriteException {
        characterDao.save(character);
        return character;
    }
    
    public Character updateCharacter(Character character) throws SpiderImageException, SpiderWriteException {
        characterDao.update(character);
        return character;
    }
    
    public Alliance addAlliance(House house1, House house2, LocalDate startDate) throws SpiderImageException, SpiderWriteException {
        return addAlliance(new Alliance(house1, house2, startDate));
    }
    
    public Alliance addAlliance(House house1, House house2, LocalDate startDate, LocalDate endDate) throws SpiderImageException, SpiderWriteException {
        return addAlliance(new Alliance(house1, house2, startDate, endDate));
    }
    
    public Alliance addAlliance(Alliance alliance) throws SpiderImageException, SpiderWriteException {
        allianceDao.save(alliance);
        return alliance;
    }
    
    public void endAlliance(Alliance alliance, LocalDate endDate) throws SpiderImageException, SpiderWriteException {
        allianceDao.closeAlliance(alliance, endDate);
    }
    
    public House getHouse(Integer id) throws SpiderReadException {
        return houseDao.findOneById(id);
    }
    
    public House getHouse(String name) throws SpiderReadException {
        return houseDao.findOneByName(name);
    }
    
    public Character getCharacter(Integer id) throws SpiderReadException {
        return characterDao.findOneById(id);
    }
    
    public Character getCharacter(String name) throws SpiderReadException {
        return characterDao.findOneByName(name);
    }
    
    public Alliance getAlliance(Integer id) throws SpiderReadException {
        return allianceDao.findOneById(id);
    }
    
    public List<House> getAllHouse() throws SpiderReadException {
        return houseDao.findAll();
    }
    
    public List<Alliance> getAllAlliance() throws SpiderReadException {
        return allianceDao.findAll();
    }
    
    public List<Alliance> getAllAlliance(House house) throws SpiderReadException {
        return allianceDao.findOpenWith(house);
    }
    
    public List<Character> getAllCharacter() throws SpiderReadException {
        return characterDao.findAll();
    }
    
    public List<Character> getAllCharacter(House house) throws SpiderReadException {
        return characterDao.findAll(house);
    }
}
