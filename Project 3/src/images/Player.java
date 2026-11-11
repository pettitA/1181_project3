//Author: Alexandra Pettit
//Due: April 4th
package images;

/**
 * Creates new Player objects to hold the values chosen during the character selection phase
 */
//This entire class is loosely based on my Project 4 (Catacomb Crawler) "Player" class
public class Player {
    //Character attributes
    private String name;
    private String weapon;
    private int maxDamage;
    private int health;
    private int currHealth;
    private String img;

    //Game mechanics
    private boolean blocking = false;
    private int healsUsed = 0;
    private int maxHeals = 3;

    //My damage was initially wrong and I thought my math was wrong, only to realize the maxDamage and health pools were swapped :/

    /**
     * Constructor for the Player objects used in the Battle class
     * @param name name of the character
     * @param weapon the unique weapon of choice
     * @param maxDamage the most damage that can be dealt in a single attack
     * @param health the starting health of the character
     * @param img the png that represents the character
     */
    public Player (String name, String weapon, int maxDamage, int health, String img){
        this.name = name;
        this.weapon = weapon;
        this.health = health;
        this.maxDamage = maxDamage;
        this.currHealth = health;
        this.img = img;

    }

    /**
     * Sets the value of blocking
     * @param blocking boolean for if the character blocks or not
     */
    public void setBlock(boolean blocking){
        this.blocking = blocking;

    }

    /**
     * Checks if the character is currently blocking
     * @return true if they are, false if they are not
     */
    public boolean isBlocking(){
        return blocking;
    }

    /**
     * Retrieves the name of the character
     * @return the name of the character as a string
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the max amount of damage a character can do
     * @return the int value of the max amount of damage
     */
    public int getMaxDamage(){
        return maxDamage;
    }

    /**
     * Retrieves the starting amount of health a character has
     * @return the int value of the starting health
     */
    public int getHealth(){
        return health;
    }

    /**
     * Retrieves the current health of the character
     * @return the int value of their updated health after actions (attacking, blocking, healing) have been applied
     */
    public int getCurrHealth(){
        return currHealth;
    }

    /**
     * Retrieves the string with the character's image filename
     * @return string value with the path to the filename
     */
    public String getImg(){
        return img;
    }

    /**
     * Calculates the damage dealt to the character
     * @param damage the amount of health the character will be taking
     * @return the total amount of damage a single attack has
     */
    public int takeDamage(int damage){
        //Halves the damage taken
        if (blocking){
            damage = damage / 2;
            blocking = false;
        }

        //Loses health from current health pool
        currHealth -= damage;

        //Set's the health to 0 even if an attack deals enough damage to put them in the negatives
        if (currHealth <= 0){
            currHealth = 0;
        }

        return damage;
    }

    /**
     * Calculates how much damage the character will deal to another (5-max damage)
     * @param other the character that will be receiving the damage
     * @return the amount of damage the other character takes
     */
    public int dealDamage(Player other) {
        //Minimum of 5 damage and a maximum of the character's max damage int
        int minDamage = 5;
        int randomDamage = (int)(Math.random() * (getMaxDamage() - minDamage + 1)) + minDamage; //Determines the range of the random amount of damage
        return other.takeDamage(randomDamage);
    }

    /**
     * Calculates the random amount of healing a character receives (5-40)
     * @return the amount of healing received
     */
    public int heal(){
        //Checks if all 3 healing item's have been used. Returns -1 if they have
        if (healsUsed >= maxHeals){
            return -1;
        }

        //Random amount of healing within range
        int healing = (int) (Math.random() * 36) + 5;
        //Adds healing to current health pool
        currHealth += healing;

        //Maxes out healing at the starting health so you can't over-heal
        if  (currHealth > health){
            currHealth = health;
        }

        //Tracker for how many times the character's healed
        healsUsed++;
        return healing;
    }

    /**
     * Checks if the character's health is still above 0
     * @return true if they are, false if they're not
     */
    public boolean isAlive(){
        return currHealth > 0;
    }

    /**
     * Prints the name of the character and their weapon
     * @return outputs the name and weapon tied to the character
     */
    @Override
    public String toString(){
        return name + " (" + weapon + ")";
    }
}
