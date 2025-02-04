import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Represents a player in the game with hitpoints and strength attributes.
 * Provides methods to manipulate player's attributes and check their status.
 * 
 * @author Andrew Roberts
 * @version March 17, 2024
 */
public class Player {
    private int hitpoints;
    public static final int MAX_HITPOINTS = 100;
    private int strength;
    
    /**
     * Constructs a new player with maximum hitpoints and a default strength of 30.
     */
    public Player() 
    {
        this.hitpoints = MAX_HITPOINTS;
        this.strength = 30;
    }
    
    /**
     * Retrieves the current hitpoints of the player.
     * 
     * @return The current hitpoints of the player.
     */
    public int getHitpoints()
    {
        return hitpoints;
    }
    
    /**
     * Resets the player's hitpoints and strength to their maximum values.
     */
    public void resetStats()
    {
        hitpoints = MAX_HITPOINTS;
        strength = 30;
    }
    
    /**
     * Restores the player's hitpoints by the specified amount, up to the maximum hitpoints.
     * 
     * @param healthBoost The amount by which the player's hitpoints will be increased.
     */
    public void restoreHealth(int healthBoost)
    {
        hitpoints += healthBoost;
        if (hitpoints > MAX_HITPOINTS){
            hitpoints = MAX_HITPOINTS;
        }
    }
    
    /**
     * Reduces the player's hitpoints by the specified amount.
     * If the hitpoints drop below zero, they are set to zero.
     * 
     * @param amount The amount by which the player's hitpoints will be reduced.
     */
    public void reduceHitpoints(double amount) 
    {
        hitpoints -= amount;
        if (hitpoints < 0) {
            hitpoints = 0;
        }
    }

    /**
     * Retrieves the strength attribute of the player.
     * 
     * @return The strength attribute of the player.
     */
    public int getStrength() 
    {
        return strength;
    }
    
    /**
     * Boosts the player's strength by the specified amount.
     * 
     * @param strengthBoost The amount by which the player's strength will be increased.
     */
    public void boostStrength(int strengthBoost)
    {
        strength += strengthBoost;
    }
    
    /**
     * Checks if the player is defeated.
     * 
     * @return True if the player's hitpoints are zero, indicating defeat. otherwise, false.
     */
    public boolean isDefeated()
    {
        if (hitpoints == 0){
            return true;
        }
        return false;
    }
}

