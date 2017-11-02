package game1;

import utilities.Sprite;
import utilities.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by lmmiu on 27/01/2017.
 */
public class View extends JComponent {

    //Field witch hold the current game
    private Game game;

    /**
     * View class Constructor
     * @param game Game
     */
    public View(Game game) {
        this.game = game;
    }


    //Method to paint all the objects and sprites/images.
    @Override
    public void paintComponent(Graphics g0) {
        Graphics2D g = (Graphics2D) g0;



        if(game.gameMenu == true)
        {

            //Main menu background
            Sprite menuBack = new Sprite(Constants.MENU, Constants.FRAME_CENTER, Constants.UP, 1055,577 );
            menuBack.draw(g);

            //Logo Sprite in Main Menu
            Sprite logo = new Sprite(Constants.LOGO, new Vector2D(Constants.FRAME_WIDTH/2, Constants.FRAME_HEIGHT/4), new Vector2D(0,1), 2 * 4+100, 2 * 4+500);
            logo.draw(g);

            //Setting the Main Menu Text Font and Color
            Font setMenu = new Font("Courier New", Font.BOLD, 25);
            g.setFont(setMenu);
            g.setColor(Color.white);


            if (!game.ctrl.action.options){

                //Painting the Main Menu Text
                g.drawString("NEW GAME - PRESS ENTER", Constants.FRAME_WIDTH / 2-145, Constants.FRAME_HEIGHT / 2);
                g.drawString("OPTIONS - PRESS O", Constants.FRAME_WIDTH / 2-130, Constants.FRAME_HEIGHT / 2 + Constants.FRAME_HEIGHT / 8);

            } else {

                //Setting the font and color for the Option Menu text
                Font setOptions = new Font("Courier New", Font.BOLD, 20);
                g.setFont(setOptions);

                //Painting the Options Text on screen
                g.drawString("DIFFICULTY - PRESS G", Constants.FRAME_WIDTH / 2 - 145, Constants.FRAME_HEIGHT / 2 - 30);

                switch (game.N_INITIAL_ASTEROIDS) {
                    case 1:
                        g.setColor(Color.GREEN);
                        g.drawString("EASY", Constants.FRAME_WIDTH / 2 - 145, Constants.FRAME_HEIGHT / 2 + Constants.FRAME_HEIGHT / 8 -60);
                        break;
                    case 2:
                        g.setColor(Color.YELLOW);
                        g.drawString("MEDIUM", Constants.FRAME_WIDTH / 2 - 145, Constants.FRAME_HEIGHT / 2 + Constants.FRAME_HEIGHT / 8 -60);
                        break;
                    case 3:
                        g.setColor(Color.RED);
                        g.drawString("HARD", Constants.FRAME_WIDTH / 2 - 145, Constants.FRAME_HEIGHT / 2 + Constants.FRAME_HEIGHT / 8 -60);
                        break;
                }

                g.setColor(Color.WHITE);
                g.drawString("ENABLE/DISABLE SOUNDS - PRESS M", Constants.FRAME_WIDTH / 2 - 145, Constants.FRAME_HEIGHT / 2 + Constants.FRAME_HEIGHT / 4 -80);
                if (game.ctrl.action.sounds)
                {
                        g.setColor(Color.orange);
                        g.drawString("SOUNDS : ENABLED", Constants.FRAME_WIDTH / 2 - 145, Constants.FRAME_HEIGHT / 2 + Constants.FRAME_HEIGHT / 4 + Constants.FRAME_HEIGHT / 8 -110);
                }else{
                        g.setColor(Color.GRAY);
                        g.drawString("SOUNDS : DISABLED", Constants.FRAME_WIDTH / 2-145, Constants.FRAME_HEIGHT / 2 + Constants.FRAME_HEIGHT / 4 + Constants.FRAME_HEIGHT / 8 -110);
                }

                g.setColor(Color.WHITE);
                g.drawString("MAIN MENU - PRESS ESC", Constants.FRAME_WIDTH / 2-145, Constants.FRAME_HEIGHT / 2 + Constants.FRAME_HEIGHT / 4 + Constants.FRAME_HEIGHT / 8 );
            }



        } else {

            //Painting the Level Background Image
            if (game.hud.getLevel() < 10){
                Sprite sprite = new Sprite(Constants.planets.get(game.hud.getLevel() - 1), Constants.FRAME_CENTER, Constants.UP, 1055,577 );
                sprite.draw(g);}
            else
            {
                Sprite sprite = new Sprite(Constants.POTATO, Constants.FRAME_CENTER, Constants.UP, 1055,577 );
                sprite.draw(g);
            }

            //Painting all the GameObject objects
            synchronized (Game.class) {
                for (GameObject ob : Game.objects) {

                    ob.draw(g);
                }
            }


            g.setColor(Color.white);
            if (game.ctrl.action.viewscore) {

                //Painting the High Score screen
                int distance = 100;
                int position = 1;
                Font setare = new Font("Courier New", Font.BOLD, 25);
                g.setFont(setare);
                g.drawString("NEW GAME - PRESS R", Constants.FRAME_WIDTH / 2 - 125, Constants.FRAME_HEIGHT / 4);
                g.drawString("HIGH SCORE", Constants.FRAME_WIDTH / 2 - 60, Constants.FRAME_HEIGHT / 2);
                int Hstock = 0;

                //Painting the High Score Table
                for (int i : game.hScore.getHscore()) {
                    if (Hstock == 5)
                        break;
                    g.drawString(position + " : " + i, Constants.FRAME_WIDTH / 2 - 60, Constants.FRAME_HEIGHT / 2 + distance);
                    position += 1;
                    distance += 30;
                    Hstock++;
                }


            } else if (game.hud.getLives() <= 0) {
                //Adding score to HScore list
                if (!game.hScore.getHscore().contains(game.hud.getScore()))
                    game.hScore.addScore(game.hud.getScore());

                //Normalising the Lives
                if (game.hud.getLives() < 0)
                    game.hud.setLives(0);

                //Painting the Game Over logo
                Font current = g.getFont();
                Sprite gameover = new Sprite(Constants.GAMEOVER, new Vector2D(Constants.FRAME_WIDTH/2, Constants.FRAME_HEIGHT/4), new Vector2D(0,1), 2 * 4+100, 2 * 4+500);
                gameover.draw(g);

                //Painting the Game Over Screen text
                Font setare2 = new Font("Courier New", Font.BOLD, 25);
                g.setFont(setare2);
                g.setColor(Color.WHITE);
                g.drawString("NEW GAME - PRESS R", Constants.FRAME_WIDTH / 10, Constants.FRAME_HEIGHT / 2 + Constants.FRAME_HEIGHT / 4);
                g.drawString("HIGH SCORE - PRESS H", (Constants.FRAME_WIDTH / 2 + Constants.FRAME_WIDTH / 4) - 110, Constants.FRAME_HEIGHT / 2 + Constants.FRAME_HEIGHT / 4);
                g.setFont(current);
            }

            //Painting the stats HUD
            Font setare2 = new Font("Courier New", Font.BOLD, 16);
            g.setFont(setare2);
            g.drawString(Game.hud.getTextScore(), 10, 15);
            g.drawString(Game.hud.getTextLives(), Constants.FRAME_WIDTH / 2 - 25, 15);
            g.drawString(Game.hud.getTextLevel(), Constants.FRAME_WIDTH - 100, 15);
            g.drawString(Game.hud.getTextNuke(), Constants.FRAME_WIDTH - 100, Constants.FRAME_HEIGHT-10);
            g.drawString(Game.hud.getTextFuel(),  10, Constants.FRAME_HEIGHT-10);
        }
    }

    //Method to set the game
    public void setGame(Game g1){
        this.game=g1;
    }

    @Override
    public Dimension getPreferredSize() {
        return Constants.FRAME_SIZE;
    }
}