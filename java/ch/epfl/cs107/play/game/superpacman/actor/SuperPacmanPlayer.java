package ch.epfl.cs107.play.game.superpacman.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.*;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.rpg.actor.Player;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.game.superpacman.SuperPacman;
import ch.epfl.cs107.play.game.superpacman.SuperPacmanStatusGUI;
import ch.epfl.cs107.play.game.superpacman.area.Level1;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanBehavior;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

public class SuperPacmanPlayer extends Player {
    private int hp;
    private int score;
    private float invulnerabilityTimer;

    private Sprite sprite;
    private SuperPacmanStatusGUI gui;
    //Movement speed of player, duration in frame number
    private final static int SPEED = 5;
    private final float INVULNERABLE_TIME = 10.f;
    Sprite[][] sprites = RPGSprite.extractSprites("superpacman/pacman", 4, 1, 1,
            this, 64, 64, new Orientation[] {Orientation.DOWN, Orientation.LEFT, Orientation.UP, Orientation.RIGHT});

    Animation[] animations = Animation.createAnimations(2, sprites);

    Animation animation = animations[3];

    private SuperPacmanPlayerHandler handler;

    public int getScore(){
        return this.score;
    }

    public int getHp(){
        return this.hp;
    }

    public float getInvulnerabilityTimer(){ return this.invulnerabilityTimer; }

    public void setInvulnerabilityTimer(){ this.invulnerabilityTimer = INVULNERABLE_TIME;}

    @Override
    public void draw(Canvas canvas) {
        animation.draw(canvas);
        gui.draw(canvas);
    }

    public SuperPacmanPlayer(Area area, Orientation orientation, DiscreteCoordinates coordinates){
        super(area, orientation, coordinates);
        handler = new SuperPacmanPlayerHandler();
        this.hp=3;
        this.score=0;
        gui = new SuperPacmanStatusGUI(this);

       // resetMotion();
    }

    public boolean isWeak(){return (hp <= 0); }

    public void strengthen(){ hp = 3; }

   private Orientation desiredOrientation = Orientation.RIGHT;

    private void getOrientation(Button b, Orientation orientation){

        if(b.isDown()){
            desiredOrientation = orientation;
        }
    }

    @Override
    public void update(float deltaTime) {

        Keyboard keyboard = getOwnerArea().getKeyboard();

        getOrientation(keyboard.get(Keyboard.LEFT), Orientation.LEFT);
        getOrientation(keyboard.get(Keyboard.RIGHT), Orientation.RIGHT);
        getOrientation(keyboard.get(Keyboard.UP), Orientation.UP);
        getOrientation(keyboard.get(Keyboard.DOWN), Orientation.DOWN);

        if(isDisplacementOccurs()){
            animation = animations[getOrientation().ordinal()];
            animation.update(deltaTime);
        }
        else{animation.reset();}

        if(!isDisplacementOccurs()){
               if(getOwnerArea().canEnterAreaCells(this, Collections.singletonList(getCurrentMainCellCoordinates() .jump(desiredOrientation.toVector()))))
            {
                orientate(desiredOrientation);
            }
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

        other.acceptInteraction(handler);

    }


    private class SuperPacmanPlayerHandler implements SuperPacmanInteractionVisitor {

        @Override
        public void interactWith(Door door) {
            setIsPassingADoor(door);
        }

       public void interactWith(Cherry cherry){
            cherry.collect();
        }

        public void interactWith(Diamond diamond){
            diamond.collect();
            score += 10;

            ((SuperPacmanArea)getOwnerArea()).countDiamonds();

        }

        public void interactWith(Bonus bonus){
            bonus.collect();

        }

        public void interactWith(Key key){
            key.collect();
            key.isCollected = true;

        }
    }




}
