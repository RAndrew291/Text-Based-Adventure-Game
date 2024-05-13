
/**
 * A beamer is a device that can be charged and fired. When you charge the beamer, it memorizes 
 * the current room. When you fire the beamer, it transports you immediately back to the room it 
 * was charged in.
 *
 * @author Andrew Roberts
 * @version March 17, 2024
 */
public class Beamer extends Item
{
    private boolean charged;
    private Room savedRoom;
    
    /**
     * Constructs a Beamer object with the given description and weight.
     * Initially, the Beamer is not charged.
     * 
     * @param description The description of the Beamer
     * @param name The name of the item
     * @param weight The weight of the Beamer
     */
    public Beamer(String description, String name, double weight, int healingEffect, int strengthEffect)
    {
        super(description, name, weight, healingEffect, strengthEffect);
        this.charged = false;
    }
    
    /**
     * Sets the Beamer as charged.
     * 
     * @param currentRoom the room then player is currently in/the room to be saved
     */
    public void charge(Room currentRoom)
    {
        charged = true;
        savedRoom = currentRoom;
        System.out.println("Beamer charged. Current room memorized.");
    }
    
    /**
     * Checks if the Beamer is charged.
     * 
     * @return true if the Beamer is charged, false otherwise
     */
    public boolean isCharged()
    {
        return charged;
    }
    
    /**
     * Fires the beamer to retrieve the room saved by the beamers charge and resets its charge.
     * 
     * @return The room that was saved by the charge.
     */
    public Room fire()
    {
        Room temp = savedRoom;
        charged = false;
        savedRoom = null;
        return temp;
    }
}
