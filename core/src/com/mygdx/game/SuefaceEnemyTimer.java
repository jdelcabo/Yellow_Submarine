package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.screens.GameScreen;

import java.util.Date;
import java.util.Random;
import java.util.TimerTask;

public class SuefaceEnemyTimer extends TimerTask {

    public Stage stage;
    private GamePage rootManager;
    int min, max;

    public SuefaceEnemyTimer(Stage stage, GameScreen gs, GamePage rootManager) {
        this.stage = stage;
        this.gs = gs;
        this.rootManager = rootManager;
        adjustEnemySpawnTime(this.rootManager.currentGameDifficulty);
    }

    public GameScreen gs;
    static int i = 1;

    @Override
    public void run() {
        try {
            System.out.println("-----------------------------------------");
            System.out.println(i + "回目タスク開始" + new Date());

            /*int min = 100;
            int max = 3000;*/
            Random random = new Random();
            int randomNumber = random.nextInt((max - min) + 1) + min;
            System.out.println("------------------------------");
            System.out.println(randomNumber);
            System.out.println("------------------------------");
            //3秒間停止する
            Thread.sleep(randomNumber);

            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    gs.generateEnemySurFace();
                }
            });
            i++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void adjustEnemySpawnTime(GAME_DIFFICULTIES currentGameMode){
        switch (currentGameMode){
            case EASY:
                min = 3000;
                max = 6000;
                break;
            case NORMAL:
                min = 100;
                max = 3000;
                break;
            case HARDCORE:
                min = 100;
                max = 1000;
                break;
        }

    }
}
