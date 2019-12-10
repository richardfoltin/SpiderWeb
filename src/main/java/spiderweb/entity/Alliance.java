/*
 * ----------------------SpiderWeb----------------------
 * | Leírás:   Adatbázis alkalmazás Lord Varys számára |
 * | Tantárgy: ELTE - Programozási Technológia 2.      |
 * | Szerző:   Foltin Csaba Richárd (I37M02)           |
 * -----------------------------------------------------
 */
package spiderweb.entity;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Szövetség
 * 
 * @author Foltin Csaba Richárd
 */
public class Alliance extends Entity {
    
    private House house1;
    private House house2;
    private LocalDate startDate;
    private LocalDate endDate;

    public Alliance(House house1, House house2) {
        this.house1 = house1;
        this.house2 = house2;
    }
    
    public Alliance(House house1, House house2, LocalDate startDate) {
        this.house1 = house1;
        this.house2 = house2;
        this.startDate = startDate;
    }
    
    public Alliance(House house1, House house2, LocalDate startDate, LocalDate endDate) {
        this.house1 = house1;
        this.house2 = house2;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    public House getHouse1() {
        return house1;
    }

    public House getHouse2() {
        return house2;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
    
    /**
     * Lezárt-e a szövetség?
     * 
     * @return true ha van végdátum
     */
    public boolean isClosed() {
        return endDate != null;
    }
    
    /**
     * Beállítja a megadott házat (ha van ilyen) az első háznak a szövetségben
     * @param firstHouse 
     */
    public void setFirstHouse(House firstHouse) {
        if (!Objects.equals(house1.getId(), firstHouse.getId())) {
            house2 = house1;
            house1 = firstHouse;
        }
    }
    
    @Override
    public String toString() {
        return "[Alliance] #" + id + " " + house1.getName() + "-" + house2.getName() + " " + startDate.toString() + "->" + ((isClosed()) ? endDate.toString() : "");
    }
}
