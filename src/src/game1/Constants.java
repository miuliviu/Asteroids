package game1;

import utilities.ImageManager;
import utilities.Vector2D;

import java.awt.*;
import java.io.IOException;
import java.util.*;

/**
 * Created by lmmiu on 27/01/2017.
 */
public class Constants {

    //Width and Height of the frame
    public static final int FRAME_HEIGHT = 555;
    public static final int FRAME_WIDTH = 1013;
    public static final Dimension FRAME_SIZE = new Dimension(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
    public static final Vector2D FRAME_CENTER = new Vector2D(FRAME_WIDTH / 2, FRAME_HEIGHT / 2);
    public static final Vector2D UP = new Vector2D(1,0);

    // sleep time between two frames
    public static final int DELAY = 20;  // in milliseconds
    public static final double DT = DELAY / 1000.0;  // in seconds

    //Declaration of the imported images
    public static Image ASTEROID1, POTATO, SAUCER, SHIP, BULLET, PROPULSION, EBULLET, MENU, LOGO, GAMEOVER, TARGET, NUKE, POTATOASTEROID, POTATOSAUCER, CRATE, CRATEHP, SHIELD;

    public static ArrayList<Image> planets;
    public static ArrayList<Image> explosion;

    static {
        try {

            //ArrayLists used to store background and explosion animation sprites
            planets = new ArrayList<Image>();
            explosion = new ArrayList<Image>();

            //Menu background and Logo sprites
            MENU = ImageManager.loadImage("menu");
            LOGO = ImageManager.loadImage("logo");

            //Nuke sprites, the target and the bomb
            TARGET = ImageManager.loadImage("target");
            NUKE = ImageManager.loadImage("nuke");

            //Game Over title sprite
            GAMEOVER = ImageManager.loadImage("gameover");

            //Asteroids and enemy saucer sprites
            ASTEROID1 = ImageManager.loadImage("asteroid1");
            SAUCER = ImageManager.loadImage("saucer");

            //Potato world sprites
            POTATO = ImageManager.loadImage("potato");
            POTATOASTEROID = ImageManager.loadImage("potatoAsteroid");
            POTATOSAUCER = ImageManager.loadImage("potatoSaucer");

            //Background sprites
            planets.add(ImageManager.loadImage("earth"));
            planets.add(ImageManager.loadImage("moon"));
            planets.add(ImageManager.loadImage("mars"));
            planets.add(ImageManager.loadImage("venus"));
            planets.add(ImageManager.loadImage("sun"));
            planets.add(ImageManager.loadImage("hole"));
            planets.add(ImageManager.loadImage("galaxy"));
            planets.add(ImageManager.loadImage("star"));
            planets.add(ImageManager.loadImage("milkyway1"));

            //Explosion animation sprites
            explosion.add(ImageManager.loadImage("explosion0"));
            explosion.add(ImageManager.loadImage("explosion1"));
            explosion.add(ImageManager.loadImage("explosion2"));
            explosion.add(ImageManager.loadImage("explosion3"));
            explosion.add(ImageManager.loadImage("explosion4"));
            explosion.add(ImageManager.loadImage("explosion5"));
            explosion.add(ImageManager.loadImage("explosion6"));
            explosion.add(ImageManager.loadImage("explosion7"));
            explosion.add(ImageManager.loadImage("explosion8"));
            explosion.add(ImageManager.loadImage("explosion9"));
            explosion.add(ImageManager.loadImage("explosion10"));
            explosion.add(ImageManager.loadImage("explosion11"));

            //Shield and Ship+Propulsion sprites
            SHIP = ImageManager.loadImage("ship");
            SHIELD = ImageManager.loadImage("shield");
            PROPULSION = ImageManager.loadImage("propulsion");

            //Player Bullet and Alien Bullet sprites
            BULLET = ImageManager.loadImage("bullet");
            EBULLET = ImageManager.loadImage("ebullet");

            //Create sprites
            CRATE = ImageManager.loadImage("crate");
            CRATEHP = ImageManager.loadImage("cratehp");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}