package ch.epfl.cs107.play.game.superpacman.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.window.Canvas;

public class Blinky extends Ghost{


    Sprite[] sprites = RPGSprite.extractSprites("superpacman/ghost.blinky", 2, 1, 1,
            this, 64, 64);

    Animation animation;

    int randomInt = RandomGenerator.getInstance().nextInt(3);



    /**
     * Default MovableAreaEntity constructor
     *
     * @param area        (Area): Owner area. Not null
     * @param orientation (Orientation): Initial orientation of the entity. Not null
     * @param position    (Coordinate): Initial position of the entity. Not null
     */
    public Blinky(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        animation = new Animation(8, sprites);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if(!isAfraid()){
            if(isDisplacementOccurs()){
                animation.update(deltaTime);
            }

        }
    }

    @Override
    public Orientation getNextOrientation() {
        return Orientation.fromInt(randomInt);
    }

    @Override
    public void draw(Canvas canvas) {
        animation.draw(canvas);
    }
}
