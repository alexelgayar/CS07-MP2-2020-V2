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

    public Inky(Area area, Orientation orientation, DiscreteCoordinates position, AreaGraph areaGraph) {
        super(area, orientation, position, areaGraph);
        sprites = RPGSprite.extractSprites("superpacman/ghost.inky", 2, 1, 1,
                this, 16, 16, new Orientation[]{Orientation.UP, Orientation.RIGHT, Orientation.DOWN, Orientation.LEFT});
        animations = Animation.createAnimations(2, sprites);
        animation = animations[1];
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
    }

    @Override
    protected void ghostMovement() {
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

    //Choose the orientation that enables Inky and Pinky to reach their target position
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
    @Override
    protected void scareGhostSignalActivated(){
        if (!scareSignalActive1){
            computeTargetPosition();
            targetPath = computeShortestPath(targetPos);
        }
    }
    @Override
    protected void scareGhostSignalDeactivated(){
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
}
