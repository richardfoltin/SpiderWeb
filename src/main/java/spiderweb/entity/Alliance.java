/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spiderweb.entity;

import java.time.LocalDate;

/**
 *
 * @author pokemonterkep
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
    
    public boolean isClosed() {
        return endDate != null;
    }
    
}
