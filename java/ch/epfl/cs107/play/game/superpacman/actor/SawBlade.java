package ch.epfl.cs107.play.game.superpacman.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Or;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public class SawBlade extends MovableAreaEntity {


    Sprite sprite = new Sprite("superpacman/sawblade", 1.f, 1.f, this);
    Orientation currentOrientation;
    /**
     * Default MovableAreaEntity constructor
     *
     * @param area        (Area): Owner area. Not null
     * @param orientation (Orientation): Initial orientation of the entity. Not null
     * @param position    (Coordinate): Initial position of the entity. Not null
     */
    public SawBlade(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        currentOrientation = orientation;
        orientate(orientation);
    }

    /**
     * Method which computes the nextOrientation for the sawblade (constrained it to 1D motion)
     * @return (Orientation): Returns the next orientation that the sawblade must do
     */
    public Orientation getNextOrientation(){

        if(currentOrientation.equals(Orientation.UP)){
                return Orientation.DOWN;
        }

        else if(currentOrientation.equals(Orientation.DOWN)){
            return Orientation.UP;
        }

        else if(currentOrientation.equals(Orientation.LEFT)){
            return Orientation.RIGHT;
        }

        else if(currentOrientation.equals(Orientation.RIGHT)){
            return Orientation.LEFT;
        }
        else return null;

    }


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        move(12);

            if(!isDisplacementOccurs())
            {
                currentOrientation = getNextOrientation();
               orientate(currentOrientation);
               move(12);
            }
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);

    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public boolean takeCellSpace() {
        return true;
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public boolean isViewInteractable() {
        return false;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((SuperPacmanInteractionVisitor)v).interactWith(this);

    }

}


