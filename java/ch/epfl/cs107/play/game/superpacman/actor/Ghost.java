package ch.epfl.cs107.play.game.superpacman.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaGraph;
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
import java.util.LinkedList;
import java.util.List;

public class Ghost extends MovableAreaEntity implements Interactor {

    private final static int MAX = 4;
    protected final static int FIELD_OF_VIEW_RADIUS = 5;
    protected SuperPacmanPlayer player;
    private static boolean isAfraid;
    private GhostHandler handler;
    protected final static int SPEED = 18;
    protected DiscreteCoordinates spawnPoint;

    //Sprite[][] sprites = RPGSprite.extractSprites("superpacman/ghost.blinky", 2, 1, 1,
    //        this, 64, 64, new Orientation[] {Orientation.UP, Orientation.RIGHT, Orientation.DOWN, Orientation.LEFT});


    private Sprite[] afraidSprites = RPGSprite.extractSprites("superpacman/ghost.afraid", 2, 1, 1,
            this, 16, 16);

    protected Animation afraidAnimation = new Animation(2, afraidSprites);

    protected Sprite[][] sprites;

    protected Animation[] animations;
    protected Animation animation;

    public Ghost(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        handler = new GhostHandler();
        spawnPoint = position;
        isAfraid = false;
    }

    @Override
    public void update(float deltaTime){
        super.update(deltaTime);

        if (player != null){
            DiscreteCoordinates playerPosition = new DiscreteCoordinates((int)player.getPosition().x, (int)player.getPosition().y);
            if (DiscreteCoordinates.distanceBetween(playerPosition, getCurrentMainCellCoordinates()) > FIELD_OF_VIEW_RADIUS){
                forgetPlayer();
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (!isAfraid()) {
            animation.draw(canvas);
        }
        else{
            afraidAnimation.draw(canvas);
        }
    }

    public Orientation getNextOrientation(){
        int randomInt = RandomGenerator.getInstance().nextInt(MAX);
        return Orientation.fromInt(randomInt);
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }


    //Create the field of perception here
    //Fixed radius, square field of view
    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        List<DiscreteCoordinates> fieldOfView = new LinkedList<>();

        DiscreteCoordinates ghostPosition = new DiscreteCoordinates((int)getPosition().x, (int)getPosition().y);
        for (int y = ghostPosition.y - FIELD_OF_VIEW_RADIUS; y <= ghostPosition.y + FIELD_OF_VIEW_RADIUS; ++y){
            for (int x = ghostPosition.x - FIELD_OF_VIEW_RADIUS; x <= ghostPosition.x + FIELD_OF_VIEW_RADIUS; ++x){
                fieldOfView.add(new DiscreteCoordinates(x, y));
            }
        }

        return fieldOfView;
    }

    @Override
    public boolean wantsCellInteraction() {
        return false;
    }

    //This should return true?
    @Override
    public boolean wantsViewInteraction() {
        return true; //TODO: Return field of view to get this to work
    }

    //Interactor methods
    @Override
    public void interactWith(Interactable other) {
        other.acceptInteraction(handler); //We need this => This allows interaction
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
        if(!isAfraid()){
            if(isDisplacementOccurs()) {
                animation = animations[getOrientation().ordinal()];
                animation.update(deltaTime);
            }
        }
        else{
            if(isDisplacementOccurs()) {
                afraidAnimation.update(deltaTime);
            }
        }
    }

    //When ghost is eaten by pacman => Respawn the ghost in its spawn position
    public void eatGhost(){
        player = null;

        getOwnerArea().leaveAreaCells(this, this.getEnteredCells());
        setCurrentPosition(spawnPoint.toVector());

        getOwnerArea().enterAreaCells(this, this.getCurrentCells());
        resetMotion();
    }

    public static boolean isAfraid(){
        return isAfraid;
    }

    public void scareGhost() {
        isAfraid = true;
        //System.out.println("Running ScareGhost");
    }

    public void unscareGhost(){
        isAfraid = false;
        //System.out.println("Running ScareGhost");
    }

    //Respawn all the ghosts into their spawn locations
    public void resetGhosts(){
        isAfraid = false;
        player = null; //reset the ghost memorisation of player

        getOwnerArea().leaveAreaCells(this, this.getEnteredCells());
        setCurrentPosition(spawnPoint.toVector());

        getOwnerArea().enterAreaCells(this, this.getCurrentCells());
        resetMotion();
    }

    protected void memorisePlayer(SuperPacmanPlayer player){
        this.player = player;
    }

    protected void forgetPlayer(){
        this.player = null;
    }

    private class GhostHandler implements GhostInteractionVisitor {

        //Make this interaction a fieldOfView interaction (not a contact interaction)
        public void interactWith(SuperPacmanPlayer player) {
            memorisePlayer(player);
            //System.out.println("Player has entered Field Of View of Ghost: " + player);
        }
    }
}
