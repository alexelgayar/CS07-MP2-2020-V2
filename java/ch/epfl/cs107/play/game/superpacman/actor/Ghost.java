package ch.epfl.cs107.play.game.superpacman.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.game.superpacman.SuperPacman;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public class Ghost extends MovableAreaEntity {

    private SuperPacmanPlayer player;
    private boolean isAfraid;

    DiscreteCoordinates spawnPoint;

    //Sprite[][] sprites = RPGSprite.extractSprites("superpacman/ghost.blinky", 2, 1, 1,
    //        this, 64, 64, new Orientation[] {Orientation.UP, Orientation.RIGHT, Orientation.DOWN, Orientation.LEFT});


    Sprite[] afraidSprites = RPGSprite.extractSprites("superpacman/ghost.afraid", 2, 1, 1,
            this, 16, 16);

    Animation afraidAnimation = new Animation(2, afraidSprites);

    /**
     * Default MovableAreaEntity constructor
     *
     * @param area        (Area): Owner area. Not null
     * @param orientation (Orientation): Initial orientation of the entity. Not null
     * @param position    (Coordinate): Initial position of the entity. Not null
     */
    public Ghost(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        spawnPoint = position;
        isAfraid = false;
    }

    @Override
    public void update(float deltaTime){
        super.update(deltaTime);

        //getNextOrientation();

        if(!isDisplacementOccurs()){ //if displacememt not moving
            //System.out.println("Orientation clear: " + getOwnerArea().canEnterAreaCells(this, Collections.singletonList(getCurrentMainCellCoordinates().jump(getNextOrientation().toVector()))));
            if(getOwnerArea().canEnterAreaCells(this, Collections.singletonList(getCurrentMainCellCoordinates().jump(getNextOrientation().toVector()))))
            {
                orientate(getNextOrientation());
            }
           move(18);
        }
        else{ //if displacement is moving

        }
    }

    public Orientation getNextOrientation(){
        return null;
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

    //When ghost is eaten by pacman => Respawn the ghost in its spawn position
    public void eatGhost(){
        getOwnerArea().leaveAreaCells(this, this.getEnteredCells());
        setCurrentPosition(spawnPoint.toVector());

        getOwnerArea().enterAreaCells(this, this.getCurrentCells());
        resetMotion();
    }

    public boolean isAfraid(){
        return isAfraid;
    }

    public void scareGhosts() {
        isAfraid = true;
    }

    public void unscareGhosts(){
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




}
