package ch.epfl.cs107.play.game.superpacman.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.superpacman.actor.Gate;
import ch.epfl.cs107.play.game.superpacman.actor.Key;
import ch.epfl.cs107.play.game.superpacman.actor.SuperPacmanPlayer;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Window;

public class Level1 extends SuperPacmanArea {

    public final static DiscreteCoordinates PLAYER_SPAWN_POSITION = new DiscreteCoordinates(15, 6);

    @Override
    public DiscreteCoordinates getSpawnPoint() {
        return PLAYER_SPAWN_POSITION;

    }


    @Override
    public boolean isOn() {
        return nbrDiamonds==406;
    }

    private int nbrDiamonds= 0;                                                                                                                         

    public void countDiamonds(){
        nbrDiamonds ++;
    }


    @Override
    public String getTitle() {
        return "superpacman/Level1";
    }

    @Override
    protected void createArea() {
        super.createArea();
       // registerActor(new Background(this));
        registerActor(new Door("superpacman/Level2", new DiscreteCoordinates(15,29), Logic.TRUE, this, Orientation.DOWN, new DiscreteCoordinates(14,0), new DiscreteCoordinates(15, 0)));

      //registerActor(new Gate(this, Orientation.RIGHT, this, new DiscreteCoordinates(14,3)));
       registerActor(new Gate(this, Orientation.RIGHT, this, new DiscreteCoordinates(15,3)));


    }


}
