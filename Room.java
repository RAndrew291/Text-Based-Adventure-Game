import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList; // or java.util.*; and replace the above

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 * 
 * @author Lynn Marshall 
 * @version A1 Solution
 * 
 * @author Andrew Roberts
 * @version March 17, 2024
 */

public class Room 
{
    private String description;
    protected HashMap<String, Room> exits;        // stores exits of this room.

    // the items in this room
    protected ArrayList<Item> items;
    
    // stores all the rooms in the game
    protected static ArrayList<Room> allRooms = new ArrayList<>();
    
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * 
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<String, Room>();
        items = new ArrayList<Item>();
        allRooms.add(this);
    }
    
    /**
     * Add an item to the room, best to check that it's not null.
     * 
     * @param item The item to add to the room
     */
    public void addItem(Item item) 
    {
        if (item!=null) { // not required, but good practice
            items.add(item);
        }
    }

    /**
     * Define an exit from this room.
     * 
     * @param direction The direction of the exit
     * @param neighbour The room to which the exit leads
     */
    public void setExit(String direction, Room neighbour) 
    {
        exits.put(direction, neighbour);
    }

    /**
     * Returns a short description of the room, i.e. the one that
     * was defined in the constructor
     * 
     * @return The short description of the room
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a long description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     *     Items: 
     *        a chair weighing 5 kgs.
     *        a table weighing 10 kgs.
     *     
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString()
            + "\nItems:" + getItems();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * 
     * @return Details of the room's exits
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * 
     * @param direction The exit's direction
     * @return The room in the given direction
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    /**
     * Return a String representing the items in the room, one per line.
     * 
     * @return A String of the items, one per line
     */
    public String getItems() 
    {
        // let's use a StringBuilder (not required)
        StringBuilder s = new StringBuilder();
        for (Item i : items) {
            s.append("\n    " + i.getDescription());
        }
        return s.toString(); 
    }
    
    /**
     * Select a item in the room specified by its name.
     * 
     * @param itemName The name of the item
     * @return The specifed item, null if it does not exist
     */
    public Item getItem(String itemName)
    {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)){
                return item;
            }
        }
        return null; // item not in room
    }
    
    /**
     * Removes the specified item from the room.
     * 
     * @param itemName the name of the item to be removed
     * @return The room that was removed
     */
    public Item removeItem(String itemName)
    {
        Item removedItem = null;
        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()){
            Item item = iterator.next();
            if (item.getName().equalsIgnoreCase(itemName)) {
                removedItem = item;
                iterator.remove();
                break;
            }
        }
        return removedItem;
    }
    
    /**
     * Returns the ArrayList of Rooms
     * 
     * @return The ArrayList of Rooms in the game
     */
    public static ArrayList<Room> getAllRooms() 
    {
        return allRooms;
    }
}

