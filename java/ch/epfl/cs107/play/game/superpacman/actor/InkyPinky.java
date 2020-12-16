package ch.epfl.cs107.play.game.superpacman.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaGraph;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Path;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import javax.print.attribute.standard.OrientationRequested;
import javax.xml.xpath.XPath;
import java.util.LinkedList;
import java.util.Queue;

public class InkyPinky extends Ghost{

    protected static AreaGraph areaGraph;
    protected DiscreteCoordinates targetPos;
    protected Queue<Orientation> targetPath;

    //Path path = new Path(this.getPosition(), new LinkedList<Orientation>());
    //Path graphicPath = new Path(this.getPosition(), new LinkedList<Orientation>(path));

    public InkyPinky(Area area, Orientation orientation, DiscreteCoordinates position, AreaGraph areaGraph) {
        super(area, orientation, position);

        this.areaGraph = areaGraph;
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

    //Method computes a new target
    public void computeTargetPath() {
        //Leave empty
    }

    public boolean ghostHasReachedTarget(){
        if (targetPos == null){
            return false;
        }
        else {
            return ((targetPos.x == (int) this.getPosition().x) && (targetPos.y == (int) this.getPosition().y));
        }
    }

    public Queue<Orientation> computeShortestPath(DiscreteCoordinates targetPosition){
        targetPath = areaGraph.shortestPath(new DiscreteCoordinates((int) getPosition().x, (int)getPosition().y), targetPosition);
        return targetPath;
    }

    @Override
    public Orientation getNextOrientation() {
        return super.getNextOrientation();
        //Choose the orientation that enables Inky and Pinky to reach their target position
    }
}
