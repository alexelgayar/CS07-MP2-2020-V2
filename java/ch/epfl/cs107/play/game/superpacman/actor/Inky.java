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

        inkyMovement();
    }


    /**
     * Method that applies the specific movement behaviour of Inky
     * Note: Follow shortest path to player, else stay need refuge area
     */
    private void inkyMovement() {
        //No displacement occurs when ghost reaches target
        if(!isDisplacementOccurs()){
            orientate(getNextOrientation());
            if (!isAfraid()) {
                //System.out.println("INKY: targetPos (goal): " + targetPos + " | ghostPos (start): " + this.getCurrentMainCellCoordinates());
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
        //This only runs if any of the following conditions are true
        getTarget();
        return targetPath.poll();
    }


    /**
     * Method to verify that chosen target has a valid path
     */
    private void getTarget(){
        computeTargetPosition();
        while(ghostHasReachedTarget() || targetPath == null || targetPath.size() == 0 || targetPos == null){
            computeTargetPosition();
            targetPath = computeShortestPath(targetPos);
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
            //System.out.println("Error with player path: MemorisePlayerInky");
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
            //System.out.println("Error with player path: ForgetPlayerInky");
        }
    }

    //Signal occurs when the ghosts are activated

    /**
     * Method which allows to computeTargetPosition once, when ghosts transition to afraid state
     */
    protected void scareGhostSignalActivated(){
        if (!scareSignalActive1){
            computeTargetPosition();
            targetPath = computeShortestPath(targetPos);
        }
    }


    /**
     * Method which allows to computeTargetPosition once, when ghosts transition to unafraid state
     */
    protected void scareGhostSignalDeactivated(){
        if (!scareSignalActive2){
            computeTargetPosition();
            targetPath = computeShortestPath(targetPos);
        }
    }


    /**
     * Method to compute a valid targetPosition which respects the behaviour of Inky's personality
     */
    public void computeTargetPosition() {
        if (!isAfraid()) {
            if (player == null) { //This runs well
                {
                    //Go to any cell within spawnPoint + MAX_DISTANCE_WHEN_NOT_SCARED (=10)
                    do {
                        int x = RandomGenerator.getInstance().nextInt(currentAreaWidth);
                        int y = RandomGenerator.getInstance().nextInt(currentAreaHeight);
                        targetPos = new DiscreteCoordinates(x, y);
                    } while ((DiscreteCoordinates.distanceBetween(spawnPoint, targetPos) > MAX_DISTANCE_WHEN_NOT_SCARED) || (DiscreteCoordinates.distanceBetween(targetPos, getCurrentMainCellCoordinates()) == 0) || (targetPos == getCurrentMainCellCoordinates()));
                    //System.out.println(DiscreteCoordinates.distanceBetween(spawnPoint, targetPos));
                }
            }
        }
        else { //Here, path goes haywire
            //Go to any cell within spawnPoint + MAX_DISTANCE_WHEN_SCARED (=5)
            do {
                int x = RandomGenerator.getInstance().nextInt(currentAreaWidth);
                int y = RandomGenerator.getInstance().nextInt(currentAreaHeight);
                targetPos = new DiscreteCoordinates(x, y);
            } while ((DiscreteCoordinates.distanceBetween(spawnPoint, targetPos) > MAX_DISTANCE_WHEN_SCARED) || (DiscreteCoordinates.distanceBetween(targetPos, getCurrentMainCellCoordinates()) == 0) || (targetPos == getCurrentMainCellCoordinates()));
            //targetPath = computeShortestPath(targetPos);
        }
    }
}
