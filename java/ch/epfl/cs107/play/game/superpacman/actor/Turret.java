package ch.epfl.cs107.play.game.superpacman.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.*;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public class Turret extends AreaEntity {

    private Sprite sprite;

    private final static float SHOOT_COOLDOWN = 150.f;
    private float shootCooldownTimer = 0.f;
    private Orientation projectileDirection;
    private int projectileXOffset = 0;
    private int projectileYOffset = 0;

    public Turret(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        projectileDirection = orientation;
        computeProjectileOffset();
        sprite = new Sprite("superpacman/Turret_2", 1.f, 1.f , this);
    }

    /**
     * Computes the required offset to spawn the projectile in an empty neighbour cell (and not inside a wall)
     */
    private void computeProjectileOffset(){
        if (projectileDirection == Orientation.RIGHT){
            projectileXOffset = 1;
        }
        if (projectileDirection == Orientation.LEFT){
            projectileXOffset = -1;
        }
        if(projectileDirection == Orientation.UP){
            projectileYOffset = 1;
        }
        if (projectileDirection == Orientation.DOWN) {
            projectileYOffset = -1;
        }
    }
    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        //Spawn a projectile that fires
        if (shootCooldownTimer <= 0){
            shootCooldownTimer = SHOOT_COOLDOWN;
            getOwnerArea().registerActor(new Projectile(getOwnerArea(), projectileDirection, new DiscreteCoordinates((int)getPosition().x + projectileXOffset, (int) getPosition().y + projectileYOffset)));
        }
        shootCooldownTimer -= 1;
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public boolean takeCellSpace() {
        return true;
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
        ((SuperPacmanInteractionVisitor)v).interactWith(this);
    }
}
