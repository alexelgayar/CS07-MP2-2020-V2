package ch.epfl.cs107.play.game.superpacman.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.superpacman.actor.*;
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

    @Override
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
        registerActor(new Door("superpacman/Level2", new DiscreteCoordinates(15,29), Logic.TRUE, this, Orientation.DOWN, new DiscreteCoordinates(14,0), new DiscreteCoordinates(15, 0)));

        //Spawning in the Gates for the level 1
        registerActor(new Gate(this, Orientation.RIGHT, this, new DiscreteCoordinates(14,3)));
        registerActor(new Gate(this, Orientation.RIGHT, this, new DiscreteCoordinates(15,3)));

        //Spawning in the Heart for the level 1
        registerActor(new Heart(this, Orientation.UP, new DiscreteCoordinates(14,13)));

        //Spawning in the SawBlades for the Level 1
        registerActor(new SawBlade(this, Orientation.UP, new DiscreteCoordinates(28, 1)));
        registerActor(new SawBlade(this, Orientation.UP, new DiscreteCoordinates(1, 1)));
        registerActor(new SawBlade(this, Orientation.RIGHT, new DiscreteCoordinates( 13, 28)));

        //Spawning in the turrets for the level 1
        registerActor(new Turret(this, Orientation.RIGHT, new DiscreteCoordinates(0,1)));
        registerActor(new Turret(this, Orientation.LEFT, new DiscreteCoordinates(29,1)));
        registerActor(new Turret(this, Orientation.RIGHT, new DiscreteCoordinates(0,28)));
        registerActor(new Turret(this, Orientation.LEFT, new DiscreteCoordinates(29,28)));


    }


}
