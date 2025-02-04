import java.util.Random;

/**
 * Represents an enemy in a game with hitpoints and strength attributes.
 * Provides methods to manipulate enemy's attributes and check their status.
 * 
 * @author Andrew Roberts
 * @version March 17, 2024
 */
public class Enemy {
    private int hitpoints;
    private int strength;

    /**
     * Constructs a new enemy with 250 hitpoints and a strength of 40.
     */
    public Enemy() {
        this.hitpoints = 250;
        this.strength = 40;
    }

    /**
     * Retrieves the current hitpoints of the enemy.
     * 
     * @return The current hitpoints of the enemy.
     */
    public int getHitpoints()
    {
        return hitpoints;
    }
    
    /**
     * Reduces the enemy's hitpoints by the specified amount.
     * If the hitpoints drop below zero, they are set to zero.
     * 
     * @param damage The amount by which the enemy's hitpoints will be reduced.
     */
    public void reduceHitpoints(double damage) {
        this.hitpoints -= damage;
        if (hitpoints < 0) {
            hitpoints = 0;
        }
    }
    
    /**
     * Retrieves the strength attribute of the enemy.
     * 
     * @return The strength attribute of the enemy.
     */
    public int getStrength()
    {
        return strength;
    }
    
    /**
     * Checks if the enemy is defeated.
     * 
     * @return True if the enemy's hitpoints are zero, indicating defeat; otherwise, false.
     */
    public boolean isDefeated()
    {
        if (hitpoints == 0){
            return true;
        }
        return false;
    }
}
