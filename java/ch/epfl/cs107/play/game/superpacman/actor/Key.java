package ch.epfl.cs107.play.game.superpacman.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.CollectableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Canvas;

public class Key extends CollectableAreaEntity implements Logic {

    Sprite sprite;

    private boolean isCollected = false;

    public Key(Area area, Orientation orientation, DiscreteCoordinates coords) {
        super(area, orientation, coords);

        sprite = new Sprite("superpacman/key", 1.f , 1.f, this);

    }

    /**
     * Method which sets the key status as "collected" -> Initiates signal to open gates
     */
    public void setKeyCollected(){
        isCollected = true;
    }


    @Override
    public boolean isOn() {
        return isCollected;
    }

    @Override
    public boolean isOff() {
        return false;
    }

    @Override
    public float getIntensity() {
        return 0;
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }

    public void acceptInteraction(AreaInteractionVisitor v) {
        ((SuperPacmanInteractionVisitor)v).interactWith(this);

    }
}
