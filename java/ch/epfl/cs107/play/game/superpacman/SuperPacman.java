package ch.epfl.cs107.play.game.superpacman;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.rpg.RPG;
import ch.epfl.cs107.play.game.rpg.actor.Player;
import ch.epfl.cs107.play.game.superpacman.actor.SuperPacmanPlayer;
import ch.epfl.cs107.play.game.superpacman.area.Level0;
import ch.epfl.cs107.play.game.superpacman.area.Level1;
import ch.epfl.cs107.play.game.superpacman.area.Level2;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.game.tutosSolution.actor.GhostPlayer;
import ch.epfl.cs107.play.game.tutosSolution.area.tuto2.Ferme;
import ch.epfl.cs107.play.game.tutosSolution.area.tuto2.Village;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

public class SuperPacman  extends RPG {
    public final static float CAMERA_SCALE_FACTOR = 17.f;
    public final static float STEP = 0.05f;
    private static boolean isGameEnd = false;
    public SuperPacmanPlayer player;

    private final String[] areas = {"superpacman/Level0", "superpacman/Level1", "superpacman/Level2"};
    private int areaIndex;

    /**
     * Add all the areas
     */
    private void createAreas(){
        addArea(new Level0());
        addArea(new Level1());
        addArea(new Level2());
    }

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            createAreas();
            areaIndex = 0;

            Area area = setCurrentArea(areas[areaIndex], true);
            player = new SuperPacmanPlayer(area, Orientation.RIGHT, ((SuperPacmanArea)area).getSpawnPoint());
            super.initPlayer(player);
            return true;
        }
        return false;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if(player.getHp()<= 0){
            if (!isGameEnd) {
                System.out.println("Game over, Pacman ran out of lives");
                System.out.println("Your final score was: " + player.getScore());
                System.exit(0);
            }
        }
        //Ghost interaction checker
        if (player.isInvincible()) {
            getCurrentArea().scareGhosts();
        } else {
            getCurrentArea().unscareGhosts();
            //if (!(player.getHp() <= 0)) {
                if (!player.getPacmanAlive()) {
                    //Reset the level here
                    player.respawnPacman();
                    getCurrentArea().resetGhosts();
                }
            //}
        }
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

}