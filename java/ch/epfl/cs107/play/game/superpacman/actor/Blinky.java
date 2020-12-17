package ch.epfl.cs107.play.game.superpacman.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaGraph;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;

public class Blinky extends Ghost{

    public Blinky(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);

        sprites = RPGSprite.extractSprites("superpacman/ghost.blinky", 2, 1, 1,
                this, 16, 16, new Orientation[]{Orientation.UP, Orientation.RIGHT, Orientation.DOWN, Orientation.LEFT});
        animations = Animation.createAnimations(2, sprites);
        animation = animations[1];
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        blinkyMovement();
        startAnimation(deltaTime);
    }


    /**
     * Method that applies the specific behaviour of the Blinky Ghost
     * Note: Get randomOrientation at each step of simulation
     */
    public void blinkyMovement(){
        if(!isDisplacementOccurs()){
            if(getOwnerArea().canEnterAreaCells(this, Collections.singletonList(getCurrentMainCellCoordinates().jump(getNextOrientation().toVector()))))
            {
                orientate(getNextOrientation());
            }
            move(SPEED);
        }
    }

    @Override
    public void startAnimation(float deltaTime) {
        super.startAnimation(deltaTime);
        if(!isAfraid()){
            if(isDisplacementOccurs()) {
                animation = animations[getOrientation().ordinal()];
                animation.update(deltaTime);
            }
        }
        else{
            if(isDisplacementOccurs())
                super.afraidAnimation.update(deltaTime);
        }
    }
}
