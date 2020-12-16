package ch.epfl.cs107.play.game.superpacman.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Path;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import javax.print.attribute.standard.OrientationRequested;
import javax.xml.xpath.XPath;
import java.util.LinkedList;

public class InkyPinky extends Ghost{

    Path path = new Path(this.getPosition(), new LinkedList<Orientation>());
    Path graphicPath = new Path(this.getPosition(), new LinkedList<Orientation>(path));

    public InkyPinky(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
    }

    //Inky and Pinky cause an error when resetting the ghosts
    @Override
    public void resetGhosts() {
        //super.resetGhosts();
    }

    //Inky and Pinky cause an error when eating the ghosts
    @Override
    public void eatGhost() {
        //super.eatGhost();
    }

    @Override
    public Orientation getNextOrientation() {
        return super.getNextOrientation();
        //Choose the orientation that enables Inky and Pinky to reach their target position

    }
}
