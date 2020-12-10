package ch.epfl.cs107.play.game.superpacman;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.rpg.RPG;
import ch.epfl.cs107.play.game.rpg.actor.Player;
import ch.epfl.cs107.play.game.superpacman.actor.SuperPacmanPlayer;
import ch.epfl.cs107.play.game.superpacman.area.Level0;
import ch.epfl.cs107.play.game.tutosSolution.actor.GhostPlayer;
import ch.epfl.cs107.play.game.tutosSolution.area.tuto2.Ferme;
import ch.epfl.cs107.play.game.tutosSolution.area.tuto2.Village;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

public class SuperPacman  extends RPG {
    public final static float CAMERA_SCALE_FACTOR = 15.f;
    public final static float STEP = 0.05f;

    private SuperPacmanPlayer player;

    private final String[] areas = {"superpacman/background.png"};
    private final DiscreteCoordinates[] startingPositions = {new DiscreteCoordinates(2,10)};

    private int areaIndex;

    /**
     * Add all the areas
     */
    private void createAreas(){
        addArea(new Level0());
    }

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        System.out.println("Testing begin of SuperPacman");
        if (super.begin(window, fileSystem)) {
            System.out.println("Testing window start of SuperPacman");
            createAreas();
            areaIndex = 0;
            Area area = setCurrentArea(areas[areaIndex], true);
            player = new SuperPacmanPlayer(area, Orientation.DOWN, startingPositions[areaIndex]);
            initPlayer(player); //does this need to be removed?
            return true;
        }
        return false;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public String getTitle() {
        return "Super Pac-Man";
    }

    @Override
    public void end() {
        super.end();
    }

    @Override
    protected Player getPlayer() {
        return super.getPlayer();
    }

    @Override
    protected void initPlayer(Player player) {
        initPlayer(player);
    }
}