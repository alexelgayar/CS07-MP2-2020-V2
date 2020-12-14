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

    DiscreteCoordinates spawnPoint;



    //Sprite[][] sprites = RPGSprite.extractSprites("superpacman/ghost.blinky", 2, 1, 1,
    //        this, 64, 64, new Orientation[] {Orientation.UP, Orientation.RIGHT, Orientation.DOWN, Orientation.LEFT});


    Sprite[] afraidSprites = RPGSprite.extractSprites("superpacman/ghost.afraid", 2, 1, 1,
            this, 64, 64);

    Animation afraidAnimation;

    /**
     * Default MovableAreaEntity constructor
     *
     * @param area        (Area): Owner area. Not null
     * @param orientation (Orientation): Initial orientation of the entity. Not null
     * @param position    (Coordinate): Initial position of the entity. Not null
     */
    public Ghost(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);


        if(isAfraid()) {
            if(isDisplacementOccurs()) {
                afraidAnimation = new Animation(2, afraidSprites);
            }
        }



    }

    @Override
    public void update(float deltaTime){
        super.update(deltaTime);



        if(isAfraid()) {
            if(isDisplacementOccurs()) {
                afraidAnimation.update(deltaTime);
            }
        }

        getNextOrientation();

        if(!isDisplacementOccurs()){
            if(getOwnerArea().canEnterAreaCells(this, Collections.singletonList(getCurrentMainCellCoordinates().jump(getNextOrientation().toVector()))))
            {
                orientate(getNextOrientation());
            }
           move(18);
        }


    }

    public boolean isAfraid(){
        return player.isInvincible();
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


        /*Here we will check for interactions between ghost and player
            If player touches ghost and invulnerabilityTimer = 0 =>
                - Player loses hp: hp -= 1;
                - Reset map + ghosts
                - Unregister Player and Re-register at spawn position
            If player touches ghost and invulnerabilityTimer > 0 =>
                - Player Score += GHOST_SCORE
                - Ghost spawns back at its spawn position



        if (invulnerabilityTimer > 0) {
        invulnerabilityTimer -= deltaTime;
    }
        if (invulnerabilityTimer < 0){
        invulnerabilityTimer = 0;
    }
         */



}
