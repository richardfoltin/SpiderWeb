/*
 * ----------------------SpiderWeb----------------------
 * | Leiras:   Adatbazis alkalmazas Lord Varys szamara |
 * | Tantargy: ELTE - Programozasi Technologia 2.      |
 * | Szerzo:   Foltin Csaba Richárd (I37M02)           |
 * -----------------------------------------------------
 */
package spiderweb;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import spiderweb.jdbcdao.dbexception.SpiderImageException;

/**
 * Képek betöltéséért felelős statikus metódusok tartalmazó osztály
 * 
 * @author Foltin Csaba Richárd
 */
public class Resource {
         
    /**
     * Betölti egy képet a file neve alapján a Resource könyvtárból.
     * 
     * @param fileName
     * @return a file byteokban
     * @throws SpiderImageException 
     */
    public static byte[] getBytesFromResource(String fileName) throws SpiderImageException {
        URL url = Resource.class.getResource(fileName);
        String typeName = getTypeName(fileName);
        return getBytes(url, typeName);
    }
       
    /**
     * Betölt egy képet a megadott URL-ről
     * 
     * @param url
     * @return a file byteokban
     * @throws SpiderImageException 
     */
    public static byte[] getBytes(URL url) throws SpiderImageException {
        String typeName = getTypeName(url.toString());
        return getBytes(url, typeName);
    }
        
    /**
     * Betölt egy képet a megodott URL-ről a megadott kiterjesztéssel
     * 
     * @param url
     * @param typeName
     * @return a file byteokban
     * @throws SpiderImageException 
     */
    private static byte[] getBytes(URL url, String typeName) throws SpiderImageException {
        
        ByteArrayOutputStream baos;
        try {
            BufferedImage bi = ImageIO.read(url);
            baos = new ByteArrayOutputStream();
            ImageIO.write(bi, typeName, baos);
        } catch (IllegalArgumentException | IOException ex) {
            throw new SpiderImageException("Can't load " + url);
        }
        
        return baos.toByteArray();
    }
    
    /**
     * Betölt egy képet egy ImageIcon-ból
     * 
     * @param imageIcon
     * @return a file byteokban
     * @throws IOException 
     */
    public static byte[] getBytes(ImageIcon imageIcon) throws IOException {
        Image img = imageIcon.getImage();
        BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        
        Graphics2D bGr = bi.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, "png", baos);
        
        return baos.toByteArray();
    }
    
    /**
     * Átméretezi a képet.
     * 
     * @param srcImg a forrás kép
     * @param w szélesség pixelben
     * @param h magasság pixelben
     * @return 
     */
    public static ImageIcon getScaledImage(ImageIcon srcImg, int w, int h) {
        Image img = srcImg.getImage();
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(img, 0, 0, w, h, null);
        g2.dispose();
        
        return new ImageIcon(resizedImg);
    }
    
    /**
     * Visszaadja az elrési úton található file kiterjesztését.
     * 
     * @param path
     * @return 
     */
    private static String getTypeName(String path) {
        int dotPosition = path.lastIndexOf(".");
        return path.substring(dotPosition + 1);
    }
        
}
