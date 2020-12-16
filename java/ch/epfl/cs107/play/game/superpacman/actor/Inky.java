package ch.epfl.cs107.play.game.superpacman.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaGraph;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Path;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.window.Canvas;

import java.util.LinkedList;

public class Inky extends InkyPinky{

    private final static int MAX_DISTANCE_WHEN_SCARED = 5;
    private final static int MAX_DISTANCE_WHEN_NOT_SCARED = 10;
    private final static int SPEED_WHEN_SCARED = 9;

    private Sprite[][] sprites = RPGSprite.extractSprites("superpacman/ghost.inky", 2, 1, 1,
            this, 16, 16, new Orientation[]{Orientation.UP, Orientation.RIGHT, Orientation.DOWN, Orientation.LEFT});

    private Animation[] animations = Animation.createAnimations(2, sprites);
    private Animation animation = animations[1];
    private boolean firstPath = false;


    /**
     * Default MovableAreaEntity constructor
     *
     * @param area        (Area): Owner area. Not null
     * @param orientation (Orientation): Initial orientation of the entity. Not null
     * @param position    (Coordinate): Initial position of the entity. Not null
     */
    public Inky(Area area, Orientation orientation, DiscreteCoordinates position, AreaGraph areaGraph) {
        super(area, orientation, position, areaGraph);

        System.out.println("Ghost spawnpoint: " + spawnPoint);
        //areaGraph.shortestPath(new DiscreteCoordinates((int) getPosition().x, (int)getPosition().y), new DiscreteCoordinates((int)player.getPosition().x, (int)player.getPosition().y));
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if(isAfraid()) {
            scareGhostSignalActivated();
            scareSignalActive1 = true;
            scareSignalActive2 = false;
        } else{
            scareGhostSignalDeactivated();
            scareSignalActive1 = false;
            scareSignalActive2 = true;
        }
        ghostMovement();

        startAnimation(deltaTime);
    }

    public void ghostMovement(){
        //No displacement occurs when ghost reaches target
        if(!isDisplacementOccurs()){
            orientate(getNextOrientation());
            if (!isAfraid()) {
                move(SPEED);
            }
            else {
                move(SPEED_WHEN_SCARED);
            }
        }
    }

    @Override
    public Orientation getNextOrientation() {
        if (player != null){
            targetPos = new DiscreteCoordinates((int)player.getPosition().x, (int)player.getPosition().y);
            targetPath = computeShortestPath(targetPos);
        }
        while(ghostHasReachedTarget() || targetPath == null){
            computeTargetPosition();
            targetPath = computeShortestPath(targetPos);
        }

        return targetPath.poll();
    }

    @Override
    protected void memorisePlayer(SuperPacmanPlayer player) {
        super.memorisePlayer(player);
        computeTargetPosition();
        targetPath = computeShortestPath(targetPos);
    }

    @Override
    protected void forgetPlayer() {
        super.forgetPlayer();
        computeTargetPosition();
        targetPath = computeShortestPath(targetPos);
    }

    //Signal occurs when the ghosts are activated
    private void scareGhostSignalActivated(){
        if (!scareSignalActive1){
            computeTargetPosition();
            targetPath = computeShortestPath(targetPos);
        }
    }

    private void scareGhostSignalDeactivated(){
        if (!scareSignalActive2){
            computeTargetPosition();
            targetPath = computeShortestPath(targetPos);
        }
    }

    //Compute the next path
    @Override
    public void computeTargetPosition() {
        if (!isAfraid()) {
            if (player == null) {
                {
                    //Go to any cell within spawnPoint + MAX_DISTANCE_WHEN_NOT_SCARED (=10)
                    do {
                        int x = RandomGenerator.getInstance().nextInt(currentAreaWidth);
                        int y = RandomGenerator.getInstance().nextInt(currentAreaHeight);
                        targetPos = new DiscreteCoordinates(x, y);
                    } while ((DiscreteCoordinates.distanceBetween(spawnPoint, targetPos) > MAX_DISTANCE_WHEN_NOT_SCARED) || (DiscreteCoordinates.distanceBetween(targetPos, getCurrentMainCellCoordinates()) == 0));
                    //System.out.println(DiscreteCoordinates.distanceBetween(spawnPoint, targetPos));
                }
            }
        }
        else {
            //Go to any cell within spawnPoint + MAX_DISTANCE_WHEN_SCARED (=5)
            do {
                int x = RandomGenerator.getInstance().nextInt(currentAreaWidth);
                int y = RandomGenerator.getInstance().nextInt(currentAreaHeight);
                targetPos = new DiscreteCoordinates(x, y);
            } while ((DiscreteCoordinates.distanceBetween(spawnPoint, targetPos) > MAX_DISTANCE_WHEN_SCARED) || (DiscreteCoordinates.distanceBetween(targetPos, getCurrentMainCellCoordinates()) == 0));
            //targetPath = computeShortestPath(targetPos);
        }
    }

    @Override
    public void startAnimation(float deltaTime) {
        super.startAnimation(deltaTime);
        if(!isAfraid()){
            if(isDisplacementOccurs()) {
                animation = animations[getOrientation().ordinal()];
                animation.update(deltaTime);
            }
        }
        else{
            if(isDisplacementOccurs())
                super.afraidAnimation.update(deltaTime);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (targetPath != null) {
            Path graphicPath = new Path(this.getPosition(), new LinkedList<Orientation>(targetPath));
            graphicPath.draw(canvas);
        }
        if (!isAfraid()) {
            animation.draw(canvas);
        }
        else{
            afraidAnimation.draw(canvas);
        }
    }
}
