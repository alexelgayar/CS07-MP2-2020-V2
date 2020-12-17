package ch.epfl.cs107.play.game.superpacman.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaGraph;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Path;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import javax.print.attribute.standard.OrientationRequested;
import javax.xml.xpath.XPath;
import java.util.LinkedList;
import java.util.Queue;

public class InkyPinky extends Ghost{

    protected static AreaGraph areaGraph;
    protected DiscreteCoordinates targetPos;
    protected Queue<Orientation> targetPath;
    protected boolean scareSignalActive1 = false;
    protected boolean scareSignalActive2 = false;

    protected int currentAreaWidth = getOwnerArea().getWidth();
    protected int currentAreaHeight = getOwnerArea().getHeight();



    //Path path = new Path(this.getPosition(), new LinkedList<Orientation>());
    //Path graphicPath = new Path(this.getPosition(), new LinkedList<Orientation>(path));

    public InkyPinky(Area area, Orientation orientation, DiscreteCoordinates position, AreaGraph areaGraph) {
        super(area, orientation, position);

        this.areaGraph = areaGraph;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        startAnimation(deltaTime);
    }

    public boolean ghostHasReachedTarget(){
        if (targetPos == null){
            return false;
        }
        else {
            return (targetPos == this.getCurrentMainCellCoordinates());
        }
    }

    public Queue<Orientation> computeShortestPath(DiscreteCoordinates targetPosition){
        targetPath = areaGraph.shortestPath(getCurrentMainCellCoordinates(), targetPosition);
        return targetPath;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (targetPath != null) {
            Path graphicPath = new Path(this.getPosition(), new LinkedList<Orientation>(targetPath));
            graphicPath.draw(canvas);
        }
    }
}
