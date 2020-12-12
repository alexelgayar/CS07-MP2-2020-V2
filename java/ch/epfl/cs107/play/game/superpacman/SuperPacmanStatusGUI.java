package ch.epfl.cs107.play.game.superpacman;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.game.superpacman.actor.SuperPacmanPlayer;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
import java.awt.Color;

import static java.awt.Color.YELLOW;


public class SuperPacmanStatusGUI implements Graphics {

private SuperPacmanPlayer player;
private int ptnMax = 5;
private int couleur;

public SuperPacmanStatusGUI(SuperPacmanPlayer player){
    this.player = player;

}


    public void draw(Canvas canvas) {

        float width = canvas.getScaledWidth();
        float height = canvas.getScaledHeight();
        Vector anchor = canvas.getTransform().getOrigin().sub(new Vector(width/2, height/2));

      //  if(player.getHp())
        ImageGraphics[] life = new ImageGraphics[5];

        for(int j = 0; j < ptnMax; ++j){

            if(player.getHp() > j)
                couleur= 0;
            else couleur= 64;

            life[j]= new ImageGraphics(ResourcePath.getSprite("superpacman/lifeDisplay"), 1.f, 1.f, new RegionOfInterest(couleur, 0, 64, 64),
                    anchor.add(new Vector(0.5f + j, height - 1.375f)), 1, 1005);

            life[j].draw(canvas);
        }
        TextGraphics score = new TextGraphics("Score: " + player.getScore() , 0.75f, Color.YELLOW, Color.BLACK, 0.09f , true , false , anchor.add(new Vector(ptnMax + 1, height - 1.100f)));
        score.draw(canvas);





    }


}
