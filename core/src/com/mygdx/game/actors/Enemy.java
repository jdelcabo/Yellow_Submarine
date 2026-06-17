package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Enemy extends Actor {
    //Set size of image
    float objectReduction = 0.4f;
    float speed;
    private Texture enemyTexture;
    private TextureRegion enemyRegion;
    int screenWidth, screenHeight;
    private Rectangle collisionDimensions;

    public Enemy(String imagePath, float speed) {
        this.speed = speed;

        //Device dimensions
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        enemyTexture = new Texture(imagePath);

        setBounds(getX(), getY(), enemyTexture.getWidth(), enemyTexture.getHeight());
        enemyRegion = new TextureRegion(this.enemyTexture, enemyTexture.getWidth(), enemyTexture.getHeight());

        //Set default dimensions and location
        this.setY((float)(Gdx.graphics.getHeight() * (Math.random() * 7 / 10)));
        this.setX((Gdx.graphics.getWidth()));
        this.setWidth((enemyRegion.getRegionWidth() - (enemyRegion.getRegionWidth()*objectReduction)));
        this.setHeight( enemyRegion.getRegionHeight() - (enemyRegion.getRegionHeight()*objectReduction));

        this.collisionDimensions = new Rectangle(this.getX(), this.getY(), this.getWidth()*0.8f, this.getHeight()*0.8f);
    }

    @Override
    public void act(float delta) {
        if (this.getX() >= (0 - this.getWidth())){
            moveLeft();
        }else {
            this.remove();
        }

        //Update collisions position
        collisionDimensions.setX(this.getX());
        collisionDimensions.setY(this.getY());

        // call super method to update the actor
        super.act(delta);
    }

    public void moveUp() {
        this.setY(this.getY() + 1f);
    }

    public void moveDown() {
        this.setY(this.getY() - 1.5f);
    }

    public void moveLeft(){
        this.setX(this.getX() - speed);
    }

    public Rectangle getCollisionDimensions() {
        return collisionDimensions;
    }
}
