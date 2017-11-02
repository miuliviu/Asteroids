package game1;

import utilities.Sprite;
import utilities.Vector2D;

import java.awt.*;

/**
 * Created by Liviu on 12-Mar-17.
 */
public class EBullet extends GameObject {

    //Time alive counter
    private int ttl = 100;
    //Vector defining the direction of the object
    public final Vector2D direction;

    /**
     * Default GameObject constructor with direction defined
     * @param r Radius
     * @param p Position
     * @param v Velocity
     */
    public EBullet(int r, Vector2D p, Vector2D v) {
        super(r, p, v);
        direction = new Vector2D(Game.ship.direction);
    }

    //Updating the Enemy Bullet position and destroying it when the time alive counter reaches zero
    public void update() {
        super.update();
        ttl --;
        if (ttl <= 0)
            dead = true;
    }

    //painting the Enemy bullet sprite on the frame
    public void draw(Graphics2D g) {

        Sprite sprite = new Sprite(Constants.EBULLET, this.position, direction, 2 * RADIUS+20, 2 * RADIUS+20);
        sprite.draw(g);

    }



    @Override
    public String getName(){
        super.getName();
        return "EBullet";
    }

    public String toString() {
        return "EBullet{" +
                "EBULLET_TIME=" + ttl +
                '}';
    }
}
