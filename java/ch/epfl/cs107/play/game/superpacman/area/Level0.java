package ch.epfl.cs107.play.game.superpacman.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.superpacman.actor.Gate;
import ch.epfl.cs107.play.game.superpacman.actor.Key;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Window;

public class Level0 extends SuperPacmanArea{

public final static DiscreteCoordinates PLAYER_SPAWN_POSITION = new DiscreteCoordinates(10, 1);



    private Key key1;

    @Override
    public String getTitle() {
        return "superpacman/Level0";
    }

    @Override
    protected void createArea() {
        super.createArea();
        //registerActor(new Background(this));
        registerActor(new Door("superpacman/Level1", new DiscreteCoordinates(15,6), Logic.TRUE, this, Orientation.UP, new DiscreteCoordinates(5,9),new DiscreteCoordinates(6,9) ));

        registerActor(key1 = new Key(this, Orientation.DOWN, new DiscreteCoordinates(3,4)));

        registerActor(new Gate(this, Orientation.RIGHT, key1, new DiscreteCoordinates(5,8)));

        registerActor(new Gate(this, Orientation.RIGHT, key1, new DiscreteCoordinates(6,8)));

    }

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        return super.begin(window, fileSystem);
    }
}
