package ch.epfl.cs107.play.game.superpacman.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.superpacman.actor.Gate;
import ch.epfl.cs107.play.game.superpacman.actor.Key;
import ch.epfl.cs107.play.game.tutosSolution.area.Tuto2Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.And;
import ch.epfl.cs107.play.signal.logic.Logic;

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

       registerActor(key1 = new Key(this, Orientation.DOWN, new DiscreteCoordinates(3, 16)));
       registerActor(key2 = new Key(this, Orientation.DOWN, new DiscreteCoordinates(26, 16)));
        registerActor(key3 = new Key(this, Orientation.DOWN, new DiscreteCoordinates(2, 8)));
        registerActor(key4 = new Key(this, Orientation.DOWN, new DiscreteCoordinates(27, 8)));

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


















    }
}
