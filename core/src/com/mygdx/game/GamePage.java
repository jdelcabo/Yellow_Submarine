package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.screens.MenuScreen;

//Game initializer
public class GamePage extends Game {

    public int screenSizeX, screenSizeY;
    public boolean gameSound = true;
    public long bestScore = 0, currentScore = 0;
    public GAME_DIFFICULTIES currentGameDifficulty = GAME_DIFFICULTIES.NORMAL;

    @Override
    public void create() {
        //Get device's display dimensions
        screenSizeX = Gdx.graphics.getWidth();
        screenSizeY = Gdx.graphics.getHeight();

        //Set game menu screen by default
        this.setScreen(new MenuScreen(this));
    }


}
