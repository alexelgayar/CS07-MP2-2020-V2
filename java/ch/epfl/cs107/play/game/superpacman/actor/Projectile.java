package ch.epfl.cs107.play.game.superpacman.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public class Projectile extends MovableAreaEntity {

    private Sprite sprite;
    private Sprite[] sprites;
    private Animation[] animations;
    private Animation animation;

    private Orientation projectileOrientation;
    private final static int PROJECTILE_SPEED = 18;

    private final static int UNREGISTER_COUNTDOWN_TIME_CONSTANT = 16;
    private static int unregisterCountdown = UNREGISTER_COUNTDOWN_TIME_CONSTANT;

    /**
     * Projectile Constructor Class
     *
     * @param area        (Area): Owner area. Not null
     * @param orientation (Orientation): Initial orientation of the entity. Not null
     * @param position    (Coordinate): Initial position of the entity. Not null
     */
    public Projectile(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        projectileOrientation = orientation;
        sprite = new Sprite("superpacman/Projectile", 1.f, 1.f , this);

        Sprite[] sprites = RPGSprite.extractSprites("superpacman/Explosion", 8,1,1, this, 48,48);
        animation = new Animation(3, sprites);
    }

    @Override
    public void draw(Canvas canvas) {
        if (isDisplacementOccurs()){
            sprite.draw(canvas);
        }
        else{
            animation.draw(canvas);
        }
        //animation.draw(canvas);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        animation.update(deltaTime);
        orientate(projectileOrientation);
        move(PROJECTILE_SPEED);

        if (!isDisplacementOccurs()){
            if (unregisterCountdown <= 0){
                getOwnerArea().unregisterActor(this);
                unregisterCountdown = UNREGISTER_COUNTDOWN_TIME_CONSTANT;
            }

            unregisterCountdown -= 1;
        }
    }

    public void unregisterProjectile(){
        getOwnerArea().unregisterActor(this);
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

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((SuperPacmanInteractionVisitor)v).interactWith(this);
    }
}
