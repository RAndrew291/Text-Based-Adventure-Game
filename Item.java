
/**
 * This class represents an item which may be put
 * in a room in the game of Zuul.
 * 
 * @author Lynn Marshall 
 * @version A1 Solution
 * 
 * @author Andrew Roberts
 * @version March 17, 2024
 */
public class Item
{
    // description of the item
    private String description;
    
    // name of the item
    private String name;
    
    // weight of the item in kilgrams 
    private double weight;
    
    private int healingEffect;
    private int strengthEffect;

    /**
     * Constructor for objects of class Item.
     * 
     * @param description The description of the item
     * @param name The name of the item
     * @param weight The weight of the item
     */
    public Item(String name, String description, double weight, int healingEffect, int strengthEffect)
    {
        this.description = description;
        this.name = name;
        this.weight = weight;
        this.healingEffect = healingEffect;
        this.strengthEffect = strengthEffect;
    }

    /**
     * Returns a description of the item, including its
     * name, description and weight.
     * 
     * @return  A description of the item
     */
    public String getDescription()
    {
        return name + " that " + description + " that weighs " + weight + "kg. It heals for " + healingEffect + " points and boosts strength by " + strengthEffect + " points.";
    }
    
    /**
     * Returns the name of the item.
     * 
     * @return the item's name.
     */
    public String getName()
    {
        return name;
    }
    
    public int getHealingEffect() {
        return healingEffect;
    }
    
    public int getStrengthEffect() {
        return strengthEffect;
    }
}
