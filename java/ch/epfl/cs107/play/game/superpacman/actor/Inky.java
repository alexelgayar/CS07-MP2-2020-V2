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
import java.util.List;

public class Inky extends InkyPinky{

    private final static int MAX_DISTANCE_WHEN_SCARED = 5;
    private final static int MAX_DISTANCE_WHEN_NOT_SCARED = 10;

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

        if (player == null && !firstPath){
            firstPath = true;
            computeTargetPath();
        }

        if (ghostHasReachedTarget()){
            computeTargetPath();
        }

        if (player!= null && !(isAfraid())){
            computeTargetPath();
        }

        ghostMovement();

        startAnimation(deltaTime);
    }

    public void ghostMovement(){
        //No displacement occurs when ghost reaches target
        if(!isDisplacementOccurs()){
            orientate(getNextOrientation());
            move(18);
        }
    }


    @Override
    public Orientation getNextOrientation() {
        return targetPath.poll();
    }

    //This won't run!! (Since Ghost class is the one that is run)
    @Override
    public void scareGhost() {
        super.scareGhost();
        System.out.println("Running ScareGhost");
        computeTargetPath();
    }

    //This won't run!! (since Ghost class is the one that is run)
    @Override
    public void unscareGhost() {
        super.unscareGhost();
        System.out.println("Running unscareGhost");
        computeTargetPath();
    }

    @Override
    protected void memorisePlayer(SuperPacmanPlayer player) {
        super.memorisePlayer(player);
    }

    //Compute the next path
    @Override
    public void computeTargetPath() {

        if (!isAfraid()){
            if (player!= null){
                targetPos = new DiscreteCoordinates((int)player.getPosition().x, (int)player.getPosition().y);
                targetPath = computeShortestPath(targetPos);
                //System.out.println("Ghost is not Afraid + player != null -> targetPos:" + targetPos);
            }
            else{
                //System.out.println("Here you must compute a random cell in spawnPoint + 10");
                List<DiscreteCoordinates> notAfraidArea = new LinkedList<>();
                for (int y = spawnPoint.y - MAX_DISTANCE_WHEN_NOT_SCARED; y <= spawnPoint.y + MAX_DISTANCE_WHEN_NOT_SCARED; ++y){
                    for (int x = spawnPoint.x - MAX_DISTANCE_WHEN_NOT_SCARED; x <= spawnPoint.x + MAX_DISTANCE_WHEN_NOT_SCARED; ++x){
                        notAfraidArea.add(new DiscreteCoordinates(x, y));
                    }
                }
                int randomInt = RandomGenerator.getInstance().nextInt(notAfraidArea.size());
                targetPos = new DiscreteCoordinates(notAfraidArea.get(randomInt).x, notAfraidArea.get(randomInt).y);
                targetPath = computeShortestPath(targetPos);
                //System.out.println("Distance between start & targetPos: " + DiscreteCoordinates.distanceBetween(spawnPoint, targetPos));
            }
        }
        else{
            //Go to any cell within spawnPoint + MAX_DISTANCE_WHEN_SCARED
            List<DiscreteCoordinates> isAfraidArea = new LinkedList<>();
            for (int y = spawnPoint.y - MAX_DISTANCE_WHEN_SCARED; y <= spawnPoint.y + MAX_DISTANCE_WHEN_SCARED; ++y){
                for (int x = spawnPoint.x - MAX_DISTANCE_WHEN_SCARED; x <= spawnPoint.x + MAX_DISTANCE_WHEN_SCARED; ++x){
                    isAfraidArea.add(new DiscreteCoordinates(x, y));
                }
            }
            int randomInt = RandomGenerator.getInstance().nextInt(isAfraidArea.size());
            targetPos = new DiscreteCoordinates(isAfraidArea.get(randomInt).x, isAfraidArea.get(randomInt).y);
            targetPath = computeShortestPath(targetPos);
            //System.out.println("Ghost is Afraid -> targetPos:" + targetPath);
        }

        /*
        //If Inky is scared
        if (isAfraid()){
            //Go to any cell within spawnPoint + MAX_DISTANCE_WHEN_SCARED
            List<DiscreteCoordinates> isAfraidArea = new LinkedList<>();
            for (int y = spawnPoint.y - MAX_DISTANCE_WHEN_SCARED; y <= spawnPoint.y + MAX_DISTANCE_WHEN_SCARED; ++y){
                for (int x = spawnPoint.x - MAX_DISTANCE_WHEN_SCARED; x <= spawnPoint.x + MAX_DISTANCE_WHEN_SCARED; ++x){
                    isAfraidArea.add(new DiscreteCoordinates(x, y));
                }
            }
            int randomInt = RandomGenerator.getInstance().nextInt(isAfraidArea.size());
            targetPos = new DiscreteCoordinates(isAfraidArea.get(randomInt).x, isAfraidArea.get(randomInt).y);
            targetPath = computeShortestPath(targetPos);
            System.out.println("Ghost is Afraid -> targetPos:" + targetPath);
        }

        //If Inky not scared
        else{
            //If player unidentified
            if (player==null){
                List<DiscreteCoordinates> notAfraidArea = new LinkedList<>();
                for (int y = spawnPoint.y - MAX_DISTANCE_WHEN_NOT_SCARED; y <= spawnPoint.y + MAX_DISTANCE_WHEN_NOT_SCARED; ++y){
                    for (int x = spawnPoint.x - MAX_DISTANCE_WHEN_NOT_SCARED; x <= spawnPoint.x + MAX_DISTANCE_WHEN_NOT_SCARED; ++x){
                        notAfraidArea.add(new DiscreteCoordinates(x, y));
                    }
                }
                int randomInt = RandomGenerator.getInstance().nextInt(notAfraidArea.size());
                targetPos = new DiscreteCoordinates(notAfraidArea.get(randomInt).x, notAfraidArea.get(randomInt).y);
                targetPath = computeShortestPath(targetPos);
                System.out.println("Ghost is not Afraid + player == null -> targetPos:" + targetPos);
            }

            //If player identified, targetPos = playerPosition
            else{
                targetPos = new DiscreteCoordinates((int)player.getPosition().x, (int)player.getPosition().y);
                targetPath = computeShortestPath(targetPos);
                System.out.println("Ghost is not Afraid + player != null -> targetPos:" + targetPos);
            }
        }
         */
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
