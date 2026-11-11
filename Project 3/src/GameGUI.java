//Author: Alexandra Pettit
//Due: April 4th
import javax.swing.*;
/**
 * Welcomes user to the game "Friend Fight!" and explains the character selection and goal
 */
public class GameGUI extends JFrame {

    /**
     * Creates a new frame for th character selection process
     */
    public GameGUI() {

        //Setting up the frame
        this.setTitle("Friend Fight!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 800);
        this.setLocation(425, 100);

        //Starts the character selection process after the JOptionPane is clicked out of
        new CharSelect(this);

        //Welcome message for the user and explanation for character selection screen
        JOptionPane.showMessageDialog(null, "Welcome to Friend Fight!\n" +
                "In this game, you will fight against a Friend until someone's health drops to or below zero.\n" +
                "Each Fighter has their own weapons, health, and max amount of damage they can deal, so choose whatever\n" +
                "playstyle fits you best!\n\n" +
                "Once you chose your fighter and which Friend you want to battle with, you'll enter\n" +
                "The Arena.\n\n" +
                "Choose your character and opponent to begin.");

    }
}
