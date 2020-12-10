package ch.epfl.cs107.play.game.superpacman.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.tutosSolution.area.Tuto2Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level2 extends SuperPacmanArea {

    @Override
    public String getTitle() {
        return "superpacman/Level2";
    }

    @Override
    protected void createArea() {
        super.createArea();
        registerActor(new Background(this));

    }
}
