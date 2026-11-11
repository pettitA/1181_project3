//Author: Alexandra Pettit
//Due: April 4th
import images.Player;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Battle implements ActionListener {

    //Player instances used from CharSelect class
    private Player player;
    private Player opponent;

    //Progress Bars for Player and Opponent and the text field that explains which characters do what
    private JProgressBar playerHP;
    private JProgressBar opponentHP;
    private JTextArea fightText;

    //All terminal printing is done to make it easier for checking the game functions properly

    /**
     * The Arena GUI to facilitate the fight
     * @param player the player1 instance selected from CharSelect
     * @param opponent the player2 or opponent instance selected from CharSelect
     */
    public Battle(Player player, Player opponent) {
        this.player = player;
        this.opponent = opponent;

        System.out.println("\n\nBattle starts");

        //Frame setup
        JFrame frame2 = new JFrame("The Arena");
        frame2.setSize(1400, 800);
        frame2.setLocation(150, 100);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setResizable(false);

        //Pane setup
        JPanel userPane = new JPanel(new BorderLayout());
        JPanel battlePane = new JPanel();
        battlePane.setLayout(new BorderLayout());

        //Scene/Arena set up
        JPanel scenePane = new JPanel(null);
        scenePane.setPreferredSize(new Dimension(1400, 500));
        //https://rgbcolorpicker.com/
        //Color picker to RGB values
        scenePane.setBackground(new Color (217, 197, 119));

        //Background set up
        JLabel arena = new JLabel();
        arena.setBounds(0, 0, 1400, 500);
        arena.setOpaque(true);
        arena.setBackground(new Color(170, 220, 120));

        //Enemy png
        //I reuse the same scaling and labeling as seen in the CharSelect class
        ImageIcon enemyPng = new ImageIcon(getClass().getResource(opponent.getImg()));
        Image enemyImg = enemyPng.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        JLabel enemy = new JLabel(new ImageIcon(enemyImg));
        enemy.setBounds(900, 80, 300, 300);

        //Player png
        ImageIcon playerPng = new ImageIcon(getClass().getResource(player.getImg()));
        Image playerImg = playerPng.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        JLabel user = new JLabel(new ImageIcon(playerImg));
        user.setBounds(100, 260, 300, 300);


        //Player name
        JLabel playerName = new JLabel(player.toString());
        playerName.setBounds(100, 200, 600, 25);
        playerName.setFont(new Font("Arial", Font.BOLD, 20));

        //https://www.geeksforgeeks.org/java/java-swing-jprogressbar/
        //This was my help for setting up the progress bars correctly
        //Player HP progress bar
        playerHP = new JProgressBar();
        playerHP.setValue(player.getHealth());
        playerHP.setBounds(100, 225, 200, 20);

        //Opponent Name setup
        JLabel opponentName = new JLabel(opponent.toString());
        opponentName.setBounds(900, 20, 200, 25);
        opponentName.setFont(new Font("Arial", Font.BOLD, 20));
        scenePane.add(opponentName);

        //Opponent HP progress bar setup
        opponentHP = new JProgressBar();
        opponentHP.setValue(opponent.getHealth());
        opponentHP.setBounds(900, 45, 200, 20);

        //Adding everything at once so I can clearly see the order
        scenePane.add(arena);
        scenePane.add(enemy);
        scenePane.add(user);
        scenePane.add(playerName);
        scenePane.add(playerHP);
        scenePane.add(opponentName);
        scenePane.add(opponentHP);

        //The arena kept breaking even though I have arena added first, so this is to make sure it is where it's supposed to be
        //https://stackoverflow.com/questions/4229638/z-order-on-java-swing-components
        //I used this and some general google searching to figure out what .setComponentZOrder() is and how to use it
        scenePane.setComponentZOrder(arena, scenePane.getComponentCount()-1);

        //The textbox that updates with every action taken
        fightText = new JTextArea("What will you do?");//Starting text
        fightText.setEditable(false);
        fightText.setFont(new Font("Arial", Font.BOLD, 18));
        fightText.setLineWrap(true);
        fightText.setWrapStyleWord(true);
        //Textbox decoration
        fightText.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        fightText.setBackground(Color.WHITE);

        //Adds the textbox to the user panel
        userPane.add(fightText,  BorderLayout.CENTER);

        //Button panel for all the actions
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout());

        //Loop and array for setting up the three action buttons
        String[] actionName = {"Attack", "Block", "Heal"};

        for (String action : actionName){
            JButton actionButton = new JButton(action);
            actionButton.setPreferredSize(new Dimension(150, 80));
            actionButton.setFont(new Font("Arial", Font.BOLD, 18));
            actionButton.addActionListener(this);
            actionPanel.add(actionButton);
        }

        //Adds the action buttons to the user's panel
        userPane.add(actionPanel,  BorderLayout.SOUTH);

        //Adding both user and scene panes to the battlePane
        battlePane.add(scenePane, BorderLayout.CENTER);
        battlePane.add(userPane, BorderLayout.SOUTH);

        //Finally adds the battle pane to the frame
        frame2.add(battlePane);

        //Tutorial dialog box that explains how the buttons work before entering The Arena
        JOptionPane.showMessageDialog(frame2, "You have entered The Arena! Now you face off against your chosen opponent!\n\n" +
                "Button Tutorial:\n" +
                "Attack: Deals a random amount of damage to your opponent, ranging from 5 to your fighter's max damage.\n" +
                "Block: Use your turn to brace yourself and halve incoming damage at the expense of your turn\n" +
                "Heal: Healing will give you any random amount of HP between 5 and 40. Be careful though! \nYou're limited to only 3" +
                "healing items!\n\n" +
                "Your goal is the be the last Friend standing. Good Luck!");

        frame2.setVisible(true);
    }

    /**
     * Calls the correct methods that are associated with each action button
     * @param e the event to be processed
     */
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        //https://www.geeksforgeeks.org/java/stringbuilder-class-in-java-with-examples/
        //String Builder reference
        StringBuilder text = new StringBuilder();

        //Similar switch to CharSelect class
        switch (action) {
            case "Attack":
                //Player deals damage to the opponent
                int damage = player.dealDamage(opponent);
                text.append("You dealt ").append(damage).append(" damage!\n");
                System.out.println("You attacked");
                break;

            case "Block":
                //Player blocks the incoming damage and halves it
                player.setBlock(true);
                text.append("You brace for impact!\n");
                System.out.println("You blocked");
                break;

            case "Heal":
                //Player tries to heal
                int healAmount = player.heal();

                //Checks if all 3 have already been used
                if (healAmount == -1) {
                    //No healing occurs
                    text.append("You ran out of healing items!\n");
                    System.out.println("You ran out of heals");
                } else {
                    //Player get HP back
                    text.append("You healed ").append(healAmount).append(" HP!\n");
                    System.out.println("You healed");
                }
                break;
        }

        //Opponent's turn
        if (opponent.isAlive()){
            //Always blocks within a certain health range
            if (opponent.getCurrHealth() > 30 && opponent.getCurrHealth() <= 50){
                opponent.setBlock(true);
                text.append(opponent.getName()).append(" blocked!\n");
                System.out.println(opponent.getName() + " blocked");
            }

            else if (opponent.getCurrHealth() <= 30){
                //Always tries to heal if health is low
                int recoverAmount = opponent.heal();
                System.out.println(opponent.getName() + " tried to heal");

                //If no more heals are left, the opponent attacks
                if (recoverAmount == -1){
                    text.append(opponent.getName()).append(" ran out of healing items!\n");
                    System.out.println(opponent.getName() + " has no more heals");

                    int damageTaken = opponent.dealDamage(player);
                    text.append(opponent.getName()).append(" attacks!\nYou took ").append(damageTaken).append(" damage");
                    System.out.println(opponent.getName() + " attacks you instead");

                    //Incoming damage is halved if the player is blocking
                    if (player.isBlocking()) {
                        text.append(" (reduced by blocking)");
                        System.out.println("You blocked");
                    }
                }
                //Opponent heals successfully if they have enough healing remaining
                else {
                    text.append(opponent.getName()).append(" healed themselves!\n");
                    System.out.println(opponent.getName() + " heals successfully");
                }
            }
            //Otherwise, opponent will always attack
            else {
                int damageTaken = opponent.dealDamage(player);
                text.append(opponent.getName()).append(" attacks!\nYou took ").append(damageTaken).append(" damage");
                System.out.println(opponent.getName() + " attacks you");

                //Checks and applies blocking reduction if player is blocking
                if (player.isBlocking()) {
                    text.append(" (reduced by blocking)");
                    System.out.println("You blocked");
                }
            }
            text.append("\n");
        }

        //Updates the health bars after every action is taken
        playerHP.setValue(player.getCurrHealth());
        opponentHP.setValue(opponent.getCurrHealth());

        //If the opponent hits 0 first, a window pops up and tells the player that they won
        //Exits the program after
        if (!opponent.isAlive()){
            JOptionPane.showMessageDialog(null, "YOU WON! CONGRATULATIONS!");
            System.out.println(opponent.getName() + " is dead now");
            System.exit(0);

        }
        //If the player hits 0 first, a window pops up and tells the player that they lost
        //Exits the program after
        if (!player.isAlive()){
            JOptionPane.showMessageDialog(null, "YOU'VE BEEN DEFEATED! GAME OVER!");
            System.out.println("You are dead now");
            System.exit(0);

        }

        fightText.setText(text.toString());




    }
}
