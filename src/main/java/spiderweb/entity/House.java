/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spiderweb.entity;

import java.io.IOException;
import javax.swing.ImageIcon;
import spiderweb.Resource;

/**
 *
 * @author eandgna
 */
public class House extends Entity {

    private String name;
    private String motto;
    private ImageIcon crest;
    
    public House(String name, Integer id) {
        this.name = name;
        this.id = id;
    }
    
    public House(String name, Integer id, byte[] imageBytes) {
        this.name = name;
        this.id = id;
        this.crest = (imageBytes == null) ? null : new ImageIcon(imageBytes);
    }
    
    public House(String name, String motto, byte[] imageBytes) {
        this.name = name;
        this.motto = motto;
        this.crest = (imageBytes == null) ? null : new ImageIcon(imageBytes);
    }
    
    public House(String name, String motto, ImageIcon crest) {
        this.name = name;
        this.motto = motto;
        this.crest = crest;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMotto() {
        return motto;
    }
    
    public void setMotto(String motto) {
        this.motto = motto;
    }
    
    public ImageIcon getCrest() {
        return this.crest;
    }
    
    public byte[] getCrestBytes() throws IOException {
        return Resource.getBytes(crest);
    }
    
}
