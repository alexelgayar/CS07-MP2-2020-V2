package ch.epfl.cs107.play.game.superpacman.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.superpacman.actor.*;
import ch.epfl.cs107.play.game.tutosSolution.area.Tuto2Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.And;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.signal.logic.Or;

import javax.print.attribute.standard.OrientationRequested;

public class Level2 extends SuperPacmanArea {

    public final static DiscreteCoordinates PLAYER_SPAWN_POSITION = new DiscreteCoordinates(15, 29);

    @Override
    public DiscreteCoordinates getSpawnPoint() {
        return PLAYER_SPAWN_POSITION;

    }

    @Override
    public boolean isOn() {
        return nbrDiamonds==394;
    }

    private int nbrDiamonds= 0;

    public void countDiamonds(){
        nbrDiamonds ++;
    }

    Key key1;
    Key key2;
    Key key3;
    Key key4;

    @Override
    public String getTitle() {
        return "superpacman/Level2";
    }

    @Override
    protected void createArea() {
        super.createArea();

        //Key1 is for AreaGraph 1 of the Pinky Ghost
        //Key2 is for AreaGraph 2 of the Pinky Ghost

        //Spawning in the Keys for the Level 2
        registerActor(key1 = new Key(this, Orientation.DOWN, new DiscreteCoordinates(3, 16)));
        registerActor(key2 = new Key(this, Orientation.DOWN, new DiscreteCoordinates(26, 16)));
        registerActor(key3 = new Key(this, Orientation.DOWN, new DiscreteCoordinates(2, 8)));
        registerActor(key4 = new Key(this, Orientation.DOWN, new DiscreteCoordinates(27, 8)));

        //Spawning in the Gates for the Level 2
        registerActor(new Gate(this, Orientation.RIGHT, key1, new DiscreteCoordinates(8,14)));
        registerActor(new Gate(this, Orientation.UP, key1, new DiscreteCoordinates(5,12)));
        registerActor(new Gate(this, Orientation.RIGHT, key1, new DiscreteCoordinates(8,10)));
        registerActor(new Gate(this, Orientation.RIGHT, key1, new DiscreteCoordinates(8,8)));

        registerActor(new Gate(this, Orientation.RIGHT, key2, new DiscreteCoordinates(21,14)));
        registerActor(new Gate(this, Orientation.UP, key2, new DiscreteCoordinates(24,12)));
        registerActor(new Gate(this, Orientation.RIGHT, key2, new DiscreteCoordinates(21,10)));
        registerActor(new Gate(this, Orientation.RIGHT, key2, new DiscreteCoordinates(21, 8)));

        registerActor(new Gate(this, Orientation.RIGHT, new And(key3,key4), new DiscreteCoordinates(10,2)));
        registerActor(new Gate(this, Orientation.RIGHT, new And(key3,key4), new DiscreteCoordinates(19,2)));
        registerActor(new Gate(this, Orientation.RIGHT, new And(key3,key4), new DiscreteCoordinates(12,8)));
        registerActor(new Gate(this, Orientation.RIGHT, new And(key3,key4), new DiscreteCoordinates(17,8)));

        registerActor(new Gate(this, Orientation.RIGHT, this, new DiscreteCoordinates(14,3)));
        registerActor(new Gate(this, Orientation.RIGHT, this, new DiscreteCoordinates(15,3)));

        //Spawning in the Hearts for the Level2
        registerActor(new Heart(this, Orientation.UP, new DiscreteCoordinates(21,11)));

        //Spawning in the SawBlades for the Level2
        registerActor(new SawBlade(this, Orientation.RIGHT, new DiscreteCoordinates(5,9)));
        registerActor(new SawBlade(this, Orientation.DOWN, new DiscreteCoordinates(9,28)));
        registerActor(new SawBlade(this, Orientation.DOWN, new DiscreteCoordinates(20,28)));

        //Spawning in the Turrets for the Level2
        registerActor(new Turret(this, Orientation.DOWN, new DiscreteCoordinates(14,22)));
        registerActor(new Turret(this, Orientation.DOWN, new DiscreteCoordinates(15,22)));

        registerActor(new Turret(this, Orientation.RIGHT, new DiscreteCoordinates(0,28)));
        registerActor(new Turret(this, Orientation.LEFT, new DiscreteCoordinates(29,28)));
    }
}
