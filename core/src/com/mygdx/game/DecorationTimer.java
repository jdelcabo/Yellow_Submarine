package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.screens.GameScreen;

import java.util.Date;
import java.util.Random;
import java.util.TimerTask;

public class DecorationTimer extends TimerTask {

    public Stage stage;

    public DecorationTimer(Stage stage, GameScreen gs) {
        this.stage = stage;
        this.gs = gs;
    }

    public GameScreen gs;
    static int i = 1;

    @Override
    public void run() {
        try {
            System.out.println("-----------------------------------------");
            System.out.println(i + "回目タスク開始" + new Date());

            int min = 500;
            int max = 3000;
            Random random = new Random();
            int randomNumber = random.nextInt((max - min) + 1) + min;
            //3秒間停止する
            Thread.sleep(randomNumber);

            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    gs.generateDeco();
                }
            });
            i++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
