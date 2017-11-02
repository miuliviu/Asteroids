package game1;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by lmmiu on 27/01/2017.
 */
public class Keys extends KeyAdapter implements Controller  {
    Action action;

    /**
     * keys constructor creating a new action object
     */
    public Keys() {
        action = new Action();
    }

    //Method to return an action instance
    public Action action() {
        // this is defined to comply with the standard interface
        return action;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {

            //Deploy a Nuke
            case KeyEvent.VK_CONTROL:
                action.nukeTrigger = true;
                break;

            //Throttle up
            case KeyEvent.VK_UP:
                action.thrust = 1;
                break;

            //Rotate counter-clockwise
            case KeyEvent.VK_LEFT:
                action.turn = -1;
                break;

            //Rotate clockwise
            case KeyEvent.VK_RIGHT:
                action.turn = +1;
                break;

            //Shoot missile
            case KeyEvent.VK_SPACE:
                action.shoot = true;
                break;

            //Restart game if in Game Over Screen
            case KeyEvent.VK_R:
                if(Game.hud.getLives()<=0)
                action.start = true;

                //View High Score if in Game Over Screen
            case KeyEvent.VK_H:
                if(Game.hud.getLives()<=0)
                    action.viewscore = true;
                break;

            //Start a new game from Main Menu Screen
            case KeyEvent.VK_ENTER:
                if(Game.gameMenu && !action.options)
                {
                   Game.gameMenu=false;
                   action.options=false;
                   action.meniuClose =true;
                }
                break;

            //Enter option Menu
            case KeyEvent.VK_O:
                if(Game.gameMenu)
                    action.options=true;
                break;

            //Change the Game Difficulty if in Option Menu
            case KeyEvent.VK_G:
                if(action.options)
                {
                    if(Game.N_INITIAL_ASTEROIDS!=3)
                    {
                        Game.N_INITIAL_ASTEROIDS++;
                    }
                    else
                        Game.N_INITIAL_ASTEROIDS=1;
                }
                break;

            //Enable/Disable music if in Option menu
            case KeyEvent.VK_M:
                if(action.options)
                    action.sounds = !action.sounds;
                break;

            //Return to Main Menu if in Option Menu
            case KeyEvent.VK_ESCAPE:
                if(action.options)
                {
                    action.options=false;
                    Game.gameMenu=true;
                }

        }
    }

    //Turning the movement and shooting action off if a key is released
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP:
                action.thrust = 0;
                break;
            case KeyEvent.VK_LEFT:
                action.turn = 0;
                break;
            case KeyEvent.VK_RIGHT:
                action.turn = 0;
                break;
            case KeyEvent.VK_SPACE:
                action.shoot = false;
                break;
        }
    }


}