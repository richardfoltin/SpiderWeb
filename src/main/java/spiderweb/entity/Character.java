/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spiderweb.entity;

/**
 *
 * @author pokemonterkep
 */
public class Character extends Entity {
    
    public enum Status { Living, Deceased}
    
    private String name;
    private Integer armySize;
    private Status status;
    private House house;

    public Character(String name, Integer armySize, Status status, House house) {
        this.name = name;
        this.armySize = armySize;
        this.status = status;
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
    
    public static Status statusFromBoolean(Boolean statusBoolean) {
        return (statusBoolean) ? Status.Living : Status.Deceased;
    }
    
    public boolean hasHouse() {
        return house != null;
    }

    @Override
    public String toString() {
        return "[Character] #" + id + " " + name + "(" + status.toString() + ") @" + getHouseName() + " Army: " + armySize.toString();
    }
}
