package ch.epfl.cs107.play.game.tutosSolution;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.superpacman.actor.SuperPacmanPlayer;
import ch.epfl.cs107.play.game.superpacman.area.Level1;
import ch.epfl.cs107.play.game.superpacman.area.Level2;
import ch.epfl.cs107.play.game.tutosSolution.actor.GhostPlayer;
import ch.epfl.cs107.play.game.tutosSolution.area.tuto2.Ferme;
import ch.epfl.cs107.play.game.tutosSolution.area.tuto2.Village;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

public class Tuto2 extends AreaGame {
	public final static float CAMERA_SCALE_FACTOR = 13.f;
	public final static float STEP = 0.05f;


	private GhostPlayer player;
	private SuperPacmanPlayer pacman;
	private final String[] areas = {"zelda/Route", "zelda/Ferme"};
	private final DiscreteCoordinates[] startingPositions = {new DiscreteCoordinates(2,10), 
															 new DiscreteCoordinates(4,10)};

	private int areaIndex;
	/**
	 * Add all the areas
	 */
	private void createAreas(){

		addArea(new Ferme());
		addArea(new Level2());

	}

	@Override
	public boolean begin(Window window, FileSystem fileSystem) {


		if (super.begin(window, fileSystem)) {

			createAreas();
			areaIndex = 0;
			Area area = setCurrentArea(areas[areaIndex], true);
			player = new GhostPlayer(area, Orientation.DOWN, startingPositions[areaIndex],"ghost.1");
			//pacman = new SuperPacmanPlayer(getCurrentArea(), Orientation.RIGHT, new DiscreteCoordinates(10,1));

			area.registerActor(player);
			area.setViewCandidate(player);
			return true;
		}
		return false;
	}

	@Override
	public void update(float deltaTime) {
		if(player.isWeak()){
			switchArea();         
		}
		super.update(deltaTime);

	}

	@Override
	public void end() {
	}

	@Override
	public String getTitle() {
		return "Tuto2";
	}

	protected void switchArea() {

		player.leaveArea();

		areaIndex = (areaIndex==0) ? 1 : 0;

		Area currentArea = setCurrentArea(areas[areaIndex], false);
		player.enterArea(currentArea, startingPositions[areaIndex]);

		player.strengthen();
	}

}
