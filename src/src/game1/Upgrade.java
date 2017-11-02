package game1;

import utilities.Sprite;
import utilities.Vector2D;

import java.awt.*;

/**
 * Created by Liviu on 18-Mar-17.
 */
public class Upgrade extends GameObject {

    //Direction of the object
    public final Vector2D direction = new Vector2D(0, 0);

    //Time alive on screen
    public int timeAlive = 800;

    //Type of crate
    public int type;

    /**
     * Upgrade class Constructor with specifeid type of crate
     * @param r Radius
     * @param p Position
     * @param v Velocity
     * @param type Type of Crate 1 or 2
     */
    public Upgrade(int r, Vector2D p, Vector2D v, int type) {
        super(r, p, v);
        this.type=type;
    }

    //Update method to specify when to disappear
    public void update(){
        super.update();
        if (timeAlive<=0)
            dead=true;
        timeAlive--;
    }

    //Draw method to draw the object frame
    public void draw(Graphics2D g) {

        //Drawing the object according to the type selected
        if(timeAlive>400)
        {
            if(type==1) {
                Sprite sprite = new Sprite(Constants.CRATE, this.position, direction, 2 * RADIUS + 5, 2 * RADIUS);
                sprite.draw(g);
            }else{
                if(type==2){
                    Sprite sprite = new Sprite(Constants.CRATEHP, this.position, direction, 2 * RADIUS + 5, 2 * RADIUS);
                    sprite.draw(g);
                }
            }

            //making the crate flash if half of the TimeAlive passed
        }else{
            if(timeAlive%40!=0){
                if(type==1) {
                    Sprite sprite = new Sprite(Constants.CRATE, this.position, direction, 2 * RADIUS + 5, 2 * RADIUS);
                    sprite.draw(g);
                }else{
                        Sprite sprite = new Sprite(Constants.CRATEHP, this.position, direction, 2 * RADIUS + 5, 2 * RADIUS);
                        sprite.draw(g);

                }
            }
        }

    }

    @Override
    public String getName(){
        super.getName();
        return "Upgrade";
    }

    @Override
    public void hit() {
        dead=true;
    }
}
