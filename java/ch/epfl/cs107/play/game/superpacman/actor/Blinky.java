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


    private Sprite[][] sprites = RPGSprite.extractSprites("superpacman/ghost.blinky", 2, 1, 1,
            this, 16, 16, new Orientation[]{Orientation.UP, Orientation.RIGHT, Orientation.DOWN, Orientation.LEFT});

    private Animation[] animations = Animation.createAnimations(2, sprites);
    private Animation animation = animations[1];

    public Blinky(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        blinkyMovement();
        startAnimation(deltaTime);
    }

    public void blinkyMovement(){
        if(!isDisplacementOccurs()){
            if(getOwnerArea().canEnterAreaCells(this, Collections.singletonList(getCurrentMainCellCoordinates().jump(getNextOrientation().toVector()))))
            {
                orientate(getNextOrientation());
            }
            move(SPEED);
        }
        else{ //if blinky is moving

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

    @Override
    public void draw(Canvas canvas) {
        if (!isAfraid()) {
            animation.draw(canvas);
        }
        else{
            afraidAnimation.draw(canvas);
        }
    }
}
