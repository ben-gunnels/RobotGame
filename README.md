# Scrap-E: A Scrap Metal Collection Game
A simple command-line robot game in Java

![Scrap-E Cartoon](/craiyon_144920_Rusty_futuristic_metal_cute_cartoon_robot_with_boxy_features_and_smile.png)
*Credit craiyon.com*
# To Run
Compile the RobotGame.java file in your command line:
```
javac RobotGame.java
```
Run the public class file:
```
java RobotGame
```

As instructed, enter your desired difficulty (easy, medium, hard).

This will initialize your health to start the game.

Then, when prompted, set your desired score that you would like to play to (between 1-25). 

The game will then initialize the board randomly and you will be prompted to make a move.

Enter your desired move (left, right, up, down) in full and lowercase.

Try to avoid the bombs (2) and collect the scrap metal (1). 

When the game is finished the command line will tell you your final score and how much damage you received.

# Game Rules
* The player exists as a robot in the center of the board.
* The player can choose to move either right, left, up, or down.
* The goal is to travel on 0's (Walkway) to collect 1's (Scrap Metal) and avoid 2's (Bombs).
* The player starts out with a limited amount of health depending on the difficulty.
* If a player consumes metal his score increases by one.
* If a player consumes a bomb his health decreases by one.
* If the player's health drops to 0 he is out of the game.
* If the player collects enough Scrap Metal he wins the game.

### Key
* bomb = 2
* scrap metal = 1
* walkway = 0
