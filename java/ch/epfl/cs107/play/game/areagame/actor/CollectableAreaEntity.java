package ch.epfl.cs107.play.game.areagame.actor;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public class CollectableAreaEntity extends AreaEntity{


public CollectableAreaEntity(Area area, Orientation orientation, DiscreteCoordinates position){
    super(area,orientation,position);
}


    public boolean takeCellSpace() {
        return false;
    }

    public boolean isCellInteractable() {
        return true;
    }

    public boolean isViewInteractable() {
        return false;
    }

    public void acceptInteraction(AreaInteractionVisitor v) {
        ((SuperPacmanInteractionVisitor)v).interactWith(this);

    }

    public void collect(){
        getOwnerArea().unregisterActor(this);

    }



    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }



    public void draw(Canvas canvas) {


    }
}
