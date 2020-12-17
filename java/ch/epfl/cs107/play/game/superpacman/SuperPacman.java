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
    public final static float CAMERA_SCALE_FACTOR = 45.f;//17.f;
    public final static float STEP = 0.05f;

    public SuperPacmanPlayer player;

    private final String[] areas = {"superpacman/Level0", "superpacman/Level1", "superpacman/Level2"}; //"superpacman/background.png"


    private int areaIndex;

    /**
     * Add all the areas
     */
    private void createAreas(){
        addArea(new Level0()); //new Level => This area always returns null
        addArea(new Level1()); //new Level => This area always returns null
        addArea(new Level2()); //new Level => This area always returns null
    }

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            createAreas();
            areaIndex = 1; //Testing Ghost Spawning

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
        //Ghost interaction checker
        if (player.isInvincible()){
            getCurrentArea().scareGhosts();
        }
        else{
            getCurrentArea().unscareGhosts();
            if (!(player.getHp() <= 0)) {
                if (!player.getPacmanAlive()) {
                    //Reset the level here
                    player.respawnPacman();
                    getCurrentArea().resetGhosts();
                }
            }

                //Game over screen
                //TODO: Extension create a game-over screen
                //getCurrentArea().unregisterActor(player);

        }

        if(player.getHp()<= 0){
            end();
            super.begin(getWindow(),getFileSystem());


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