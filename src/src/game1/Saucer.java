package game1;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import utilities.SoundManager;
import utilities.Sprite;
import utilities.Vector2D;

import java.awt.*;

import static game1.Game.objects;
import static game1.Game.saucer;

/**
 * Created by Liviu on 12-Mar-17.
 */
public class Saucer extends GameObject {

    //Variable to store the Enemy Bullet
    public EBullet ebullet;

    //Vector which specifies the direction of the enemy ship
    public Vector2D direction;

    //Bullet velocity for going upwards
    public Vector2D bulletVelocityUP = new Vector2D(0,400);

    //Bullet velocity for going downwards
    public Vector2D bulletVelocityDOWN = new Vector2D(0,-400);

    //Timer for the enemy ship to attack
    private int attackTime = 0;

    /**
     * Constructor for the enemy saucer
     * @param position Position
     * @param velocity Velocity
     */
    public Saucer(Vector2D position, Vector2D velocity){
        super(20, position, velocity);
        direction= new Vector2D(0,-1);
    }

    //Updating the shiop position and the time of attack; Also starting the sounds
    public void update() {
        super.update();
        attackTime++;
        if(attackTime==50)
        {
            attackTime=0;
            mkEnemyBullet();
        }

        if(Game.ctrl.action.sounds)
            SoundManager.startAlarm();

    }

    //Painting the enemy saucer on the screen according to type Lizard Saucer / Potato Aline (level 10)
    public void draw(Graphics2D g) {
        if(Game.hud.getLevel()<10)
        {
            Sprite spriteSaucer = new Sprite(Constants.SAUCER, this.position, this.direction, 2 * RADIUS, 2 * RADIUS);
            spriteSaucer.draw(g);
        }else{
            Sprite spriteSaucer = new Sprite(Constants.POTATOSAUCER, this.position, this.direction, 2 * RADIUS+50, 2 * RADIUS+50);
            spriteSaucer.draw(g);
        }
    }

    //Method to create Enemy Bullet
    private void mkEnemyBullet() {

        Vector2D bulletPosition = new Vector2D(position);


        if(Game.ship.position.y >= position.y)
            ebullet = new EBullet(8, bulletPosition, bulletVelocityUP);
        else
            ebullet = new EBullet(8, bulletPosition, bulletVelocityDOWN);
        if(Game.ctrl.action.sounds)
            SoundManager.fire();

    }

    @Override
    public void hit() {
        super.hit();
        if(Game.ctrl.action.sounds)
            SoundManager.stopAlarm();
        objects.remove(this);
        objects.add(new Explosion(this.RADIUS, this.position,new Vector2D(0,0)));

    }

    @Override
    public String getName(){
        super.getName();
        return "Saucer";
    }
}
