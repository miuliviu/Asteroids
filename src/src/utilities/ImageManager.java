package utilities;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class ImageManager {

    //Image Path
    public final static String path = "resources/images/";

    //File extension
    public final static String ext = ".png";

    //Map to store images
    public static Map<String, Image> images = new HashMap<String, Image>();

    //Getter to get Image from map with a specific name
    public static Image getImage(String s) {
        return images.get(s);
    }

    //Method to load image
    public static Image loadImage(String fname) throws IOException {
        BufferedImage img = null;
        img = ImageIO.read(new File(path + fname + ext));
        images.put(fname, img);
        return img;
    }

}