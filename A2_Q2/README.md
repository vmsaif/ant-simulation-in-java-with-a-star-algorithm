# Ant Simulation

## Logic and Design of the Simulation
This is a simple simulation where an ant is trying to find food and water. If the ant finds food, it will carry it to it's home avoiding the poisons. From the food location to the house, the and follows A* pathfinding algorithm. After returning the food at it's home, A new ant will be born at it's colony. The the initial ant will get thirsty and start looking for water and the new ant will start looking for food. An ant will die if it walks into a poison. The simulation ends when all ants die.

## State Machine

### States

The game uses a simple state machine to manage the different states of the game. The states are as follows:

- Asking the user for the number of ants to spawn : This is the initial state of the game. The user is asked to enter the number of ants to spawn. The user can enter any number between 1 and 10. If the user enters an invalid number, the game will ask the user to enter a valid number. If the user enters a valid number, the game will move to the next state.

- Spawning the ants : In this state, the game will spawn the number of ants entered by the user. The ants will be spawned at the colony. The game will move to the next state after spawning the ants. Starting the Simulation.

- Looking for food : In this state, the ants will look for food and will not look for water.  
- Searching path to home : In this state, the ants will search for a path to their home using A* pathfinding algorithm.
- Bring food to the colony : In this state, the ants will bring the food to the colony. In this state, the ants will not look for food or water.
- Looking for water : The ant will become thirsty and will start looking for water and will not look for food anymore.
- Is the ant dead? : In this state, the game will check if the ant is dead. If the ant is dead, the game will move to the next state.
- Is the simulation over? : In this state, the game will check if all the ants are dead. If all the ants are dead, the simulation is over.


## Compiling and Running

### Compiling
The game uses a simple state machine to manage the different states of the game. 

- Run the command `javac -d bin src/*.java` to compile the Java files in the src directory to bin/ directory.
- Run the command `java -cp bin/ App` to run the main game.

### Option 2: Directly Run

- Just run the A2_Q2.jar file to start.

## Resources

### Images
[Ant](https://www.pngegg.com/en/png-zblks)
[food](https://www.pngegg.com/en/png-medpx)
[poison](https://forums.episodeinteractive.com/t/green-magic-overlay/348539)
[water](https://www.freepnglogos.com/images/water-drop-11843.html)

### Bugs:
If too many ant spawns, it runs out of memory with this error:
`"AWT-EventQueue-0" java.lang.OutOfMemoryError: Java heap space`
Otherwise, everything works great.

