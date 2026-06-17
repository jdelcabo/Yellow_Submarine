package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.DecorationTimer;
import com.mygdx.game.GamePage;
import com.mygdx.game.GifDecoder;
import com.mygdx.game.SuefaceEnemyTimer;
import com.mygdx.game.actors.BackgroundImage;
import com.mygdx.game.actors.Boat;
import com.mygdx.game.actors.SeaWeedL;
import com.mygdx.game.actors.SeaWeedS;
import com.mygdx.game.actors.Shark;
import com.mygdx.game.actors.Ship;
import com.mygdx.game.actors.Submarine;

import java.util.Timer;

//Reasons why the submarine could be dead
enum DeadReason {
    SHARK_COLLISION,
    LACK_OF_OXIGEN,
    BOAT_CRASHED,
    SHIP_CRASHED
}


public class GameScreen implements Screen, InputProcessor {
    private GamePage rootManager;
    private Stage gameStage;
    private BackgroundImage backgroundImage;
    private Submarine yellowSubmarine;
    private TextButton deadMessage;
    private ProgressBar currentOxigenLevel;
    private Shark shark;
    private Ship ship;
    private Boat boat;
    private SeaWeedL seaWeedL;
    private SeaWeedS seaWeedS;
    private long lastdecoTime;

    private boolean gameOver = false;
    private int row_height = Gdx.graphics.getWidth() / 12;
    private int col_width = Gdx.graphics.getWidth() / 12;
    private Actor objectsCollection[];
    private Timer time;
    private Skin goldenStructureTexturePack, pixeledThemeStructurePack;

    private DecorationTimer dt;
    private Music deadSound, sonarPing, recoveringOxigen;
    private boolean isFingerPressingTheScreen = false, submarineVerticalMovementDirection; //true means upwards and false downwards
    private Label currentScore;
    private SpriteBatch batch;
    private Animation<TextureRegion> animation;
    float elapsed;
    private int counter = 0;

    //Constructor
    public GameScreen(GamePage rootManager) {
        this.rootManager = rootManager;

    }

    //Screen methods
    @Override
    public void show() {
        //Create necessary classes
        gameStage = new Stage();

        batch = new SpriteBatch();
        animation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("animations/explotion.gif").read());


        /*deadSound = Gdx.audio.newMusic(Gdx.files.internal("sounds/power_down.mp3"));
        deadSound.setVolume(1.0f);
        deadSound.setLooping(false);*/

        sonarPing = Gdx.audio.newMusic(Gdx.files.internal("sounds/sonar_ping.mp3"));
        sonarPing.setLooping(true);
        sonarPing.setVolume(1.0f);

        recoveringOxigen = Gdx.audio.newMusic(Gdx.files.internal("sounds/bubbles.wav"));
        recoveringOxigen.setLooping(true);
        recoveringOxigen.setVolume(1.0f);

        if(rootManager.gameSound) {
            sonarPing.play();
        }


        time = new Timer();
        //Actors
        yellowSubmarine = new Submarine(rootManager);
        shark = new Shark();
        backgroundImage = new BackgroundImage(rootManager.screenSizeX, rootManager.screenSizeY);
        yellowSubmarine.setTouchable(Touchable.disabled);

        //Load texture packs
        goldenStructureTexturePack = new Skin(Gdx.files.internal("golden-spiral/skin/golden-ui-skin.json"));
        pixeledThemeStructurePack = new Skin(Gdx.files.internal("pixthulhu/skin/pixthulhu-ui.json"));

        //Oxigen manager
        currentOxigenLevel = new ProgressBar(0, 100, 0.07f, true, goldenStructureTexturePack,"mana-orb");
        currentOxigenLevel.setSize(rootManager.screenSizeX*0.15f , rootManager.screenSizeY*0.15f);
        currentOxigenLevel.setPosition((rootManager.screenSizeX - (rootManager.screenSizeX*0.15f)), (rootManager.screenSizeY - (rootManager.screenSizeY*0.18f)));
        currentOxigenLevel.setTouchable(Touchable.disabled);
        currentOxigenLevel.setValue(currentOxigenLevel.getMaxValue());

        //Preload the dead message in case of needing it
        deadMessage = new TextButton("Game over... Go back to main menu?",pixeledThemeStructurePack,"default");
        deadMessage.setSize(col_width*7,row_height*2);
        deadMessage.setPosition(col_width*2.5f, row_height*2.5f);
        deadMessage.align(Align.center);
        deadMessage.setTouchable(Touchable.enabled);
        deadMessage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sonarPing.stop();
                // Change the screen to the game screen
                rootManager.setScreen(new MenuScreen(rootManager));
            }
        });

        //label that represents the user's current game score
        //Setting 1 description
        currentScore = new Label("0",pixeledThemeStructurePack,"subtitle");
        currentScore.setSize(col_width*4,row_height*2);
        currentScore.setPosition(col_width*0f,row_height*6f);
        currentScore.setAlignment(Align.center);
        currentScore.setTouchable(Touchable.disabled);
        currentScore.setWrap(true);

        //Add actors to stage
        gameStage.addActor(backgroundImage);
        gameStage.addActor(yellowSubmarine);
        gameStage.addActor(shark);
        gameStage.addActor(currentOxigenLevel);
        gameStage.addActor(currentScore);

        // 一定間隔で処理を開始する
        // SampleTaskを、3秒後に、5秒間隔で実行する
        time.scheduleAtFixedRate(new DecorationTimer(gameStage, this), 0, 500);

        // 一定間隔で処理を開始する
        // SampleTaskを、3秒後に、5秒間隔で実行する
        time.scheduleAtFixedRate(new SuefaceEnemyTimer(gameStage, this, rootManager), 0, 1000);

        //Implement the own input processor
        Gdx.input.setInputProcessor(this);

    }

    @Override
    public void render(float delta) {
        //Render backogrund color by default
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        elapsed += Gdx.graphics.getDeltaTime();

        //For each frame, dicrease the oxigen level
        currentOxigenLevel.setValue(currentOxigenLevel.getValue() - currentOxigenLevel.getStepSize());

        //If current oxigen level is equals or below 0, submarin must be dead.
        if (currentOxigenLevel.getValue() == 0 && !yellowSubmarine.isDead()) {
            //Perform dead event for lack of oxigen case
            deadEvent(DeadReason.LACK_OF_OXIGEN);
        }

        //If user's touches the half-top device's screen, move submarine up. In the contrary, move it down (By default, because of gravity the submarine sinks
        if (isFingerPressingTheScreen){
            if (submarineVerticalMovementDirection) {
                yellowSubmarine.moveUp();
            } else {
                yellowSubmarine.moveDown();
            }
        }

        //In case of having rendered any enemy, check if has collided with the submarine. On that case, perform dead event
        if (objectsCollection != null){
            for (int i = 0; i < objectsCollection.length; i++){
                if (objectsCollection[i] != null) {
                    if (objectsCollection[i].toString().equalsIgnoreCase("shark") && !yellowSubmarine.isDead()) {
                        if (yellowSubmarine.getCollisionDimensions().overlaps(((Shark) objectsCollection[i]).getCollisionDimensions())) {
                            // Actors have collided
                            deadEvent(DeadReason.SHARK_COLLISION);

                        }
                    } else if (objectsCollection[i].toString().equalsIgnoreCase("boat") && !yellowSubmarine.isDead()) {
                        if (yellowSubmarine.getCollisionDimensions().overlaps(((Boat) objectsCollection[i]).getCollisionDimensions())) {
                            // Actors have collided
                            deadEvent(DeadReason.BOAT_CRASHED);

                        }
                    } else if (objectsCollection[i].toString().equalsIgnoreCase("ship") && !yellowSubmarine.isDead()) {
                        if (yellowSubmarine.getCollisionDimensions().overlaps(((Ship) objectsCollection[i]).getCollisionDimensions())) {
                            // Actors have collided
                            deadEvent(DeadReason.SHIP_CRASHED);

                        }
                    }

                }
            }
        }

        //If submarine is on the surface, recover oxigen level
        if (yellowSubmarine.getY() >= rootManager.screenSizeY*0.67f){
            if (rootManager.gameSound) {
                recoveringOxigen.play();
            }
            currentOxigenLevel.setValue(currentOxigenLevel.getValue() + currentOxigenLevel.getStepSize()*8);
        } else {
            recoveringOxigen.stop();
        }

        //Display game interactions on the screen
        gameStage.act(delta);

        currentScore.setText("Score: " + rootManager.currentScore);

        gameStage.draw();

        if (yellowSubmarine.isDead() && counter<15){
            batch.begin();
            batch.draw(animation.getKeyFrame(elapsed), yellowSubmarine.getX(), yellowSubmarine.getY());
            batch.end();

            counter++;
        }

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        sonarPing.stop();
        recoveringOxigen.stop();
    }

    @Override
    public void resume() {

    }

    //añadir balbula como sonido para cuando el submarino salga a la superficie

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        //In case of disposing this screen, remove all current objects
        for (int i = 0; i < objectsCollection.length; i++){
            objectsCollection[i].remove();
        }
        batch.dispose();
        recoveringOxigen.dispose();
        sonarPing.dispose();
        gameStage.dispose();
        //deadSound.dispose();
    }

    //Input processor methods
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    public void generateDeco(){

        int genNum = (int)Math.ceil(Math.random() * 2);

        switch (genNum){
            case 1:
                seaWeedL = new SeaWeedL();
                gameStage.addActor(seaWeedL);
                break;
            case 2:
                seaWeedS = new SeaWeedS();
                gameStage.addActor(seaWeedS);
                break;
        }

//        shark = new Shark();
//        gameStage.addActor(shark);
        shark = new Shark();

        gameStage.addActor(shark);

        objectsCollection = gameStage.getActors().toArray();
    }

    public void generateEnemyInTheSea(){

        int genNum = (int)Math.ceil(Math.random() * 2);

        switch (genNum){
            case 1:
                seaWeedL = new SeaWeedL();
                gameStage.addActor(seaWeedL);
                break;
            case 2:
                seaWeedS = new SeaWeedS();
                gameStage.addActor(seaWeedS);
                break;
        }

//        shark = new Shark();
//        gameStage.addActor(shark);
        shark = new Shark();

        gameStage.addActor(shark);

        objectsCollection = gameStage.getActors().toArray();
    }

    public void generateEnemySurFace(){

        int genNum = (int)Math.ceil(Math.random() * 2);

        switch (genNum){
            case 1:
                ship = new Ship();
                gameStage.addActor(ship);
                break;
            case 2:
                boat =  new Boat();
                gameStage.addActor(boat);
                break;
        }

        objectsCollection = gameStage.getActors().toArray();
    }

    private void deadEvent(DeadReason deadReason){
//        deadSound.play();
        //Stop everything if main character is dead.
        time.cancel();
        yellowSubmarine.setDead(true);

        Gdx.input.setInputProcessor(null);
        Gdx.input.setInputProcessor(gameStage);

        adaptiveDeadMessage(deadReason);
        gameStage.addActor(deadMessage);

    }

    //Print one or another message depending on the dead reason
    private void adaptiveDeadMessage(DeadReason deadReason){
        switch (deadReason){
            case  SHARK_COLLISION:
                deadMessage.setText("A shark broke the submarine!\n" + deadMessage.getText());
                break;
            case LACK_OF_OXIGEN:
                deadMessage.setText("The sailors run out of oxigen!\n" + deadMessage.getText());
                break;
            case BOAT_CRASHED:
                deadMessage.setText("Our ship crashed into a boat!\n" + deadMessage.getText());
                break;
            case SHIP_CRASHED:
                deadMessage.setText("The submarine crashed into a ship!\n" + deadMessage.getText());
                break;
        }
    }

    //Method to know if user is pressing half-top or half-bottom screen and do the corresponding action
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        isFingerPressingTheScreen = true;

        if (screenY <= (rootManager.screenSizeY/2)){
            submarineVerticalMovementDirection = true;
        } else {
            submarineVerticalMovementDirection = false;
        }

        return true;
    }

    //Know when is user has stop pressing the screen
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        isFingerPressingTheScreen = false;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

}