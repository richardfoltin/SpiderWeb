/*
 * ----------------------SpiderWeb----------------------
 * | Leírás:   Adatbázis alkalmazás Lord Varys számára |
 * | Tantárgy: ELTE - Programozási Technológia 2.      |
 * | Szerző:   Foltin Csaba Richárd (I37M02)           |
 * -----------------------------------------------------
 */
package spiderweb.entity;

import java.io.IOException;
import javax.swing.ImageIcon;

import spiderweb.Resource;

/**
 * Ház
 * 
 * @author Foltin Csaba Richárd
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

    public void setName(String name) {
        this.name = name;
    }
    
    public void setMotto(String motto) {
        this.motto = motto;
    }
    
    public String getName() {
        return name;
    }

    public String getMotto() {
        return motto;
    }
    
    public ImageIcon getCrest() {
        return this.crest;
    }
    
    public byte[] getCrestBytes() throws IOException {
        return Resource.getBytes(crest);
    }
    
    @Override
    public String toString() {
        return "[House] #" + id + " " + name;
    }
}
