package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.GamePage;
import com.mygdx.game.actors.BackgroundImage;

public class MenuScreen implements Screen {
    private GamePage rootManager;
    private Stage menuStage;
    private Label title, bestScore;
    private TextButton playButton, settingsButton;
    private BackgroundImage backgroundImage;
    private Music backgroundMusic;

    public MenuScreen(GamePage rootManager) {
        this.rootManager = rootManager;
    }

    @Override
    public void show() {
        menuStage = new Stage();
        Gdx.input.setInputProcessor(menuStage);

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/yellow_submarine_remix.mp3"));
        backgroundMusic.setVolume(1.0f);
        backgroundMusic.setLooping(true);

        if(rootManager.gameSound){
            backgroundMusic.play();
        }

        Skin pixeledThemeStructurePack = new Skin(Gdx.files.internal("pixthulhu/skin/pixthulhu-ui.json"));

        int row_height = Gdx.graphics.getWidth() / 12;
        int col_width = Gdx.graphics.getWidth() / 12;

        //Background image
        backgroundImage = new BackgroundImage(rootManager.screenSizeX, rootManager.screenSizeY);
        backgroundImage.setTouchable(Touchable.disabled);

        //title of the game
        title = new Label("The Yellow Submarine",pixeledThemeStructurePack,"title");
        title.setSize(Gdx.graphics.getWidth(),row_height*2);
        title.setPosition(0,Gdx.graphics.getHeight()-row_height*2);
        title.setAlignment(Align.center);
        title.setTouchable(Touchable.disabled);

        //play button
        playButton = new TextButton("Start",pixeledThemeStructurePack,"default");
        playButton.setSize(col_width*2,row_height);
        playButton.setPosition(col_width*5, Gdx.graphics.getHeight()-row_height*3.5f);
        playButton.align(Align.center);
        playButton.setTouchable(Touchable.enabled);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                rootManager.currentScore = 0;

                // Change the screen to the game screen
                backgroundMusic.stop();
                rootManager.setScreen(new GameScreen(rootManager));
            }
        });

        //Settings button
        settingsButton = new TextButton("Settings",pixeledThemeStructurePack,"default");
        settingsButton.setSize(col_width*2,row_height);
        settingsButton.setPosition(col_width*5, Gdx.graphics.getHeight()-row_height*5);
        settingsButton.align(Align.center);
        settingsButton.setTouchable(Touchable.enabled);
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Change the screen to the game screen
                backgroundMusic.stop();
                rootManager.setScreen(new SettingsScreen(rootManager));
            }
        });

        //best game score gotten
        bestScore = new Label("Best score: " + rootManager.bestScore, pixeledThemeStructurePack,"subtitle");
        bestScore.setSize(Gdx.graphics.getWidth(),row_height*2);
        bestScore.setPosition(0,row_height*0);
        bestScore.setAlignment(Align.center);
        bestScore.setTouchable(Touchable.disabled);

        //Add actors
        menuStage.addActor(backgroundImage);
        menuStage.addActor(title);
        menuStage.addActor(playButton);
        menuStage.addActor(settingsButton);
        menuStage.addActor(bestScore);

    }

    @Override
    public void render(float delta) {
        //Set a default background color
        Gdx.gl.glClearColor(0, .25f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Render the current actors
        menuStage.act(delta);

        if (rootManager.currentScore >= rootManager.bestScore){
            rootManager.bestScore = rootManager.currentScore;
            bestScore.setText("Best score: " + rootManager.bestScore);
        }

        menuStage.draw();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        backgroundMusic.stop();
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        //Remove everything when disposing this screen
        title.remove();
        playButton.remove();
        settingsButton.remove();
        backgroundImage.remove();
        menuStage.dispose();
        backgroundMusic.dispose();

    }

}
