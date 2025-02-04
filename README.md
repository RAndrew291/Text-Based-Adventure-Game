# Text-Based Adventure Game

## Overview
**Text-Based Adventure Game** is an interactive text-based role-playing game where players explore different rooms, collect items, battle enemies, and navigate a dynamic game world. The game follows the classic *World of Zuul* style, allowing players to issue commands to move, interact with objects, and engage in combat.

## Features
- **Exploration**: Players can navigate through various rooms with unique descriptions and challenges.
- **Inventory Management**: Players can pick up, drop, and use items.
- **Combat System**: Players can engage in battles with enemies and use strategic attacks.
- **Transporter Room**: A special room that teleports players to a random location.
- **Beamer Item**: Allows players to save and teleport back to a specific room.
- **Stack-Based Navigation**: Players can backtrack through previously visited rooms.

## Technologies Used
- **Programming Language**: Java
- **Object-Oriented Design**: Utilized classes and inheritance for game mechanics.
- **Data Structures**: HashMaps for room exits, ArrayLists for inventory, Stacks for navigation history.
- **User Input Handling**: Command parser for interpreting player actions.

## Installation & Usage
### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Terminal (Linux/macOS) or Command Prompt (Windows)

### Compilation & Execution
1. Clone the repository:
   ```sh
   git clone <repository_url>
   cd text-based-adventure-game
   ```
2. Compile the program:
   ```sh
   javac *.java
   ```
3. Run the game:
   ```sh
   java Game
   ```

## Project Structure
```
text-based-adventure-game/
├── Game.java           # Main game loop and command processing
├── Room.java           # Represents game locations and navigation
├── Player.java         # Handles player attributes and actions
├── Item.java           # Implements collectible items
├── Enemy.java          # Defines enemy attributes and combat logic
├── Beamer.java         # Special teleportation item
├── TransporterRoom.java # Room that teleports players randomly
├── Command.java        # Handles player command structure
├── CommandWords.java   # Stores valid command words
├── Parser.java         # Parses user input
├── README.md          # Project documentation
```

## How to Play
- **Movement**: Use commands like `go north`, `go west` to explore rooms.
- **Item Interaction**: Use `take <item>`, `drop <item>`, `use <item>`.
- **Combat**: Attack enemies using `attack`.
- **Special Items**:
  - `charge beamer`: Saves the current room.
  - `fire beamer`: Teleports you back to the saved room.
- **Help**: Type `help` for a list of commands.
- **Quit**: Type `quit` to exit the game.

---
**Author**: Andrew Roberts
