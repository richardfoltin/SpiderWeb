package spiderweb;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import javax.swing.ImageIcon;
import spiderweb.jdbcdao.dbexception.SpiderImageException;

public class Resource {
    
    public static InputStream loadResource(String resName){
        return Resource.class.getResourceAsStream(resName);
    }
    
    public static Image loadImage(String resName) throws IOException{
        URL url = Resource.class.getResource(resName);
        return ImageIO.read(url);
    }
    
    public static BufferedImage loadBufferedImage(String resName) throws IOException {
        URL url = Resource.class.getResource(resName);
        return ImageIO.read(url);
    }
    
    public static byte[] getBytes(String resName) throws SpiderImageException {
        return getBytes(resName, "png");
    }
    
    public static byte[] getBytes(String resName, String typeName) throws SpiderImageException {
       
        URL url = Resource.class.getResource(resName);
        ByteArrayOutputStream baos;
        
        try {
            BufferedImage bi = ImageIO.read(url);
            baos = new ByteArrayOutputStream();
            ImageIO.write(bi, typeName, baos);
        } catch (IllegalArgumentException | IOException ex) {
            throw new SpiderImageException("Can't load " + resName);
        }
        
        return baos.toByteArray();
    }
    
    public static byte[] getBytes(ImageIcon imageIcon) throws IOException {
        BufferedImage bi = getBufferedImage(imageIcon.getImage());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, "png", baos);
        
        return baos.toByteArray();
    }
    
    private static BufferedImage getBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
           return (BufferedImage) img;
        }

        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }
    
    public static ImageIcon getScaledImage(ImageIcon srcImg, int w, int h) {
        return new ImageIcon(getScaledImage(srcImg.getImage(), w, h));
    }
    
    public static Image getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }   
        
}
