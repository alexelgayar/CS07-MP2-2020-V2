package ch.epfl.cs107.play.game.superpacman.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.rpg.actor.Player;
import ch.epfl.cs107.play.game.superpacman.SuperPacman;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.Collections;
import java.util.List;

public class SuperPacmanPlayer extends Player {
    private float hp;
    private Sprite sprite;

    //Movement speed of player, duration in frame number
    private final static int SPEED = 6;


    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }

    public SuperPacmanPlayer(Area area, Orientation orientation, DiscreteCoordinates coordinates){
        super(area, orientation, coordinates);
        this.hp=5;
        sprite = new Sprite("superpacman/bonus", 1.f, 1.f, this );
        System.out.println("Constructing pacman player");
    }

    public boolean isWeak(){return (hp <= 0.f); }

    public void strengthen(){ hp = 5; }

    Orientation desiredOrientation;

    @Override
    public void update(float deltaTime) {
        Keyboard keyboard = getOwnerArea().getKeyboard();

        if(keyboard.get(Keyboard.LEFT).isDown())
                desiredOrientation = Orientation.LEFT;
        if(keyboard.get(Keyboard.RIGHT).isDown())
            desiredOrientation = Orientation.RIGHT;
        if(keyboard.get(Keyboard.DOWN).isDown())
            desiredOrientation = Orientation.DOWN;
        if(keyboard.get(Keyboard.UP).isDown())
            desiredOrientation = Orientation.UP;
        if(isDisplacementOccurs() &&
                getOwnerArea().canEnterAreaCells(this, Collections.singletonList(getCurrentMainCellCoordinates() .jump(desiredOrientation.toVector())))){
            orientate(desiredOrientation);
            move(SPEED);
        }

        super.update(deltaTime);
    }


    /**
     * Leave an area by unregister this player
     */
    public void leaveArea(){
        getOwnerArea().unregisterActor(this);
    }

    /**
     *
     * @param area (Area): initial area, not null
     * @param position (DiscreteCoordinates): initial position, not null
     */
    public void enterArea(Area area, DiscreteCoordinates position){
        area.registerActor(this);
        area.setViewCandidate(this);
        setOwnerArea(area);
        setCurrentPosition(position.toVector());
        resetMotion();
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
        //return null;
    }

    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public boolean isCellInteractable() {
        return false;
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((SuperPacmanInteractionVisitor)v).interactWith(this);
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return null;
    }

    @Override
    public boolean wantsCellInteraction() {
        return true;
    }

    @Override
    public boolean wantsViewInteraction() {
        return false;
    }

    @Override
    public void interactWith(Interactable other) {

    }

    private class SuperPacmanPlayerHandler implements SuperPacmanInteractionVisitor {
        @Override
        public void interactWith(Door door) {
            setIsPassingADoor(door);
        }
    }
}
