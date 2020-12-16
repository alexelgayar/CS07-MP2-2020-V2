package ch.epfl.cs107.play.game.superpacman.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaGraph;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;

public class Inky extends InkyPinky{

    private final static int MAX_DISTANCE_WHEN_SCARED = 5;
    private final static int MAX_DISTANCE_WHEN_NOT_SCARED = 10;

    private Sprite[][] sprites = RPGSprite.extractSprites("superpacman/ghost.inky", 2, 1, 1,
            this, 16, 16, new Orientation[]{Orientation.UP, Orientation.RIGHT, Orientation.DOWN, Orientation.LEFT});

    private Animation[] animations = Animation.createAnimations(2, sprites);
    private Animation animation = animations[1];



    /**
     * Default MovableAreaEntity constructor
     *
     * @param area        (Area): Owner area. Not null
     * @param orientation (Orientation): Initial orientation of the entity. Not null
     * @param position    (Coordinate): Initial position of the entity. Not null
     */
    public Inky(Area area, Orientation orientation, DiscreteCoordinates position, AreaGraph areaGraph) {
        super(area, orientation, position, areaGraph);

        //areaGraph.shortestPath(new DiscreteCoordinates((int) getPosition().x, (int)getPosition().y), new DiscreteCoordinates((int)player.getPosition().x, (int)player.getPosition().y));
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        inkyMovement();
        //orientate(getNextOrientation());
        //move(18);

        startAnimation(deltaTime);
    }

    @Override
    public Orientation getNextOrientation() {
        if (player != null){
            return areaGraph.shortestPath(new DiscreteCoordinates((int) getPosition().x, (int)getPosition().y),
                    new DiscreteCoordinates((int)player.getPosition().x, (int)player.getPosition().y)).poll();
        }
        else{
            return super.getNextOrientation();
        }
    }

    public void inkyMovement(){
        if(!isDisplacementOccurs()){
            orientate(getNextOrientation());
            move(18);
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
