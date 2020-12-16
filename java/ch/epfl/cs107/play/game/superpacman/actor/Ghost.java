package ch.epfl.cs107.play.game.superpacman.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.*;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.game.superpacman.SuperPacman;
import ch.epfl.cs107.play.game.superpacman.handler.GhostInteractionVisitor;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public class Ghost extends MovableAreaEntity implements Interactor {

    private final static int MAX = 4;

    protected SuperPacmanPlayer player;
    private static boolean isAfraid;
    private GhostHandler handler;
    private DiscreteCoordinates spawnPoint;

    //Sprite[][] sprites = RPGSprite.extractSprites("superpacman/ghost.blinky", 2, 1, 1,
    //        this, 64, 64, new Orientation[] {Orientation.UP, Orientation.RIGHT, Orientation.DOWN, Orientation.LEFT});


    private Sprite[] afraidSprites = RPGSprite.extractSprites("superpacman/ghost.afraid", 2, 1, 1,
            this, 16, 16);

    protected Animation afraidAnimation = new Animation(2, afraidSprites);

    /**
     * Default MovableAreaEntity constructor
     *
     * @param area        (Area): Owner area. Not null
     * @param orientation (Orientation): Initial orientation of the entity. Not null
     * @param position    (Coordinate): Initial position of the entity. Not null
     */
    public Ghost(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        handler = new GhostHandler();
        spawnPoint = position;
        isAfraid = false;
    }

    @Override
    public void update(float deltaTime){
        super.update(deltaTime);

        //getNextOrientation();


    }

    public Orientation getNextOrientation(){
        int randomInt = RandomGenerator.getInstance().nextInt(MAX);
        return Orientation.fromInt(randomInt);
    }

    @Override
    public void draw(Canvas canvas) {
        if (isAfraid()) {
            afraidAnimation.draw(canvas);
        }
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }


    //Interactor methods
    //Here, you will get the position of the pacman?
    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return null;
    }

    @Override
    public boolean wantsCellInteraction() {
        return false;
    }

    //This should return true?
    @Override
    public boolean wantsViewInteraction() {
        return false;
    }

    //Interactor methods
    @Override
    public void interactWith(Interactable other) {

    }

    //MoveableAreaEntitiy methods
    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public boolean isViewInteractable() {
        return false;
    }

    //Handle Interaction with Pacman
    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((SuperPacmanInteractionVisitor)v).interactWith(this);
    }

    public void startAnimation(float deltaTime){
        //Start the animation of the ghosts
    }

    //When ghost is eaten by pacman => Respawn the ghost in its spawn position
    public void eatGhost(){
        getOwnerArea().leaveAreaCells(this, this.getEnteredCells());
        setCurrentPosition(spawnPoint.toVector());

        getOwnerArea().enterAreaCells(this, this.getCurrentCells());
        resetMotion();
    }

    public static boolean isAfraid(){
        return isAfraid;
    }

    public static void scareGhosts() {
        isAfraid = true;
    }

    public static void unscareGhosts(){
        isAfraid = false;
    }

    //Respawn all the ghosts into their spawn locations
    public void resetGhosts(){
        isAfraid = false;

        getOwnerArea().leaveAreaCells(this, this.getEnteredCells());
        setCurrentPosition(spawnPoint.toVector());

        getOwnerArea().enterAreaCells(this, this.getCurrentCells());
        resetMotion();
    }

    public void memorisePlayer(SuperPacmanPlayer player){
        this.player = player;
    }

    private class GhostHandler implements GhostInteractionVisitor {

        //Make this interaction a fieldOfView interaction (not a contact interaction)
        public void interactWith(SuperPacmanPlayer player) {
            memorisePlayer(player);
        }
    }
}
