package game1;

import utilities.Sprite;
import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by lmmiu on 10/02/2017.
 */
public class Bullet extends GameObject {
    //Bullet object speed
    public static final double BULLET_SPEED = 350;

    //time alive
    private int ttl = 100;

    //Bullet direction vector; Where is the front of the bullet
    public final Vector2D direction;

    /**
     * Bullet default Constructor
     * @param r Radius
     * @param p Position
     * @param v Velocity
     */
    public Bullet(int r, Vector2D p, Vector2D v) {
        super(r, p, v);

        //setting the direction the same as the ship direction
        direction = new Vector2D(Game.ship.direction);
    }

    //update method for bullet object
    public void update() {
        super.update();

        //decreasing the bullet leaving time and killing it if it reaches zero
        ttl --;
        if (ttl <= 0)
            dead = true;
    }

    //Drawing the bullet sprite
    public void draw(Graphics2D g) {

        Sprite sprite = new Sprite(Constants.BULLET, this.position, direction, 2 * RADIUS+20, 2 * RADIUS);
        sprite.draw(g);

    }



    @Override
    public String getName(){
        super.getName();
        return "Bullet";
    }

    public String toString() {
        return "Bullet{" +
                "BULLET_TIME=" + ttl +
                '}';
    }
}
