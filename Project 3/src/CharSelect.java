//Author: Alexandra Pettit
//Due: April 4th
import images.Player;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Both AI citations are listed at the bottom of the class for legibility

/**
 * The entire GUI used for character selection
 */
public class CharSelect implements ActionListener {

    //Character attributes
    public String fighter;
    public String weapon;
    public int maxDamage;
    public int health;

    //Selection variables at the bottom
    private JTextArea textArea;
    private JLabel imageLabel;
    public String img;

    //Added with the help of AI:
    private Player player1 = null;
    private boolean selectingSecond = false;
    private JLabel charSelectLabel;

    /**
     * Creates the character selection screen
     * @param frame builds off of the frame that is established in the GameGUI class
     */
    public CharSelect(JFrame frame) {

        //Overarching panel
        JPanel charSelectPanel = new JPanel();
        charSelectPanel.setLayout(new BorderLayout());//Changed from grid layout

        //Prompt at the top of the screen
        charSelectLabel = new JLabel("Choose your Fighter:");
        charSelectLabel.setFont(new Font("Arial", Font.BOLD, 28));
        charSelectLabel.setLayout(new BorderLayout());
        charSelectLabel.setHorizontalAlignment(JLabel.CENTER);

        //Adds the label to the top of the screen
        charSelectPanel.add(charSelectLabel, BorderLayout.NORTH);

        //Character button panel for grid layout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 2));

        //Initially I had all the buttons written out individually until I shortened it into this loop
        //(As seen in at the end of this class with the AI prompt I used)
        String[] playerName = {"Elsie", "Zach", "Spencer", "Chloe", "Casey", "Alex"};

        //Loops through each name and makes the corresponding button for it
        for (String name : playerName){
            JButton playerButton = new JButton(name);
            playerButton.addActionListener(this);
            playerButton.setFont(new Font("Arial", Font.BOLD, 20));
            buttonPanel.add(playerButton);
        }

        //Adds all the buttons into a grid
        buttonPanel.setLayout(new GridLayout(3, 2));

        //Adding button panel to character select panel at the center of the screen
        charSelectPanel.add(buttonPanel, BorderLayout.CENTER);//BorderLayout center was here

        //New panel for the bottom of the screen
        JPanel bottom = new JPanel();

        //Character information panel with their PNG images, labels, and descriptions
        JPanel infoPanel = new JPanel(new BorderLayout());

        //Sets PNG image sizes and aligns them to the left side
        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(150, 150));
        infoPanel.add(imageLabel, BorderLayout.WEST);

        //Creating the textbox for telling the player fighter details
        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.BOLD, 18));
        textArea.setColumns(10);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);

        //Adds the textbox to be in the middle of the bottom portion
        infoPanel.add(textArea, BorderLayout.CENTER);
        bottom.add(infoPanel, BorderLayout.CENTER);

        //Confirm character button creation and aligns it to the right
        JButton confirmButton = new JButton("Confirm Fighter");
        confirmButton.addActionListener(this);
        confirmButton.setFont(new Font("Arial", Font.BOLD, 18));
        bottom.add(confirmButton, BorderLayout.SOUTH);
        charSelectPanel.add(bottom, BorderLayout.SOUTH);

        //Adds the charSelect panel and everything within it to the frame
        frame.add(charSelectPanel);
    }

    /**
     * Lists the characters and their attributes, then uses attributes to create a Player instance
     * @param e the event to be processed
     */
    public void actionPerformed(ActionEvent e) {
        //No text starting out
        textArea.setText("");

        //Switch usage was based on last project
        switch (e.getActionCommand()) {
            //Each case gives the characters unique stats, weapons, and PNG image representation
            case "Elsie":
                fighter = "Elsie";
                weapon = "Spear";
                maxDamage = 30;
                health = 110;
                img = "/images/skeleton.png";
                break;

            case "Zach":
                fighter = "Zach";
                weapon = "Loaf of Bread";
                maxDamage = 32;
                health = 100;
                img = "images/sunglasses.png";
                break;

            case "Spencer":
                fighter = "Spencer";
                weapon = "Bow";
                maxDamage = 46;
                health = 70;
                img = "images/duck.png";
                break;

            case "Chloe":
                fighter = "Chloe";
                weapon = "Sword-whip";
                maxDamage = 36;
                health = 90;
                img = "images/raccoon.png";
                break;

            case "Casey":
                fighter = "Casey";
                weapon = "Obnoxiously Large Greatsword";
                maxDamage = 20;
                health = 160;
                img = "/images/ghost.png";
                break;

            case "Alex":
                fighter = "Alex";
                weapon = "Spiked Mace";
                maxDamage = 26;
                health = 130;
                img = "/images/space.png";
                break;

        }

        //Confirm Fighter button is there so you can click and read all the stats without actually selecting them
        if (e.getActionCommand().equals("Confirm Fighter")) {

            //The following if/else statement and Player instance to make 2 difference characters get chosen was from the help of AI (Citation at the bottom)
            Player selectedPlayer = new Player(fighter, weapon, maxDamage, health, img);

            if (!selectingSecond){
                //Sets the user's player instance
                player1 = selectedPlayer;
                selectingSecond = true;
                System.out.println(fighter + " (" + weapon + ", " + maxDamage + "d, " + health + "h) selected");

                //Tells the user their character has been selected and to choose their opponent
                textArea.setText("Player 1 selected: " + fighter + "\n\nNow choose your opponent:");
                imageLabel.setIcon(null);
                charSelectLabel.setText("Choose your Opponent:");
                charSelectLabel.repaint();
                return;
            }
            else {
                //Set's the opponent's player instance
                Player player2 = selectedPlayer;
                System.out.println(fighter + " (" + weapon + ", " + maxDamage + "d, " + health + "h) selected");
                new Battle(player1, player2);
                SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
                return;
            }
        }

        //https://stackoverflow.com/questions/8333802/displaying-an-image-in-java-swing
        //This was my resource for figuring out how to put the png into the label
        try {
            Image icon = ImageIO.read(getClass().getResource(img));
            //https://stackoverflow.com/questions/59935952/smooth-scaling-of-image-in-java
            //My reference for how to scale an image without distorting it
            Image scaled = icon.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaled));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        //Appends the name, weapon of choice, and stats to the textArea and updates
        textArea.append(e.getActionCommand() + " wields a " + weapon + ".\nMax Health: " + health + "\nMax Damage: " + maxDamage);
        textArea.revalidate();

        //Prints to terminal for checking
        System.out.println("You chose: " + e.getActionCommand());
    }

    //AI Citation 1:
    //I had no idea how to go about balancing health versus damage output so all the health and maxDamage integers were based on the output for this prompt
    //ChatGPT GPT-5.3
    /*Prompt:balance out 6 different player options, all with various health and each has different attacks.
    Those attacks are random up to a maxDamage they're allowed to deal out.
    balance it as well as possible to make all 6 options viable against another if played correctly.
    All players must have a chance at winning against the other 5.
     */

    //AI Citation 2:
    /* ChatGPT-5.3
    Goal: I already had everything working for a single player, and asked AI how to rework it to be suitable for 2 instances instead of 1.
    Prompt: I'm trying to make this where you can select a player character and your opponent, then battle. Here is my current code:

CharSelect.java:
import images.Player;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class CharSelect implements ActionListener {
    public String fighter;
    public String weapon;
    public int maxDamage;
    public int health;
    private JTextArea textArea;
    private JLabel imageLabel;
    public String img;
    public CharSelect(JFrame frame) {

        JPanel charSelectPanel = new JPanel();
        charSelectPanel.setLayout(new BorderLayout());//Changed from grid layout

        JLabel charSelectLabel = new JLabel("Choose your character:");
        charSelectLabel.setFont(new Font("Arial", Font.BOLD, 28));
        charSelectLabel.setLayout(new BorderLayout());
        charSelectLabel.setHorizontalAlignment(JLabel.CENTER);

        charSelectPanel.add(charSelectLabel, BorderLayout.NORTH);

        //Character Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 2));

        //Player Option 1
        JButton elsieButton = new JButton("Elsie");
        elsieButton.addActionListener(this);
        elsieButton.setFont(new Font("Arial", Font.BOLD, 20));
        //Player Option 2
        JButton zachButton = new JButton("Zach");
        zachButton.addActionListener(this);
        zachButton.setFont(new Font("Arial", Font.BOLD, 20));
        //Player Option 3
        JButton spencerButton = new JButton("Spencer");
        spencerButton.addActionListener(this);
        spencerButton.setFont(new Font("Arial", Font.BOLD, 20));
        //Player Option 4
        JButton chloeButton = new JButton("Chloe");
        chloeButton.addActionListener(this);
        chloeButton.setFont(new Font("Arial", Font.BOLD, 20));
        //Player Option 5
        JButton caseyButton = new JButton("Casey");
        caseyButton.addActionListener(this);
        caseyButton.setFont(new Font("Arial", Font.BOLD, 20));
        //Player Option 6
        JButton alexButton = new JButton("Alex");
        alexButton.addActionListener(this);
        alexButton.setFont(new Font("Arial", Font.BOLD, 20));

        //Adding buttons to its own separate panel
        buttonPanel.add(elsieButton);
        buttonPanel.add(zachButton);
        buttonPanel.add(spencerButton);
        buttonPanel.add(chloeButton);
        buttonPanel.add(caseyButton);
        buttonPanel.add(alexButton);

        buttonPanel.setLayout(new GridLayout(3, 2));

        //Adding button panel to character select panel
        charSelectPanel.add(buttonPanel, BorderLayout.CENTER);//BorderLayout center was here

        JPanel bottom = new JPanel();

        JPanel infoPanel = new JPanel(new BorderLayout());

        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(150, 150));
        infoPanel.add(imageLabel, BorderLayout.WEST);

        //Creating the textbox for telling the player which fighter wields what and how much damage
        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.BOLD, 18));
        textArea.setColumns(10);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);

        infoPanel.add(textArea, BorderLayout.CENTER);

        bottom.add(infoPanel, BorderLayout.CENTER);

        //Confirm character button
        JButton confirmButton = new JButton("Confirm Fighter");
        confirmButton.addActionListener(this);
        confirmButton.setFont(new Font("Arial", Font.BOLD, 18));
        bottom.add(confirmButton, BorderLayout.SOUTH);

        charSelectPanel.add(bottom, BorderLayout.SOUTH);

        frame.add(charSelectPanel);
    }
    public void actionPerformed(ActionEvent e) {
        textArea.setText("");

        switch (e.getActionCommand()) {
            case "Elsie":
                fighter = "Elsie";
                weapon = "Spear";
                maxDamage = 30;
                health = 110;
                img = "/images/skeleton.png";
                break;

            case "Zach":
                fighter = "Zach";
                weapon = "Loaf of Bread";
                maxDamage = 32;
                health = 100;
                img = "images/sunglasses.png";
                break;

            case "Spencer":
                fighter = "Spencer";
                weapon = "Bow";
                maxDamage = 46;
                health = 70;
                img = "images/duck.png";
                break;

            case "Chloe":
                fighter = "Chloe";
                weapon = "Sword-whip";
                maxDamage = 36;
                health = 90;
                img = "images/raccoon.png";
                break;

            case "Casey":
                fighter = "Casey";
                weapon = "Obnoxiously Large Greatsword";
                maxDamage = 20;
                health = 160;
                img = "/images/ghost.png";
                break;

            case "Alex":
                fighter = "Alex";
                weapon = "Spiked Mace";
                maxDamage = 26;
                health = 130;
                img = "/images/space.png";
                break;

        }
        }
        if (e.getActionCommand().equals("Confirm Fighter")) {
            Player player1 = new Player(fighter, weapon, maxDamage, health, img);
            System.out.println(fighter + " (" + weapon + ", " + maxDamage + "d, " + health + "h) selected");
            new Battle(player1);
            SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
            return;
        }

        try {
            Image icon = ImageIO.read(getClass().getResource(img));
            Image scaled = icon.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaled));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        textArea.append(e.getActionCommand() + " wields a " + weapon + ".\nMax Health: " + health + "\nMax Damage: " + maxDamage);
        textArea.revalidate();

        System.out.println("You chose: " + e.getActionCommand());
    }
Battle.java:
import images.Player;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Battle implements ActionListener {

    public Battle(Player player, Player opponent) {
        JFrame frame2 = new JFrame("Battle");
        frame2.setSize(1400, 800);
        frame2.setLocation(150, 100);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setVisible(true);

        JPanel battlePane = new JPanel();
        battlePane.setLayout(new BorderLayout());

        JPanel fighter = new JPanel();
        fighter.setLayout(new BoxLayout(fighter, BoxLayout.Y_AXIS));

        JLabel playerName = new JLabel(player.toString());
        fighter.add(playerName, BoxLayout.X_AXIS);

        JButton attack = new JButton("Attack");
        player.dealDamage(opponent);

        JButton block = new JButton("Block");

        JButton heal = new JButton("Heal");

        battlePane.add(playerName, BorderLayout.WEST);

        frame2.add(battlePane);
    }
    public void actionPerformed(ActionEvent e) {
    }
}
How do I make this where you can select two times, each go into its own separate objects, then they fight each other in the battle (having a character fight themselves is allowed)
     */
}
