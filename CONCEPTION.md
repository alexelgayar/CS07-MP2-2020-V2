
# CONCEPTION

##0a. Contents
1. Modifications to the provided code
2. Added classes and interfaces (includes extensions descriptions)
3. Deviations from the project description
4. List of all extensions

##1. Modifications to the provided code
1. ch/epfl/cs107/play/signal/logic/And.java
    -  Redefined the isOn() method from the LogicGate class in the And class, so that it only returns true if isOnly() method for both signals returns true. This was to simplify usage of isOnly()
    
2. 

##2. Added classes and interfaces (includes extension descriptions)
1. In ch/epfl/cs107/play/game/superpacman
    -   SuperPacman: This 
    -   SuperPacmanStatusGUI
2. In ch/epfl/cs107/play/game/superpacman/actor
    -   SuperPacmanPlayer: This is the class for the pacman player, which extends RPG. It is an Interactor (and interactable)
        -   SuperPacmanPlayerHandler [NESTED CLASS]: We required a player handler in order to handle interactions.
    -   Ghost: We created the super-class ghost containing basic methods such as sprite drawing, gettingNextOrientation (which were overridden), and getting viewInteractions as well as setting fieldOfView of ghosts
        -   GhostHandler[NESTED CLASS]: To manage the interactionsFromView, we required a ghost handler class
        -   Blinky: Blinky had a specific but basic behaviour, therefore we created it as a sub-class of Ghost
        -   InkyBlinky: Since Inky and Pinky shared similar methods, we decided to create a unified super-class where common methods and attributes were stored
            -   Inky: We needed to create a specific class for Inky to calculate the targetPos of it's path (specific to its behaviour)
            -   Pinky: We needed to create a specific class for Pinky to calculate the targetPos of it's path (specific to its behaviour)
    -   CollectibleAreaEntities
        -   Diamond: Class which is interactable, so pacman can collect it. Adds score boost.
        -   Cherry: Class which is interactable, so pacman can collect it. Adds score boost.
        -   Bonus: Class which is interactable, so pacman can collect it. Additionally, activates invincibility timer for pacman
        -   Key: Class which is interactable, so pacman can collect it. Additional, it uses Logic signal to unregister Gates
        -   Heart [EXTENSION]: The heart is collectable by pacman and provides +1 hp to pacman
        -   Potion [EXTENSION]: The Potion is collectable by pacman and activates speedboost timer for pacman
    -   MoveableAreaEntities
        -   SawBlade [EXTENSION]: Sawblade is a 1D moveable actor which can automatically configure its axis of motion given an orientation. It is a hostile object which kills Pacman on head on collisions. We made it into a moveableAreaEntity so that it would be interactable by Pacman.
        -   Projectile [EXTENSION]: Projectile is a 1D moveable actor which can kill pacman on head-on impact. Multiple instances of Projectile are periodically created by the Turret class extension. We made it into a moveableAreaEntity so that it would be interactable by Pacman.
    -   AreaEntity
        -   Gate (extends AreaEntity): Gate is a class which takes a cell position, preventing objects from passing beyond.
        -   Turret [EXTENSION]: Turret is an AreaEntity (non-moving) which we place on a wall. The turret is responsible for repeatedly spawning in new projectiles at intervals. It can automatically set the projectile orientation by providing the instance of this class with an initial orientation.

3. In ch/epfl/cs107/play/game/superpacman/area
    -   SuperPacmanArea
        -   Level0: Class that extends SuperPacmanArea, linked to a behaviourMap for Level0
        -   Level1: Class that extends SuperPacmanArea, linked to a behaviourMap for Level1
        -   Level2: Class that extends SuperPacmanArea, linked to a behaviourMap for Level2
    -   SuperPacmanBehaviour: Class that is required per area, which allows to register all the cells of the map and also generate an AreaGraph for the intelligent ghost pathing mechanism.
        -   SuperPacmanCell [NESTED CELL CLASS]
        -   SuperPacmanCellType [NESTED CELL CLASS]
    
4. In ch/epfl/cs107/play/game/superpacman/handler
    -   SuperPacmanInteractionVisitor (Interface): Interface where we store all the interactions (by contact) by default that are performed by the SuperPacmanPlayer
    -   GhostInteractionVisitor (Interface): Interface where we store all the interactions (by distance) by default that are performed by the Ghost instances.
    

##3. Deviations from the project description
    -   Our sole deviation from the project was that we directly included the extensions into the basic levels
    -   We believe this was absolutely necessary, as our extensions add to the game difficulty without fundamentally changing the game mechanics
    -   Had our extensions fundamentally changed the game mechanics, we would have created a separate level/game to demonstrate them, however this is not the case.
##4. List of all extensions
Please read from the class above what each extension does
    -   (Hostile Actor 1) Sawblade: We believe this sawblade could introduce an additional level of difficulty, as the player would have to time their movements to collect all the diamonds.
    -   (Utility Actor 2) Turret: Although the turret itself is not a hostile object, it repeatedly spawns projectiles. We found this turret was necessary to bring context as to how these projectiles were created. It also allowed for an linked object that could spawn in these projectiles
    -   (Hostile Actor 3) Projectile: We thought that the projectile was an advanced sawblade style mechanism, however it allowed for better usage in long straight paths (such as that in Level 2 of 2). Hence we developed the projectile.

All additional sprites for the extensions were used from Itch.io for free.