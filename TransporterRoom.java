import java.util.Random;
import java.util.ArrayList;

/**
 * A room that does not have set exits and will take the player to a random room when go command is
 * used in it
 *
 * @author Andrew Roberts
 * @version March 17, 2024
 */
public class TransporterRoom extends Room
{
    private static Random random;
    
    /**
     * Constructs a TransporterRoom with the given description.
     *
     * @param description The description of the TransporterRoom
     */
    public TransporterRoom(String description) {
        super(description);
    }
    
    /**
     * Overrides the getExit method to return a random room,independent of the direction parameter.
     *
     * @param direction not used in getting the exit
     * @return A randomly selected room
     */
    @Override
    public Room getExit(String direction) {
        return findRandomRoom();
    }
    
    /**
     * Chooses a random room.
     *
     * @return The room the player moves to
     */
    private Room findRandomRoom() {
        random = new Random();
        ArrayList<Room> allRooms = getAllRooms();
        int index = random.nextInt(allRooms.size());
        return allRooms.get(index);
    }
}
