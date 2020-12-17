
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

# Note on Extensions
### Activating the extensions
All extensions *(Extensions 1, 2, 3, 4 & 5) are **actively used** in Level 2 and Level 3 of the game, hence they are activated by default.

Although Level 2 and 3 make up the basic levels of the Super-Pacman game, we found it was necessary to actively use our extensions in these base levels (rather than create a separate level or separate game) as they enhanced the gameplay without fundamentally altering the mechanics of the game. 

Had our extensions fundamentally changed the mechanics of the game, we would have created a new level or prepared a different game configuration.

To recap all extensions included:
1.  **(New Actor) Sawblade:** Has the ability to move left & right, or up & down. If player hits sawblade head-on, player will be killed. (However we have programmed it such that if the player does not hit the sawblade head-on, he will not die (the concept is that the sawblade *crushes* Pac-Man)).
2.  **(New Actor) Turret:** Occupies a cell-space positioned on a wall. The turret shoots deadly projectiles in the orientation it faces, which kill Pac-Man on impact.
3.  **(New Actor) Projectile:** The projectile is an actor which kills the Pac-Man on impact. If the projectile does not hit pac-man, then it disintegrates on impact with a wall.
4.  **(New Collectible Actor) HPBoost:** The Hp boost is a heart which can be collected by the Pac-Man. Upon collection, the Pac-Man gains an extra heart point.
5.  **(New Collectible Actor) SpeedBoost:** The Speed boost is a potion which can be collected by the Pac-Man. Upon collection, the Pac-Man receives a temporary speed-boost which can allow Pac-Man to evade ghosts and quickly collect diamonds.


# End of Game
The game window will automatically close when:
1. Pac-Man runs out of lives

If you reach all the way to the end of Level 3, the final gate will open and will mark the end of the game (however we have not included an "endgame" extension, hence Pac-Man will be free to roam around Level 3)
