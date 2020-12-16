package ch.epfl.cs107.play.game.superpacman.handler;

import ch.epfl.cs107.play.game.areagame.actor.CollectableAreaEntity;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.handler.RPGInteractionVisitor;
import ch.epfl.cs107.play.game.superpacman.actor.*;

public interface GhostInteractionVisitor extends AreaInteractionVisitor {

    default void interactWith(SuperPacmanPlayer player){

    }

    default void interactWith(CollectableAreaEntity entity){

    }

    default void interactWith(Diamond diamond){

    }

    default void interactWith(Cherry cherry){

    }

    default void interactWith(Bonus bonus){

    }

    default void interactWith(Key key){

    }

    default void interactWith(Ghost ghost){

    }

    default void interactWith(Blinky blinky){

    }

    default void interactWith(InkyPinky inkyPinky){

    }

    default void interactWith(Inky inky){

    }

    default void interactWith(Pinky pinky){

    }
}
