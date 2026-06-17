package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.GAME_DIFFICULTIES;
import com.mygdx.game.GamePage;
import com.mygdx.game.actors.BackgroundImage;

public class SettingsScreen implements Screen {

    private GamePage rootManager;
    private BackgroundImage backgroundImage;
    private Stage settingsStage;
    private Label settingDescription1, settingState1, settingDescription2, settingState2;
    private TextButton closeButton, switchMusicButton, switchGameModeButton;
    private String currentGameDifficulty;
    private GAME_DIFFICULTIES gameModes [];


    public SettingsScreen(GamePage rootManager) {
        this.rootManager = rootManager;

        gameModes = new GAME_DIFFICULTIES[]{
          GAME_DIFFICULTIES.EASY,
          GAME_DIFFICULTIES.NORMAL,
          GAME_DIFFICULTIES.HARDCORE
        };
    }


    @Override
    public void show() {
        settingsStage = new Stage();
        Gdx.input.setInputProcessor(settingsStage);

        Skin pixeledThemeStructurePack = new Skin(Gdx.files.internal("pixthulhu/skin/pixthulhu-ui.json"));

        int row_height = Gdx.graphics.getWidth() / 12;
        int col_width = Gdx.graphics.getWidth() / 12;

        //Background image
        backgroundImage = new BackgroundImage(rootManager.screenSizeX, rootManager.screenSizeY);
        backgroundImage.setTouchable(Touchable.disabled);

        //Close button
        closeButton = new TextButton("Close",pixeledThemeStructurePack,"default");
        closeButton.setSize(col_width*2,row_height);
        closeButton.setPosition(col_width*0.2f, Gdx.graphics.getHeight()-row_height*1.2f);
        closeButton.align(Align.center);
        closeButton.setTouchable(Touchable.enabled);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Change the screen to the game screen
                rootManager.setScreen(new MenuScreen(rootManager));
            }
        });

        //Setting 1 description
        settingDescription1 = new Label("Press the next button to turn on or off the game's music and sounds:",pixeledThemeStructurePack,"subtitle");
        settingDescription1.setSize(col_width*6,row_height*2);
        settingDescription1.setPosition((rootManager.screenSizeX/2 - (col_width*6/2)),row_height*5);
        settingDescription1.setAlignment(Align.center);
        settingDescription1.setTouchable(Touchable.disabled);
        settingDescription1.setWrap(true);

        //Turn on or of the music and sounds of the game's music
        switchMusicButton = new TextButton("Switch",pixeledThemeStructurePack,"default");
        switchMusicButton.setSize(col_width*2,row_height);
        switchMusicButton.setPosition(col_width*4f, row_height*4.5f);
        switchMusicButton.align(Align.center);
        switchMusicButton.setTouchable(Touchable.enabled);
        switchMusicButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Change the screen to the game screen
                if (rootManager.gameSound){
                    rootManager.gameSound = false;
                } else {
                    rootManager.gameSound = true;
                }
            }
        });

        //Setting 1 state
        settingState1 = new Label("",pixeledThemeStructurePack,"subtitle");
        settingState1.setSize(col_width*6,row_height*2);
        settingState1.setPosition(col_width*4f,row_height*4f);
        settingState1.setAlignment(Align.center);
        settingState1.setTouchable(Touchable.disabled);
        settingState1.setWrap(true);

        //Setting 2 description
        settingDescription2 = new Label("Press the next button to switch between game difficulties:",pixeledThemeStructurePack,"subtitle");
        settingDescription2.setSize(col_width*6,row_height*2);
        settingDescription2.setPosition((rootManager.screenSizeX/2 - (col_width*6/2)),Gdx.graphics.getHeight()-row_height*4-100);
        settingDescription2.setAlignment(Align.center);
        settingDescription2.setTouchable(Touchable.disabled);
        settingDescription2.setWrap(true);

        //Setting 2 state
        settingState2 = new Label(getCurrentGameDifficulty(rootManager.currentGameDifficulty),pixeledThemeStructurePack,"subtitle");
        settingState2.setSize(col_width*6,row_height*2);
        settingState2.setPosition(col_width*4f,row_height*2f-100);
        settingState2.setAlignment(Align.center);
        settingState2.setTouchable(Touchable.disabled);
        settingState2.setWrap(true);

        //Switch between game modes
        switchGameModeButton = new TextButton("Switch",pixeledThemeStructurePack,"default");
        switchGameModeButton.setSize(col_width*2,row_height);
        switchGameModeButton.setPosition(col_width*4f, row_height*2.5f-100);
        switchGameModeButton.align(Align.center);
        switchGameModeButton.setTouchable(Touchable.enabled);
        switchGameModeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                for (int i = 0; i < gameModes.length; i++){
                    if (gameModes[i] == rootManager.currentGameDifficulty){
                        if (i < gameModes.length-1){
                            rootManager.currentGameDifficulty = gameModes[++i];
                        } else {
                            rootManager.currentGameDifficulty = gameModes[0];
                        }
                        i = gameModes.length;
                    }
                }
            }
        });

        settingsStage.addActor(backgroundImage);
        settingsStage.addActor(closeButton);
        settingsStage.addActor(settingDescription1);
        settingsStage.addActor(switchMusicButton);
        settingsStage.addActor(settingState1);
        settingsStage.addActor(settingState2);
        settingsStage.addActor(settingDescription2);
        settingsStage.addActor(switchGameModeButton);

    }

    @Override
    public void render(float delta) {
        //Set a default background color
        Gdx.gl.glClearColor(0, .25f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Render the current actors
        settingsStage.act(delta);
        settingsStage.draw();

        if (rootManager.gameSound){
            settingState1.setText("On");
        } else {
            settingState1.setText("Off");
        }

        settingState2.setText(getCurrentGameDifficulty(rootManager.currentGameDifficulty));
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        switchMusicButton.remove();
        closeButton.remove();
        settingDescription1.remove();
        settingsStage.dispose();

    }

    private String getCurrentGameDifficulty(GAME_DIFFICULTIES currentVersion){
        switch (currentVersion){
            case EASY:
                return "Easy";
            case NORMAL:
                return "Normal";
            case HARDCORE:
                return "Hardcore";
            default:
                return "Normal";
        }
    }
}
