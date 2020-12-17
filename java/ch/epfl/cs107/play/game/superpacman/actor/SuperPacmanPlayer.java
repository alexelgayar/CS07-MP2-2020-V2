package ch.epfl.cs107.play.game.superpacman.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.*;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.rpg.actor.Player;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.game.superpacman.SuperPacman;
import ch.epfl.cs107.play.game.superpacman.SuperPacmanStatusGUI;
import ch.epfl.cs107.play.game.superpacman.area.Level1;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanBehavior;
import ch.epfl.cs107.play.game.superpacman.handler.GhostInteractionVisitor;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.nio.channels.ClosedSelectorException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

public class SuperPacmanPlayer extends Player {
    private int hp;
    private int score;
    private float invulnerabilityTimer = 0.f;
    private boolean isPacmanAlive;
    private Sprite sprite;
    private SuperPacmanStatusGUI gui;
    private final static int SPEED = 6;     //Movement speed of player, duration in frame number
    private final float INVULNERABLE_TIME = 10.f;
    private SuperPacmanPlayerHandler handler;
    private final int GHOST_SCORE = 500;
    private final float SPEED_TIME = 8.f;
    private float speedTimer = 0.f;
    private final static int SPEED_BOOST = 2;
    private Orientation desiredOrientation = Orientation.RIGHT;

    Sprite[][] sprites = RPGSprite.extractSprites("superpacman/pacman", 4, 1, 1,
            this, 64, 64, new Orientation[] {Orientation.DOWN, Orientation.LEFT, Orientation.UP, Orientation.RIGHT});

    Animation[] animations = Animation.createAnimations(2, sprites);

    Animation animation = animations[3];

    /**
     * Returns the score obtained by the player
     * @return (int): Returns the final score of the player
     */
    public int getScore(){
        return this.score;
    }

    /**
     * Returns the HP of the Pac-Man player
     * @return (int): Returns the hp of the Pac-Man player
     */
    public int getHp(){
        return this.hp;
    }

    @Override
    public void draw(Canvas canvas) {
        animation.draw(canvas);
        gui.draw(canvas);
    }

    public SuperPacmanPlayer(Area area, Orientation orientation, DiscreteCoordinates coordinates){
        super(area, orientation, coordinates);
        handler = new SuperPacmanPlayerHandler();
        isPacmanAlive = true;
        this.hp=3;
        this.score=0;
        gui = new SuperPacmanStatusGUI(this);

       // resetMotion();
    }

    /**
     * Gets the nextOrientation that Pac-Man must follow, based on the user keyboard input
     * @param b (Button): The integer to input
     * @param orientation (Orientation): Links the orientation to the keyboard key
     */
    private void getOrientation(Button b, Orientation orientation){

        if(b.isDown()){
            desiredOrientation = orientation;
        }
    }

    /**
     * Returns the isPacmanAlive boolean, which verifies whether or not Pacman was killed
     * @return (boolean): Returns isPacmanAlive
     */
    public boolean getPacmanAlive(){
        return isPacmanAlive;
    }

    /**
     * Sets the isPacmanAlive boolean to true (which indicates that pacman was successfully respawned)
     */
    public void setPacmanAlive(){
        isPacmanAlive = true;
    }

    @Override
    public void update(float deltaTime) {

        Keyboard keyboard = getOwnerArea().getKeyboard();


        /*Here we will check for interactions between ghost and player
            If player touches ghost and invulnerabilityTimer = 0 =>
                - Player loses hp: hp -= 1;
                - Reset map + ghosts
                - Unregister Player and Re-register at spawn position
            If player touches ghost and invulnerabilityTimer > 0 =>
                - Player Score += GHOST_SCORE
                - Ghost spawns back at its spawn position
         */
        if (invulnerabilityTimer > 0) {
            invulnerabilityTimer -= deltaTime;
        }

        if(speedTimer > 0){
            speedTimer -= deltaTime;
        }

        if(hp > 5){
            hp = 5;
        }

        if(speedTimer == 0){
            speedTimer = 0;
        }

        if (invulnerabilityTimer < 0){
            invulnerabilityTimer = 0;
        }

        getOrientation(keyboard.get(Keyboard.LEFT), Orientation.LEFT);
        getOrientation(keyboard.get(Keyboard.RIGHT), Orientation.RIGHT);
        getOrientation(keyboard.get(Keyboard.UP), Orientation.UP);
        getOrientation(keyboard.get(Keyboard.DOWN), Orientation.DOWN);

        if(isDisplacementOccurs()){
            animation = animations[getOrientation().ordinal()];
            animation.update(deltaTime);
        }
        else{animation.reset();}

        if(!isDisplacementOccurs()){
               if(getOwnerArea().canEnterAreaCells(this, Collections.singletonList(getCurrentMainCellCoordinates() .jump(desiredOrientation.toVector()))))
            {
                orientate(desiredOrientation);
            }
               if(speedTimer > 0){
                   move(SPEED_BOOST);
               }
               else {
                   move(SPEED);
               }
        }

        super.update(deltaTime);
    }


    /**
     * Leave an area by unregistering this player
     */
    public void leaveArea(){
        getOwnerArea().unregisterActor(this);
    }

    /**
     * Method which resets and respawns pacman to his spawn position if he is killed
     */
    public void respawnPacman(){
        setPacmanAlive();
        this.hp -= 1;


        getOwnerArea().leaveAreaCells(this, this.getEnteredCells());
        this.setCurrentPosition((((SuperPacmanArea)getOwnerArea()).getSpawnPoint()).toVector());
        getOwnerArea().enterAreaCells(this, this.getCurrentCells());
        resetMotion();
    }

    /**
     * Methods checks whether Pac-Man has an active invulnerability timer, in which case he is invincible
     * @return (boolean): Returns whether Pac-Man is invincible against ghosts or not
     */
    public boolean isInvincible(){
        return invulnerabilityTimer > 0.f;
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
        //return null;
    }

    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public boolean isCellInteractable() {
        return false;
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((GhostInteractionVisitor)v).interactWith(this);
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return null;
    }

    @Override
    public boolean wantsCellInteraction() {
        return true;
    }

    @Override
    public boolean wantsViewInteraction() {
        return false;
    }

    @Override
    public void interactWith(Interactable other) {

        other.acceptInteraction(handler);

    }

    /**
     * Handler class for SuperPacmanPlayer, which deals with specific (non-default) interactions between player and interactable objects
     */
    private class SuperPacmanPlayerHandler implements SuperPacmanInteractionVisitor {

        @Override
        public void interactWith(Door door) {
            setIsPassingADoor(door);
        }

        /**
         * If Pacman interacts with a Cherry, unregister cherry and increase score
         */
       public void interactWith(Cherry cherry){
            cherry.collect();
            score += 200;
        }

        /**
         * If Pacman interacts with a diamond, unregister diamond and increase score
         */
        public void interactWith(Diamond diamond){
            diamond.collect();
            score += 10;

            ((SuperPacmanArea)getOwnerArea()).countDiamonds();

        }

        /**
         * If Pacman interacts with a Cherry, unregister potion and set pacMan speedboost timer
         */
        @Override
        public void interactWith(Potion potion) {
            potion.collect();
            speedTimer = SPEED_TIME;
        }

        /**
         * If Pacman interacts with a Heart, unregister heart and increase hp by 1
         */
        @Override
        public void interactWith(Heart heart) {
            heart.collect();
             hp += 1;
        }

        /**
         * If Pacman touches a projectile, unregister projectile and kill pacman
         */
        @Override
        public void interactWith(Projectile projectile) {
            projectile.unregisterProjectile();
            isPacmanAlive = false;
        }

        /**
         * If Pacman interacts with a Bonus, unregister bonus and set a pacman invincibilityTimer
         */
        public void interactWith(Bonus bonus){
            bonus.collect();
            invulnerabilityTimer = INVULNERABLE_TIME;
            //Set the ghosts to afraid here
            //=> How do the ghosts check whether invulnerability time > 0?
        }

        /**
         * Method which, upon contact of player and ghost, either eats ghost (if pacman invincible) or kills player (if not invincible)
         * @param ghost (Ghost): The ghost which has accepted interaction of player
         */
        public void interactWith(Ghost ghost){
            if (ghost.isAfraid()){
                ghost.eatGhost();
                score += GHOST_SCORE;
            }
            else{
                isPacmanAlive = false;
            }
        }

        /**
         * If Pacman collides head-on with a sawblade, then kill pacman
         */
        @Override
        public void interactWith(SawBlade sawBlade) {
            isPacmanAlive = false;
        }

        /**
         * If Pacman collects a key, collect the key and initiate signal for gates (using isCollected).
         */
        public void interactWith(Key key){
            key.collect();
            key.setKeyCollected();
        }
    }




}
