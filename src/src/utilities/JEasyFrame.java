package utilities;

import javax.swing.*;
import java.awt.*;

/**
 * Created by lmmiu on 27/01/2017.
 */
public class JEasyFrame extends JFrame {
    public Component comp;

    /**
     * Constructor which initialise a frame with a specified title and adds a specified component to the its center
     * @param comp Component (View)
     * @param title Title
     */
    public JEasyFrame(Component comp, String title) {
        super(title);

        //This should be the view component
        this.comp = comp;

        //Adding the component to the frame
        getContentPane().add(BorderLayout.CENTER, comp);
        pack();

        //making the frame visible
        this.setVisible(true);

        //Setting the exit method
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Setting the frame non-resizable
        setResizable(false);

        //setting the location of the frame in the middle of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        //repainting the component
        repaint();
    }
}

