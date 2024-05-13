import java.util.Stack;
import java.util.Random;
import java.util.Scanner;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery, pick up items and fight an enemy using attacks and items!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
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

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Room previousRoom;
    private Stack<Room> previousRoomStack;
    private boolean inCombat; // Flag to indicate whether the player is in combat
    private Player player; // Player object representing the game's protagonist
    private Enemy guardian; // Enemy object representing the guardian encountered in combat
    private Item[] inventory; // Array to store the player's inventory items
    private int numItems = 0; // The number of items currently in the player's inventory
    private Scanner scanner; // Scanner object for reading user input
    boolean finished = false; // Flag to indicate whether the game is finished
    private Room outside = new Room("outside the main entrance of the university");
    private Room transporterRoom = new TransporterRoom("in a mysterious transporter room, you feel as if space doesn't function correctly here..");
        
    /**
     * Constructs a new instance of the game.
     * Initializes the game's internal map, parser, player, enemy, inventory, and scanner.
     * Sets the initial state of the previous room to none, previous room stack to empty,
     * and the players inventory to an empty array of size 4.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        previousRoom = null;
        previousRoomStack = new Stack<Room>();
        player = new Player();
        guardian = new Enemy();
        inventory = new Item[4];
        scanner = new Scanner(System.in);
    }
    
    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room theatre, pub, lab, office;
       
        // create the rooms
        
        theatre = new Room("in a lecture theatre");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        
        // initialise room exits
        outside.setExit("east", theatre); 
        outside.setExit("south", lab);
        outside.setExit("west", pub); 

        theatre.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);
        lab.setExit("south", transporterRoom); //sets one of labs exits to the transporter room

        office.setExit("west", lab);
        
        // sets exit of transporter room. (added only to give the description of the exit's direction.
        // The location is not actaully used.
        transporterRoom.setExit("somewhere in space", transporterRoom);

        // initalizes items
        Item medkit = new Item("Medkit", "Restores health when used", 1.5, 20, 0);
        Item energyDrink = new Item("EnergyDrink", "Boosts strength temporarily", 0.5, 0, 10);
        Item bandage = new Item("Bandage", "Heals minor wounds", 0.2, 10, 0);
        Item proteinBar = new Item("ProteinBar", "Increases strength with regular use", 0.3, 0, 15);
        Item candy = new Item("Candy", "A sweet treat with no special effects", 0.1, 0, 0);
        Item herbalTea = new Item("HerbalTea", "Has calming and healing properties", 0.4, 15, 5);
        Item adrenalineShot = new Item("AdrenalineShot", "Provides a temporary boost in strength", 0.3, 0, 20);
        Item vitaminPills = new Item("VitaminPills", "Boosts overall health when taken regularly", 0.2, 25, 0);
        Item chocolateBar = new Item("ChocolateBar", "A delicious snack that can heal wounds", 0.3, 15, 5);
        Item firstAidSpray = new Item("FirstAidSpray", "Quickly heals moderate injuries", 1.0, 30, 0);
        Item proteinShake = new Item("ProteinShake", "Increases muscle mass and strength", 0.5, 0, 25);
        Item painkillers = new Item("Painkillers", "Reduces pain and promotes healing", 0.2, 20, 0);
        Item fruitBasket = new Item("FruitBasket", "Provides essential vitamins and nutrients", 1.0, 20, 10);
        Item healthPotion = new Item("HealthPotion", "Restores health to full", 1.5, 50, 0);
        Item strengthElixir = new Item("StrengthElixir", "Permanently increases strength when consumed", 0.5, 0, 30);
        Item energyBar = new Item("EnergyBar", "Boosts energy and stamina", 0.4, 0, 20);
        Item herbalSalve = new Item("HerbalSalve", "Heals wounds and soothes pain", 0.3, 25, 0);
        Item proteinPowder = new Item("ProteinPowder", "Builds muscle and increases strength", 0.2, 0, 25);
        Item mint = new Item("Mint", "Refreshes breath but has no other effects", 0.1, 0, 0);
        Item ginsengRoot = new Item("GinsengRoot", "Boosts energy and vitality", 0.3, 10, 15);
        Item chocolateChipCookie = new Item("ChocolateChipCookie", "A tasty treat that boosts morale", 0.2, 5, 5);
        Item healthTonic = new Item("HealthTonic", "Restores a moderate amount of health", 1.0, 30, 0);
        Item strengthTonic = new Item("StrengthTonic", "Increases strength for a short duration", 0.5, 0, 20);
        Item herbalPatch = new Item("HerbalPatch", "Heals wounds gradually over time", 0.3, 20, 0);
        
        // add items in rooms
        theatre.addItem(healthPotion);
        theatre.addItem(chocolateBar);
        theatre.addItem(proteinShake);
        theatre.addItem(strengthTonic);
        lab.addItem(medkit);
        lab.addItem(energyDrink);
        lab.addItem(proteinPowder);
        outside.addItem(herbalTea);
        outside.addItem(herbalSalve);
        outside.addItem(fruitBasket);
        office.addItem(firstAidSpray);
        office.addItem(painkillers);
        office.addItem(herbalPatch);
        office.addItem(proteinBar);
        pub.addItem(adrenalineShot);
        pub.addItem(healthTonic);
        pub.addItem(chocolateChipCookie);
        pub.addItem(strengthElixir);
        
        currentRoom = outside;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printRoomAndCarry();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * 
     * @param command The command to be processed
     * @return true If the command ends the game, false otherwise
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("look")) {
            look(command);
        }
        else if (commandWord.equals("back")) {
            back(command);
        }
        else if (commandWord.equals("stackBack")) {
            stackBack(command);
        }
        else if (commandWord.equals("drop")) {
            drop(command);
        }
        else if (commandWord.equals("take")) {
            take(command);
        }
        else if (commandWord.equals("charge")) {
            charge(command);
        }
        else if (commandWord.equals("fire")) {
            fire(command);
        }
        else if (commandWord.equals("inventory")) {
            displayInventory(command);
        }
        else if (commandWord.equals("stats")) {
            displayStats(command);
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print a cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.getCommands());
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     * If we go to a new room, update previous room and previous room stack.
     * 
     * @param command The command to be processed
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            previousRoom = currentRoom; // store the previous room
            previousRoomStack.push(currentRoom); // and add to previous room stack
            currentRoom = nextRoom;
            printRoomAndCarry();
            if ((nextRoom == transporterRoom || currentRoom == transporterRoom) && !guardian.isDefeated()) {
                if (inCombat) {
                System.out.println("You can't leave the transporter room until the Guardian is defeated.");
                return;
                } else {
                startCombat();
                return;
                }
            }
        }
    }
    
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * 
     * @param command The command to be processed
     * @return true, if this command quits the game, false otherwise
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    /** 
     * "Look" was entered. Check the rest of the command to see
     * whether we really want to look.
     * 
     * @param command The command to be processed
     */
    private void look(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Look what?");
        }
        else {
            printRoomAndCarry();
        }
    }
    
    /** 
     * "Back" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * 
     * @param command The command to be processed
     */
    private void back(Command command) 
    {
        if (inCombat) {
            System.out.println("You can't leave the transporter room until the Guardian is defeated.");
            return;
        }
        if(command.hasSecondWord()) {
            System.out.println("Back what?");
        }
        else {
            // go back to the previous room, if possible
            if (previousRoom==null) {
                System.out.println("No room to go back to.");
            } else {
                // go back and swap previous and current rooms,
                // and put current room on previous room stack
                Room temp = currentRoom;
                currentRoom = previousRoom;
                previousRoom = temp;
                previousRoomStack.push(temp);
                // and print description
                System.out.println(currentRoom.getLongDescription());
            }
        }
    }
    
    /** 
     * "StackBack" was entered. Check the rest of the command to see
     * whether we really want to stackBack.
     * 
     * @param command The command to be processed
     */
    private void stackBack(Command command) 
    {
        if (inCombat) {
            System.out.println("You can't leave the transporter room until the Guardian is defeated.");
            return;
        }
        if(command.hasSecondWord()) {
            System.out.println("StackBack what?");
        }
        else {
            // step back one room in our stack of rooms history, if possible
            if (previousRoomStack.isEmpty()) {
                System.out.println("No room to go stack back to.");
            } else {
                // current room becomes previous room, and
                // current room is taken from the top of the stack
                previousRoom = currentRoom;
                currentRoom = previousRoomStack.pop();
                // and print description
                System.out.println(currentRoom.getLongDescription());
            }
        }
    }
    
    /**
     * "Drop" was entered. If the player has the item they chose to drop, removes that item from
     * the player's inventory.
     * 
     * @param command The command to be processed
     */
    private void drop(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Drop what?");
            return;
        }
    
        String itemName = command.getSecondWord();
        Item itemToDrop = null;
    
        // Search for the item in the player's inventory
        for (Item item : inventory) {
            if (item != null && item.getName().equalsIgnoreCase(itemName)) {
                itemToDrop = item;
                break;
            }
        }
    
        if (itemToDrop != null) {
            // Add the item to the current room
            currentRoom.addItem(itemToDrop);
            // Remove the item from the player's inventory
            removeItemFromInventory(itemToDrop);
            System.out.println("You have dropped " + itemName + ".");
        } else {
            System.out.println("You don't have a " + itemName + " to drop.");
        }
    }
    
    /**
     * Helper method to remove an item from the player's inventory.
     *
     * @param itemToRemove The item to be removed from the inventory.
     */
    private void removeItemFromInventory(Item itemToRemove) {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null && inventory[i].equals(itemToRemove)) {
                inventory[i] = null;
                numItems--;
                break;
            }
        }
    }

    /**
     * "Take" was entered. If a player's inventory is not full, they can pick up an item in the 
     * room, granted its in the room, specified by its name.
     * 
     * @param command The command to be processed.
     */
    private void take(Command command) {
        if (command.hasSecondWord()) {
            String itemName = command.getSecondWord();
    
            Item item = currentRoom.removeItem(itemName);
    
            if (item != null) {
                boolean addedToInventory = addToInventory(item);
                if (addedToInventory) {
                    System.out.println("You picked up " + itemName + ".");
                } else {
                    System.out.println("Your inventory is full.");
                    // add item back to the room
                    currentRoom.addItem(item);
                }
            } else {
                System.out.println("That item is not in the room.");
            }
        } else {
            System.out.println("Take what?");
        }
    }
    
    /**
     * Helper method to add an item to the player's inventory.
     *
     * @param itemToAdd The item to be added to the inventory.
     * @return True if the item was successfully added, false if the inventory is full.
     */
    private boolean addToInventory(Item itemToAdd) {
        for (int i = 0; i < 4; i++) {
            if (inventory[i] == null) {
                inventory[i] = itemToAdd;
                numItems++;
                return true;
            }
        }
        return false; // Inventory is full
    }

    /**
     * "Charge" was entered. If the player is carrying a beamer and it is not already charged,
     * charge the beamer.
     * 
     * @param command The command to be processed
     */
    private void charge(Command command) {
        Beamer beamer = findBeamer();
        if (beamer != null) {
            if (!beamer.isCharged()) {
                beamer.charge(currentRoom);
            } else {
                System.out.println("The beamer is already charged. Fire before charging again.");
            }
        } else {
            System.out.println("You are not carrying a beamer.");
        }
    }
    
    /**
     * "Fire" was entered. If the player is carrying a beamer and it is charged, fire the beamer,
     * taking the player back to the room the player charged it in.
     * 
     * @param command The command to be processed
     */
    private void fire(Command command) {
        Beamer beamer = findBeamer();
        if (beamer != null) {
            if (beamer.isCharged()) {
                previousRoom = currentRoom;
                previousRoomStack.push(currentRoom);
                currentRoom = beamer.fire();
                System.out.println("You are transported back " + currentRoom.getShortDescription() + ".");
                System.out.println(currentRoom.getLongDescription());
            } else {
                System.out.println("The beamer must be charged before firing.");
            }
        } else {
            System.out.println("You are not carrying a beamer.");
        }
    }
    
    /**
     * Helper method to find the Beamer item in the player's inventory.
     * 
     * @return The Beamer item if found, or null if not found
     */
    private Beamer findBeamer() {
        for (Item item : inventory) {
            if (item instanceof Beamer) {
                return (Beamer) item;
            }
        }
        return null; // Beamer not found in inventory
    }
    
    /**
     * Method for printing the room long description and imformation of what item the player is
     * holding, if any
     */
    private void printRoomAndCarry() {
        // Output the long description of this room
        System.out.println(currentRoom.getLongDescription());
    
        // Check if the player has any items in their inventory
        if (numItems > 0) {
            System.out.println("You are carrying:");
            System.out.println("Inventory:");
            for (Item item : inventory) {
                if (item != null){
                System.out.println(item.getName());
                }
            } 
        }
        else {
            System.out.println("You are not carrying anything.");
        
        }
    }
    
    /**
     * Initiates an attack by the player on the enemy (Guardian).
     * Calculates damage based on player's strength and applies it to the enemy.
     */
    private void playerAttack() {
        // Calculate base damage using player's strength
        double baseDamage = player.getStrength() * 0.8;
    
        // Implement attack logic here
        Random rand = new Random();
        int critChance = rand.nextInt(16); // 1/16 chance to crit
        if (critChance == 0) {
            // Deal increased damage on crit
            double critDamage = player.getStrength() * 1.5;
            System.out.println("Critical hit!\nYou dealt " + critDamage + " to the guardian!");
            guardian.reduceHitpoints(critDamage);
        } else {
            System.out.println("You dealt " + baseDamage + " to the guardian!");
            guardian.reduceHitpoints(baseDamage);
        }
    }

    /**
     * Determines the effects of using an item.
     * The effect depends on the type of item used.
     *
     * @param itemName The name of the item to be used.
     */
    private void itemEffects(String itemName) {
        switch (itemName.toLowerCase()) {
            case "medkit":
                player.restoreHealth(20);
                System.out.println("You used the " + itemName + ". Health restored.");
                break;
            case "energydrink":
                player.boostStrength(10);
                System.out.println("You drank the " + itemName + ". Strength boosted temporarily.");
                break;
            case "bandage":
                player.restoreHealth(10);
                System.out.println("You used the " + itemName + ". Minor wounds healed.");
                break;
            case "proteinbar":
                player.boostStrength(15);
                System.out.println("You ate the " + itemName + ". Strength increased.");
                break;
            case "candy":
                System.out.println("You ate the " + itemName + ". It tastes sweet, but has no special effects.");
                break;
            case "herbaltea":
                player.restoreHealth(15);
                player.boostStrength(5);
                System.out.println("You drank the " + itemName + ". Feeling calm and healed.");
                break;
            case "adrenalineshot":
                player.boostStrength(20);
                System.out.println("You injected the " + itemName + ". Adrenaline rush! Strength boosted.");
                break;
            case "vitaminpills":
                player.restoreHealth(25);
                System.out.println("You took the " + itemName + ". Overall health boosted.");
                break;
            case "chocolatebar":
                player.restoreHealth(15);
                player.boostStrength(5);
                System.out.println("You ate the " + itemName + ". Delicious and healing.");
                break;
            case "firstaidspray":
                player.restoreHealth(30);
                System.out.println("You sprayed the " + itemName + ". Moderate injuries healed.");
                break;
            case "proteinshake":
                player.boostStrength(25);
                System.out.println("You drank the " + itemName + ". Muscle mass and strength increased.");
                break;
            case "painkillers":
                player.restoreHealth(20);
                System.out.println("You took the " + itemName + ". Pain reduced, healing promoted.");
                break;
            case "fruitbasket":
                player.restoreHealth(20);
                player.boostStrength(10);
                System.out.println("You enjoyed the " + itemName + ". Vitality restored.");
                break;
            case "healthpotion":
                player.restoreHealth(50);
                System.out.println("You drank the " + itemName + ". Health fully restored.");
                break;
            case "strengthelixir":
                player.boostStrength(30);
                System.out.println("You consumed the " + itemName + ". Strength permanently increased.");
                break;
            case "energy bar":
                player.boostStrength(20);
                System.out.println("You ate the " + itemName + ". Energy and stamina boosted.");
                break;
            case "herbalsalve":
                player.restoreHealth(25);
                System.out.println("You applied the " + itemName + ". Wounds healed, pain soothed.");
                break;
            case "proteinpowder":
                player.boostStrength(25);
                System.out.println("You consumed the " + itemName + ". Muscles bulked up, strength increased.");
                break;
            case "mint":
                System.out.println("You chewed the " + itemName + ". Breath refreshed, but no other effects.");
                break;
            case "ginsengroot":
                player.restoreHealth(10);
                player.boostStrength(15);
                System.out.println("You ingested the " + itemName + ". Energy and vitality increased.");
                break;
            case "chocolatechipcookie":
                player.restoreHealth(5);
                player.boostStrength(5);
                System.out.println("You ate the " + itemName + ". Morale boosted.");
                break;
            case "healthtonic":
                player.restoreHealth(30);
                System.out.println("You drank the " + itemName + ". Moderate health restored.");
                break;
            case "strengthtonic":
                player.boostStrength(20);
                System.out.println("You consumed the " + itemName + ". Strength boosted for a short duration.");
                break;
            case "herbalpatch":
                player.restoreHealth(20);
                System.out.println("You applied the " + itemName + ". Wounds healing gradually.");
                break;
            default:
                System.out.println("You can't use the " + itemName + " right now.");
                break;
        }
    }
    
    /**
     * Displays the items currently present in the player's inventory.
     */        
    private void displayInventory(Command command) {
        System.out.println("Inventory:");
        for (Item item : inventory) {
            if (item != null){
                System.out.println(item.getName());
            }
        }
    }
    
    /**
     * Displays the current hitpoints and strength of the player.
     */
    private void displayStats(Command command) {
        System.out.println("Player Stats:");
        System.out.println("HP: " + player.getHitpoints());
        System.out.println("Strength: " + player.getStrength());
    }
    
    /**
     * Initiates combat with the enemy (Guardian).
     * Alternates between player's and enemy's turns until one is defeated.
     */
    private void startCombat() {
        System.out.println("You encounter the Guardian!");
        inCombat = true;
    
        // Start the combat loop
        while (inCombat) {
            // Player's turn
            playerTurn();
            // Check if the Guardian is defeated
            if (guardian.isDefeated()) {
                endCombat(true); // Player wins
                break;
            }
            // Guardian's turn
            guardianTurn();
            // Check if the player is defeated
            if (player.isDefeated()) {
                endCombat(false); // Player loses
                break;
            }
        }
    }
    
    /**
     * Executes the player's turn during combat.
     * Allows the player to choose between attacking, using an item, or quitting the game.
     */
    private void playerTurn() {
        // Display player and Guardian hitpoints
        displayCombatStatus();
        
        System.out.println("It's your turn. What would you like to do? (Enter 1 or 2 to make your choice)");
        System.out.println("1. Attack\n2. Use Item\n3. Quit Game");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1:
                playerAttack();
                break;
            case 2:
                useItem();
                break;
            case 3:
                finished = true;
                break;
            default:
                System.out.println("Invalid choice. Please choose 1 or 2.");
                break;
                
        }
    }
    
    /**
     * Allows the player to use an item during combat.
     * Displays the player's inventory and prompts the player to select an item to use.
     */
    private void useItem() {
        // Check if the player has any items
        if (numItems == 0) {
            System.out.println("You don't have any items to use.");
            return;
        }
    
        // Display the player's inventory
        System.out.println("Select an item to use: (Enter a number 1-4 to make your choice)");
        for (int i = 0; i < numItems; i++) {
            if (inventory[i] != null){
                System.out.println((i + 1) + ". " + inventory[i].getName());
            }
        }
    
        // Read user input
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character
        
        // Validate the choice
        if (choice < 1 || choice > numItems) {
            System.out.println("Invalid choice.");
            return;
        }
    
        // Use the selected item
        String selectedItem = inventory[choice - 1].getName();
        itemEffects(selectedItem);
    }
    
    /**
     * Executes the enemy's (Guardian's) turn during combat.
     * Calculates damage based on the Guardian's strength and applies it to the player.
     */
    private void guardianTurn() {
        // Calculate base damage using guardian's strength
        double baseDamage = guardian.getStrength() * 0.5;
    
        // Implement attack logic here
        Random rand1 = new Random();
        Random rand2 = new Random();
        int critChance = rand1.nextInt(16); // 1/16 chance to crit
        int missChance = rand2.nextInt(6); //1/6 chance to miss
        if (missChance == 0) {
            System.out.println("The Guardian's attack missed!");
            return;
        }
        if (critChance == 0) {
            // Deal increased damage on crit
            double critDamage = player.getStrength() * 1.2;
            System.out.println("Critical hit!\nYou took " + critDamage + " damage from the Guardian!");
            player.reduceHitpoints(critDamage);
        } else {
            System.out.println("You took " + baseDamage + " damage from the Guardian!");
            player.reduceHitpoints(baseDamage);
        }
    }
    
    /**
     * Displays the current hitpoints of both the player and the enemy during combat as well as
     * the strength stat of the player.
     */
    private void displayCombatStatus() {
        System.out.println("Player hitpoints: " + player.getHitpoints());
        System.out.println("Player Strength: " + player.getStrength());
        System.out.println("Guardian hitpoints: " + guardian.getHitpoints());
    }
    
    /**
     * Ends combat based on the outcome (player wins or loses).
     * Resets player's status or makes transporter room items available accordingly.
     *
     * @param playerWins True if the player wins the combat, false otherwise.
     */
    private void endCombat(boolean playerWins) {
        if (playerWins) {
            System.out.println("You defeated the Guardian!");
            // Make transporter room items available
            transporterRoomItemsAvailable();
        } else {
            System.out.println("You were defeated by the Guardian...");
            // Reset player's status and return to outside room
            playerFaints();
        }
        // Allow player to leave transporter room
        inCombat = false;
    }
    
    /**
     * Makes transporter room items available after defeating the Guardian.
     * Adds the Beamer item to the transporter room.
     */
    private void transporterRoomItemsAvailable() {
        Beamer beamer = new Beamer("Beamer", "capable of saving a room and transporting you back to it when fired", 4.5, 0, 0);
        transporterRoom.addItem(beamer);
        System.out.println(transporterRoom.getLongDescription());
    }
    
    /**
     * Drops all items, resets player's stats, and returns the player to the outside room after
     * fainting in combat.
     */
    private void playerFaints() {
        for (int i = 0; i < numItems; i++){
            inventory[i] = null;
        }
        player.resetStats();
        previousRoom = null;
        currentRoom = outside; 
        System.out.println("You wake up outside in a daze, you notice you dropped your items in your fight with the Guardian.");
        printRoomAndCarry();
    }
}