
/**
 * This class runs the main game
 * 
 * @author (Matekoa Motsoasele) 
 * @version (May 2016)
 */

import javax.swing.*;

public class Driver
{
    public static void main(String [] args)
    {
        GamePanel panel = new GamePanel();
        JFrame game = new JFrame("Reversi Project");
        
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.getContentPane().add(panel);
        game.setVisible(true);
        game.pack();
        game.setResizable(false);
    }
}
