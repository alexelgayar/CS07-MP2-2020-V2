package ch.epfl.cs107.play.game.superpacman;

import ch.epfl.cs107.play.game.rpg.RPG;

public class SuperPacman  extends RPG {

    public final static float CAMERA_SCALE_FACTOR = 15.f;

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public String getTitle() {
        return "Super Pac-Man";
    }
}