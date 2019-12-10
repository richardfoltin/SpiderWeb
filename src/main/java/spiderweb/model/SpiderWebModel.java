/*
 * ----------------------SpiderWeb----------------------
 * | Leírás:   Adatbázis alkalmazás Lord Varys számára |
 * | Tantárgy: ELTE - Programozási Technológia 2.      |
 * | Szerző:   Foltin Csaba Richárd (I37M02)           |
 * -----------------------------------------------------
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
 * A program modelje
 * 
 * @author Foltin Csaba Richárd
 */
public class SpiderWebModel {
    
    private final HouseDao houseDao;    
    private final CharacterDao characterDao;
    private final AllianceDao allianceDao;
     
    public SpiderWebModel() {
        this.houseDao = new JDBCHouseDao();
        this.characterDao = new JDBCCharacterDao();
        this.allianceDao = new JDBCAllianceDao();
    }
    
    /**
     * Ház hozzáadása az adatbázishoz 
     * 
     * @param name
     * @param motto
     * @param url a ház címerének elérési útja
     * @return
     * @throws SpiderImageException
     * @throws SpiderWriteException 
     */
    public House addNewHouse(String name, String motto, URL url) throws SpiderImageException, SpiderWriteException {
        return addNewHouse(new House(name, motto, Resource.getBytes(url)));
    }
    
    /**
     * Ház hozzáadása az adatbázishoz
     * 
     * @param name
     * @param motto
     * @param crestFileNameInResource a ház címerének elérési útja a resource mappában
     * @return
     * @throws SpiderImageException
     * @throws SpiderWriteException 
     */
    public House addNewHouse(String name, String motto, String crestFileNameInResource) throws SpiderImageException, SpiderWriteException {
        return addNewHouse(new House(name, motto, Resource.getBytesFromResource(crestFileNameInResource)));
    }
    
    /**
     * Ház hozzáadása az adatbázishoz
     * 
     * @param name
     * @param motto
     * @param crest
     * @return
     * @throws SpiderImageException
     * @throws SpiderWriteException 
     */
    public House addNewHouse(String name, String motto, ImageIcon crest) throws SpiderImageException, SpiderWriteException {
        return addNewHouse(new House(name, motto, crest));
    }
    
    /**
     * Ház hozzáadása az adatbázishoz
     * 
     * @param house
     * @return
     * @throws SpiderImageException
     * @throws SpiderWriteException 
     */
    public House addNewHouse(House house) throws SpiderImageException, SpiderWriteException {
        houseDao.save(house);
        return house;
    }
    
    /**
     * Karakter hozzáadása az adatbázishoz
     * 
     * @param name
     * @param armySize
     * @param status
     * @param house
     * @return
     * @throws SpiderImageException
     * @throws SpiderWriteException 
     */
    public Character addNewCharacter(String name, Integer armySize, Character.Status status, House house) throws SpiderImageException, SpiderWriteException {
        return addNewCharacter(new Character(name, armySize, status, house));
    }
    
    /**
     * Karakter hozzáadása az adatbázishoz
     * 
     * @param character
     * @return
     * @throws SpiderImageException
     * @throws SpiderWriteException 
     */
    public Character addNewCharacter(Character character) throws SpiderImageException, SpiderWriteException {
        characterDao.save(character);
        return character;
    }
    
    /**
     * A karakter értékeinek frissítése az adatbázisban
     * 
     * @param character
     * @return
     * @throws SpiderImageException
     * @throws SpiderWriteException 
     */
    public Character updateCharacter(Character character) throws SpiderImageException, SpiderWriteException {
        characterDao.update(character);
        return character;
    }
    
    /**
     * Szövetség hozzáadása az adatbázishoz
     * 
     * @param house1
     * @param house2
     * @param startDate
     * @return
     * @throws SpiderImageException
     * @throws SpiderWriteException 
     */
    public Alliance addAlliance(House house1, House house2, LocalDate startDate) throws SpiderImageException, SpiderWriteException {
        return addAlliance(new Alliance(house1, house2, startDate));
    }
    
    /**
     * Szövetség hozzáadása az adatbázishoz
     * 
     * @param house1
     * @param house2
     * @param startDate
     * @param endDate
     * @return
     * @throws SpiderImageException
     * @throws SpiderWriteException 
     */
    public Alliance addAlliance(House house1, House house2, LocalDate startDate, LocalDate endDate) throws SpiderImageException, SpiderWriteException {
        return addAlliance(new Alliance(house1, house2, startDate, endDate));
    }
    
    /**
     * Szövetség hozzáadása az adatbázishoz
     * 
     * @param alliance
     * @return
     * @throws SpiderImageException
     * @throws SpiderWriteException 
     */
    public Alliance addAlliance(Alliance alliance) throws SpiderImageException, SpiderWriteException {
        allianceDao.save(alliance);
        return alliance;
    }
    
    /**
     * Szövetség lezárása az adatbázisban
     * 
     * @param alliance
     * @param endDate
     * @throws SpiderImageException
     * @throws SpiderWriteException 
     */
    public void endAlliance(Alliance alliance, LocalDate endDate) throws SpiderImageException, SpiderWriteException {
        allianceDao.closeAlliance(alliance, endDate);
    }
    
    /**
     * Ház megkeresése az adatbázisban kulcs alapján
     * 
     * @param id
     * @return
     * @throws SpiderReadException 
     */
    public House getHouse(Integer id) throws SpiderReadException {
        return houseDao.findOneById(id);
    }
    
    /**
     * Ház megkeresése az adatbázisban neve alapján
     * 
     * @param name
     * @return
     * @throws SpiderReadException 
     */
    public House getHouse(String name) throws SpiderReadException {
        return houseDao.findOneByName(name);
    }
    
    /**
     * Karakter megkeresése az adatbázisban kulcs alapján
     * 
     * @param id
     * @return
     * @throws SpiderReadException 
     */
    public Character getCharacter(Integer id) throws SpiderReadException {
        return characterDao.findOneById(id);
    }
    
    /**
     * Karakter megkeresése az adatbázisban neve alapján
     * 
     * @param name
     * @return
     * @throws SpiderReadException 
     */
    public Character getCharacter(String name) throws SpiderReadException {
        return characterDao.findOneByName(name);
    }
    
    /**
     * Szövetség megkeresése az adatbázisban kulcs alapján
     * 
     * @param id
     * @return
     * @throws SpiderReadException 
     */
    public Alliance getAlliance(Integer id) throws SpiderReadException {
        return allianceDao.findOneById(id);
    }
    
    /**
     * Az összes ház kilistázása az adatbázisból
     * 
     * @return
     * @throws SpiderReadException 
     */
    public List<House> getAllHouse() throws SpiderReadException {
        return houseDao.findAll();
    }
    
    /**
     * Az összes szövetség kilistázása az adatbázisból
     * 
     * @return
     * @throws SpiderReadException 
     */
    public List<Alliance> getAllAlliance() throws SpiderReadException {
        return allianceDao.findAll();
    }
    
    /**
     * A házhoz tartozó összes szövetség kilistázása az adatbázisból
     * 
     * @param house
     * @return
     * @throws SpiderReadException 
     */
    public List<Alliance> getAllAlliance(House house) throws SpiderReadException {
        return allianceDao.findOpenWith(house);
    }
    
    /**
     * Az összes karakter kilistázása az adatbázisból
     * 
     * @return
     * @throws SpiderReadException 
     */
    public List<Character> getAllCharacter() throws SpiderReadException {
        return characterDao.findAll();
    }
    
    /**
     * A házhoz tartozó összes karakter kilistázása az adatbázisból
     * 
     * @param house
     * @return
     * @throws SpiderReadException 
     */
    public List<Character> getAllCharacter(House house) throws SpiderReadException {
        return characterDao.findAll(house);
    }
}
