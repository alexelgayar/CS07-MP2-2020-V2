package ch.epfl.cs107.play.game.superpacman.area;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.superpacman.SuperPacman;
import ch.epfl.cs107.play.game.superpacman.actor.Wall;
import ch.epfl.cs107.play.game.tutosSolution.Tuto2Behavior;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

public class SuperPacmanBehavior extends AreaBehavior {

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


    public SuperPacmanBehavior(Window window, String name) {
        super(window, name);
        int height = getHeight();
        int width = getWidth();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
              SuperPacmanCellType color = SuperPacmanCellType.toType(getRGB(height - 1 - y, x));
                setCell(x, y, new SuperPacmanCell(x, y, color));
            }
        }
    }

    private Wall wall;

   protected void registerActors(Area area){
        System.out.println("Registering the Level:" + area);
        for(int y = 0; y< getHeight(); y++){
            for(int x = 0; x< getWidth(); x++){
                if(((SuperPacmanCell)getCell(x,y)).type == SuperPacmanCellType.WALL){
                    boolean[][] neighbourhood = new boolean[3][3];
                    for(int h = -1; h <= 1 ; ++h) {
                        for (int w = -1; w <= 1; ++w) {
                            if ((x + w) > 0 && (x + w) < getWidth() && (y+h) > 0 && (y+h) < getHeight() && ((SuperPacmanCell) getCell(x + w , y + h )).type == SuperPacmanCellType.WALL) {
                                neighbourhood[w + 1][y + 1] = true;
                            }
                        }
                    }
                    area.registerActor(new Wall(area, new DiscreteCoordinates(x,y), neighbourhood));
                }
            }
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
            if(entity.takeCellSpace() == false){ return true; }
            else return false;
        }

        public boolean canLeave(Interactable entity){
            return true; //to change
        }

    }
}
