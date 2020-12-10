package ch.epfl.cs107.play.game.superpacman.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

public class Level0 extends SuperPacmanArea{

    @Override
    public String getTitle() {
        return "superpacman/Level0";
    }

    @Override
    protected void createArea() {
        System.out.println("Creating Area inside Level0.java");
        super.createArea();
    }

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        return super.begin(window, fileSystem);
    }
}
