/*
 * ----------------------SpiderWeb----------------------
 * | Leírás:   Adatbázis alkalmazás Lord Varys számára |
 * | Tantárgy: ELTE - Programozási Technológia 2.      |
 * | Szerző:   Foltin Csaba Richárd (I37M02)           |
 * -----------------------------------------------------
 */
package spiderweb.entity;

/**
 * Karakter
 * 
 * @author Foltin Csaba Richárd
 */
public class Character extends Entity {
    
    public enum Status { Living, Deceased}
    
    private final String name;
    private final House house;
    private Integer armySize;
    private Status status;

    public Character(String name, Integer armySize, Status status, House house) {
        this.name = name;
        this.armySize = armySize;
        this.status = status;
        this.house = house;
    }
    
    public Character(String name, Integer armySize, boolean status, House house) {
        this.name = name;
        this.armySize = armySize;
        this.status = (status) ? Status.Living : Status.Deceased;
        this.house = house;
    }
        
    public void setArmySize(Integer newArmySize) {
        this.armySize = newArmySize;
    }
    
    public void setStatus(Status newStatus) {
        this.status = newStatus;
    }
    
    public String getName() {
        return name;
    }

    public House getHouse() {
        return house;
    }
    
    /**
     * A karakterhez tartozó ház neve
     * Ha a karakter nem tartozik egy házhoz sem, akkor "No House"
     * 
     * @return 
     */
    public String getHouseName() {
        return (hasHouse()) ? house.getName() : "No House";
    }

    public Integer getArmySize() {
        return armySize;
    }

    public Status getStatus() {
        return status;
    }
    
    public Boolean isLiving() {
        return status == Status.Living;
    }
    
    /**
     * A karakter tartozik-e házhoz
     * 
     * @return 
     */
    public boolean hasHouse() {
        return house != null;
    }

    @Override
    public String toString() {
        return "[Character] #" + id + " " + name + "(" + status.toString() + ") @" + getHouseName() + " Army: " + armySize.toString();
    }
}
