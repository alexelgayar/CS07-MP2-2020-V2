package ch.epfl.cs107.play.game.superpacman.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Window;

public class Level0 extends SuperPacmanArea{

    @Override
    public String getTitle() {
        return "superpacman/Level0";
    }

    @Override
    protected void createArea() {
        super.createArea();
        registerActor(new Background(this));
        registerActor(new Door("superpacman/Level1", new DiscreteCoordinates(15,6), Logic.TRUE, this, Orientation.UP, new DiscreteCoordinates(5,9)));

    }


}
