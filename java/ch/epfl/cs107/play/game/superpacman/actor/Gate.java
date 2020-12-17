package ch.epfl.cs107.play.game.superpacman.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaGraph;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.game.rpg.actor.Sign;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.signal.Signal;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public class Gate extends AreaEntity {

    private Logic signal;
    private AreaGraph areaGraph;

    Sprite sprite;

    /**
     * Default AreaEntity constructor
     *
     * @param area        (Area): Owner area. Not null
     * @param orientation (Orientation): Initial orientation of the entity in the Area. Not null
     * @param position    (DiscreteCoordinate): Initial position of the entity in the Area. Not null
     */
    public Gate(Area area, Orientation orientation, Logic signal, DiscreteCoordinates position) {
        super(area, orientation, position);

        if(orientation.equals(Orientation.UP)){
            sprite = new Sprite("superpacman/gate", 1.f, 1.f, this, new RegionOfInterest(0, 0, 64, 64));
        }

        if(orientation.equals(Orientation.RIGHT)){
            sprite = new Sprite("superpacman/gate", 1.f, 1.f, this, new RegionOfInterest(0, 64, 64, 64));
        }

        this.signal = signal;
    }

    @Override
    public void update(float deltaTime) {
        if(signal.isOn()){
            getOwnerArea().unregisterActor(this);
        }

        getOwnerArea().activateNodes(getCurrentMainCellCoordinates(), signal);
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
        return false;
    }

    @Override
    public boolean isViewInteractable() {
        return false;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {

    }
}
