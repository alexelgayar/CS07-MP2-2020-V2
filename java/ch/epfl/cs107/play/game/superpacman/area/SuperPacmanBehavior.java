package ch.epfl.cs107.play.game.superpacman.area;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.areagame.AreaGraph;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.superpacman.SuperPacman;
import ch.epfl.cs107.play.game.superpacman.actor.*;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.game.tutosSolution.Tuto2Behavior;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

import java.util.LinkedList;
import java.util.List;

public class SuperPacmanBehavior extends AreaBehavior {
    //Create a ghost array?
    //Ghost[] ghosts = {};
    private List<Ghost> registeredGhosts;
    private AreaGraph areaGraph;

    public enum SuperPacmanCellType {

        NONE(0),
        WALL(-16777216),
        FREE_WITH_DIAMOND(-1),
        FREE_WITH_BLINKY(-65536),
        FREE_WITH_PINKY(-157237),
        FREE_WITH_INKY(-16724737),
        FREE_WITH_CHERRY(-36752),
        FREE_WITH_BONUS(-16478723),
        FREE_EMPTY(-6118750);


        final int type;

        SuperPacmanCellType(int type) {
            this.type = type;
        }

        public static SuperPacmanCellType toType(int type) {
            for (SuperPacmanCellType ict : SuperPacmanCellType.values()) {
                if (ict.type == type)
                    return ict;
            }
            return NONE;

        }

    }

    //SuperPacmanBehavior Constructor class
    public SuperPacmanBehavior(Window window, String name) {
        super(window, name);
        registeredGhosts = new LinkedList<>();
        areaGraph = new AreaGraph();

        int height = getHeight();
        int width = getWidth();

        //Scan through all the cells in the behaviorMap of the game
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                //Set the cells
                SuperPacmanCellType color = SuperPacmanCellType.toType(getRGB(height - 1 - y, x));
                setCell(x, y, new SuperPacmanCell(x, y, color));


            }
        }

        //Once all cells are set, construct the areaGraph for the behaviour
        for(int y = 0; y< getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                if (((SuperPacmanCell) getCell(x, y)).type != SuperPacmanCellType.WALL) {
                    areaGraph.addNode(new DiscreteCoordinates(x,y), hasLeftEdge(x,y), hasUpEdge(x,y), hasRightEdge(x,y), hasDownEdge(x,y));
                }
            }
        }

        System.out.println("AreaGraph: " + areaGraph.toString());
    }

    private boolean hasLeftEdge(int x, int y){
        return (x > 0 && (((SuperPacmanCell) getCell(x-1,y)).type != SuperPacmanCellType.WALL));
    }
    private boolean hasUpEdge(int x, int y){
        return (y < getHeight() - 1 && (((SuperPacmanCell) getCell(x,y+1)).type != SuperPacmanCellType.WALL));
    }
    private boolean hasRightEdge(int x, int y){
        return (x < getWidth() - 1 && (((SuperPacmanCell) getCell(x+1,y)).type != SuperPacmanCellType.WALL));
    }
    private boolean hasDownEdge(int x, int y){
        return (y > 0 && (((SuperPacmanCell) getCell(x,y-1)).type != SuperPacmanCellType.WALL));
    }

    private Wall wall;
    private  int diamondCounter= 0;


    protected void registerActors(Area area){
        for(int y = 0; y< getHeight(); y++){
            for(int x = 0; x< getWidth(); x++){

                //Register wall cells
                if(((SuperPacmanCell)getCell(x,y)).type == SuperPacmanCellType.WALL){
                    boolean[][] neighbourhood = new boolean[3][3];
                    for(int h = -1; h < 2 ; ++h) {
                        for (int w = -1; w < 2; ++w) {
                            if ((x + w ) >= 0 && (x + w ) < getWidth() && (y+h) >= 0 && (y+h) < getHeight() && ((SuperPacmanCell) getCell(x + w , y + h )).type == SuperPacmanCellType.WALL) {
                                neighbourhood[w + 1 ][1 - h] = true;
                            }
                        }
                    }
                    area.registerActor(new Wall(area, new DiscreteCoordinates(x,y), neighbourhood));
                }

                //Register Diamond Cells
                if(((SuperPacmanCell)getCell(x,y)).type == SuperPacmanCellType.FREE_WITH_DIAMOND){
                    if(area.getTitle().equals("superpacman/Level1") && (x == 1 && y ==1) || (x == 28 && y == 28) || (x == 1 && y == 28) || ( x== 28 && y == 1)){
                        area.registerActor(new Potion(area, Orientation.UP, new DiscreteCoordinates(x,y)));

                    }
                    else {

                        area.registerActor(new Diamond(area, Orientation.UP, new DiscreteCoordinates(x, y)));

                        if (area.getTitle().equals("superpacman/Level2")) {
                            diamondCounter += 1;
                            System.out.println(diamondCounter);
                        }
                    }
                }

                //Register Cherry Cells
                if(((SuperPacmanCell)getCell(x,y)).type == SuperPacmanCellType.FREE_WITH_CHERRY) {
                    area.registerActor(new Cherry(area, Orientation.UP, new DiscreteCoordinates(x, y)));
                }

                //Register Bonus Cells
                if(((SuperPacmanCell)getCell(x,y)).type == SuperPacmanCellType.FREE_WITH_BONUS) {
                    area.registerActor(new Bonus(area, Orientation.UP, new DiscreteCoordinates(x, y)));
                }

                //Register Blinky Cells
                if(((SuperPacmanCell)getCell(x,y)).type == SuperPacmanCellType.FREE_WITH_BLINKY) {
                    System.out.println("Behaviour: Registering Blinky, Ghost count:" + registeredGhosts.size() + 1);
                    Ghost blinky = new Blinky(area, Orientation.RIGHT, new DiscreteCoordinates(x, y));
                    registeredGhosts.add(blinky);
                    area.registerActor(blinky);
                }

                //Register Inky Cells
                if(((SuperPacmanCell)getCell(x,y)).type == SuperPacmanCellType.FREE_WITH_INKY) {
                    System.out.println("Behaviour: Registering Inky, Ghost count:" + registeredGhosts.size() + 1);
                    Ghost Inky = new Inky(area, Orientation.RIGHT, new DiscreteCoordinates(x, y), areaGraph);
                    registeredGhosts.add(Inky);
                    area.registerActor(Inky);
                }

                //Register Pinky Cells
                if(((SuperPacmanCell)getCell(x,y)).type == SuperPacmanCellType.FREE_WITH_PINKY) {
                    System.out.println("Behaviour: Registering Pinky, Ghost count:" + registeredGhosts.size() + 1);
                    Ghost Pinky = new Pinky(area, Orientation.RIGHT, new DiscreteCoordinates(x, y), areaGraph);
                    registeredGhosts.add(Pinky);
                    area.registerActor(Pinky);
                }


            }
       }
    }

    @Override
    protected void scareGhosts() {
        super.scareGhosts();
        if(registeredGhosts.size() > 0) {
            registeredGhosts.get(0).scareGhost();
        }
        else{
            //System.out.println("There are no ghosts");
        }
    }

    @Override
    protected void unscareGhosts() {
        super.unscareGhosts();
        if(registeredGhosts.size() > 0) {
            registeredGhosts.get(0).unscareGhost();
        }
        else{
            //System.out.println("There are no ghosts");
        }
    }

    @Override
    protected void resetGhosts() {
        super.resetGhosts();
        for (Ghost ghost: registeredGhosts){
            System.out.println("Resetting all ghosts");
            ghost.resetGhosts();
        }
    }

    public class SuperPacmanCell extends AreaBehavior.Cell {


        private final SuperPacmanCellType type;

        SuperPacmanCell(int x, int y, SuperPacmanCellType type){
            super(x,y);
            this.type = type;

        }

        @Override
        public boolean isCellInteractable() {
            return true;
        }

        @Override
        public boolean isViewInteractable() {
            return true;
        }

        @Override
        public void acceptInteraction(AreaInteractionVisitor v) {

        }

        public boolean canEnter(Interactable entity){
            return !hasNonTraversableContent();
        }



        public boolean canLeave(Interactable entity){
            return true; //to change
        }

    }
}


