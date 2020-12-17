
# Description of the game Super Pac-Man

In this game, the player plays as a Pac-Man, a yellow blob who loves to eat. There are three levels, with the difficulty increasing with each level. The object of the game is to pass the last Gate of the last level with the highest possible score. You start with 3 lives and lose one each time you die.

# Start Game
To start the game, run the Play.java file within the project.


# Gameplay

## Controls
The only controls in the game are the four arrow keys on the keyboard, used to point the Pac-Man in the desired direction.

## Obstacles
Some obstacles will try to prevent you from winning. The first consists of ghosts. They kill you if you are in the same cell with them. They come in several forms: Blinky, Inky and Pinky.

**Blinky:** He is the red ghost, he is clumsy and stubborn as he moves randomly at each step of his motion.
**Inky:** He is a much smarter ghost, who tracks Pac-Man when Pac-man enters their field of view. He also likes to stay close to his spawn area, and flees to his spawn area when Pac-Man becomes invincible.
**Pinky:** Similar to Inky, Pinky tracks Pac-Man when Pac-Man enters their field of view. However Pinky doesn't have a preference for their spawn area, so they can move around much further and wherever they want on the map.

### Extension of Obstacles
*Note: As we believe these obstacle extensions were absolutely necessary for the basic levels, but at the same time not fundamentally changing the gameplay of Pac-Man (such that it would require a different game configuration) we decided to include the obstacle extensions into the Levels 2 and 3*

**(Extension 1) Saw Blades:** The second obstacle consists of saw blades. These have the shape of an edge with a small empty edge in it. They move either vertically or horizontally and they kill you if you are in the same cell with them.

**(Extension 2 & 3) Turrets firing Projectiles:** The third consists of the Turrets. These send deadly projectiles in one direction which will kill Pac-Man if they hit each other.

## Confront the ghosts
Even if the ghosts kill you, you can eat coins that are in the game to scare the ghosts for a period of 10 seconds. During this period the ghosts will be a blue color and it is the Pac-Man who can eat the ghosts during this period.

## Go from one level to another

To go to the next level you must remove the Gate that is blocking you. For that you must eat the corresponding keys and for levels 2 and 3 you must *also* eat all the diamonds of the level.

## Get score
Each diamond eaten adds 10 points to your score, each cherry eaten adds 200 points to your score, and each frightened ghost eaten adds 500 points to your score.

## Additional Pac-Man Boosts (Extension)
*Note: As we believe these Pac-Man boost extensions were absolutely necessary for the basic levels, but at the same time not fundamentally changing the gameplay of Pac-Man (such that it would require a different game configuration) we decided to include the obstacle extensions into the Levels 2 and 3*

### Get Extra Life Points
**(Extension 4)** To obtain life points, you can eat hearts which are in levels 2 and 3, each heart eaten adds 1 life point to you.

### Get Temporary Speed Boost
**(Extension 5)** To obtain a temporary speed boost, you can eat the potions that are located in all four corners of Level 2 and 3.

# End of Game
The game window will automatically close when:
1. Pac-Man runs out of lives

If you reach all the way to the end of Level 3, the final gate will open and will mark the end of the game (however we have not included an "endgame" extension, hence Pac-Man will be free to roam around Level 3)
