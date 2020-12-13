package ch.epfl.cs107.play.game.superpacman.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.List;

public class Ghost extends MovableAreaEntity {

    //Sprite[][] sprites = RPGSprite.extractSprites("superpacman/ghost.blinky", 2, 1, 1,
    //        this, 64, 64, new Orientation[] {Orientation.UP, Orientation.RIGHT, Orientation.DOWN, Orientation.LEFT});


    Sprite[] sprites = RPGSprite.extractSprites("superpacman/ghost.afraid", 2, 1, 1,
            this, 64, 64);

    Animation animation;

    /**
     * Default MovableAreaEntity constructor
     *
     * @param area        (Area): Owner area. Not null
     * @param orientation (Orientation): Initial orientation of the entity. Not null
     * @param position    (Coordinate): Initial position of the entity. Not null
     */
    public Ghost(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);

        animation = new Animation(2, sprites);
    }


    private void getNextOrientation(Orientation orientation){
        //Get the next orientation for the ghost
    }

    @Override
    public void draw(Canvas canvas) {
        animation.draw(canvas);
    }

    @Override
    public void update(float deltaTime){
        super.update(deltaTime);
        animation.update(deltaTime);
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return null;
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
        return false;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {

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
