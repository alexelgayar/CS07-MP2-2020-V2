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
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Canvas;

import java.util.LinkedList;

public class Pinky extends InkyPinky{

    private final static int MIN_AFRAID_DISTANCE = 5;
    private final static int MAX_RANDOM_ATTEMPT = 200;

    public Pinky(Area area, Orientation orientation, DiscreteCoordinates position, AreaGraph areaGraph) {
        super(area, orientation, position, areaGraph);
        sprites = RPGSprite.extractSprites("superpacman/ghost.pinky", 2, 1, 1,
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

    private void ghostMovement(){
        if(!isDisplacementOccurs()){
            orientate(getNextOrientation());
            //System.out.println("INKY: targetPos (goal): " + targetPos + " | ghostPos (start): " + this.getCurrentMainCellCoordinates() + " | targetPath: " + targetPath);
            move(SPEED);
        }
    }

    //Choose the orientation that enables Inky and Pinky to reach their target position
    @Override
    public Orientation getNextOrientation() {
        if (!isAfraid()) {
            if (player != null) {
                targetPos = new DiscreteCoordinates((int) player.getPosition().x, (int) player.getPosition().y);
                if (computeShortestPath(targetPos)!= null) {
                    targetPath = computeShortestPath(targetPos);
                }
                else{
                    player = null;
                    getTarget();
                }
            }
        }

        getTarget();

        return targetPath.poll();
    }

    private void getTarget(){
        computeTargetPosition();
        while(ghostHasReachedTarget() || targetPath == null || targetPath.size() == 0 || targetPos == null){
            computeTargetPosition();
            targetPath = computeShortestPath(targetPos);
        }
    }
    //Compute the next path
    public void computeTargetPosition() {
        if (!isAfraid()) {
            if (player == null) {
                {
                    //Go to any cell in the map
                    do {
                        int x = RandomGenerator.getInstance().nextInt(currentAreaWidth);
                        int y = RandomGenerator.getInstance().nextInt(currentAreaHeight);
                        targetPos = new DiscreteCoordinates(x, y);
                    } while ((DiscreteCoordinates.distanceBetween(targetPos, getCurrentMainCellCoordinates()) == 0) || (targetPos == getCurrentMainCellCoordinates()));
                }
            }
        }
        else {
            if (player != null){
                //Target any cell that is minimum MIN_AFRAID_DISTANCE away from the player
                int attemptCounter = 0;
                DiscreteCoordinates playerPosition = new DiscreteCoordinates((int)player.getPosition().x, (int)player.getPosition().y);
                do {
                    int x = RandomGenerator.getInstance().nextInt(currentAreaWidth);
                    int y = RandomGenerator.getInstance().nextInt(currentAreaHeight);
                    targetPos = new DiscreteCoordinates(x, y);
                    ++attemptCounter;
                } while ((DiscreteCoordinates.distanceBetween(playerPosition, targetPos) < MIN_AFRAID_DISTANCE || (DiscreteCoordinates.distanceBetween(targetPos, getCurrentMainCellCoordinates()) == 0)|| (targetPos == getCurrentMainCellCoordinates()) || attemptCounter <= MAX_RANDOM_ATTEMPT));

            }
            else {
                //Go to any cell in the map
                do {
                    int x = RandomGenerator.getInstance().nextInt(currentAreaWidth);
                    int y = RandomGenerator.getInstance().nextInt(currentAreaHeight);
                    targetPos = new DiscreteCoordinates(x, y);
                } while ((DiscreteCoordinates.distanceBetween(targetPos, getCurrentMainCellCoordinates()) == 0)|| (targetPos == getCurrentMainCellCoordinates()));
                //targetPath = computeShortestPath(targetPos);
            }
        }
    }

    @Override
    protected void memorisePlayer(SuperPacmanPlayer player) {
        super.memorisePlayer(player);
        computeTargetPosition();
        if (computeShortestPath(targetPos)!= null) {
            targetPath = computeShortestPath(targetPos);
        }
        else{
            //System.out.println("Error with player path: memorisePlayerInky");
        }
    }

    @Override
    protected void forgetPlayer() {
        super.forgetPlayer();
        computeTargetPosition();
        if (computeShortestPath(targetPos)!= null) {
            targetPath = computeShortestPath(targetPos);
        }
        else{
            //System.out.println("Error with player path: forgetPlayerPinky");
        }
    }

    //Signal occurs when the ghosts are activated
    protected void scareGhostSignalActivated(){
        if (!scareSignalActive1){
            computeTargetPosition();
            targetPath = computeShortestPath(targetPos);
        }
    }

    protected void scareGhostSignalDeactivated(){
        if (!scareSignalActive2){
            computeTargetPosition();
            targetPath = computeShortestPath(targetPos);
        }
    }

}
