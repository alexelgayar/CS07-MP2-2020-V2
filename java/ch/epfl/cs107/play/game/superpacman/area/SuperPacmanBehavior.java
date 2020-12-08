package ch.epfl.cs107.play.game.superpacman.area;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.superpacman.SuperPacman;
import ch.epfl.cs107.play.game.tutosSolution.Tuto2Behavior;
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

   protected void registerActors(Area area){

        area.registerActor(Actor a);



    }

    public class SuperPacmanCell extends AreaBehavior.Cell {


        private final SuperPacmanCellType type;

        SuperPacmanCell(int x, int y, SuperPacmanCellType type){
            super(x,y);
            this.type = type;

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
