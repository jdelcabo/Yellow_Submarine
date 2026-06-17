package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.GamePage;
import java.io.Serializable;

public class Submarine extends Actor implements Serializable {
    //Edit this parameter to reduce or increment the object size
    float objectReduction = 0.4f;
    //Adjust this parameter to set the object higher or lower
    float objectPositionAdjustment = 0.1f;
    private Texture submarineTexture;
    private TextureRegion submarineRegion;
    private float objectGravity = 1.5f;
    private GamePage rootManager;
    private Rectangle collisionDimensions;
    private boolean isDead;


    //Constrcutor
    public Submarine(GamePage rootManager) {
        this.rootManager = rootManager;
        isDead = false;
        //Set name for then having a reference to take for using this object
        this.setName("submarine");

        submarineTexture = new Texture("images/yellow_submarine.png");

        setBounds(getX()+20, getY(), submarineTexture.getWidth(), submarineTexture.getHeight());

        submarineRegion = new TextureRegion(submarineTexture, submarineTexture.getWidth(), submarineTexture.getHeight());

        //Set default dimensions and location
        this.setY(((Gdx.graphics.getHeight() - submarineRegion.getRegionHeight())/2) - (Gdx.graphics.getHeight()*objectPositionAdjustment));
        this.setWidth((submarineRegion.getRegionWidth() - (submarineRegion.getRegionWidth()*objectReduction)));
        this.setHeight( submarineRegion.getRegionHeight() - (submarineRegion.getRegionHeight()*objectReduction));

        //Collision dimensions
        collisionDimensions = new Rectangle(this.getX(), this.getY(), this.getWidth()*0.8f, this.getHeight()*0.8f);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Draw the submarine texture
        batch.draw(submarineRegion, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    @Override
    public void act(float delta) {
        //make submarine have some gravity
        if (this.getY() >= 0 && !isDead){
            submarineGravity();
        }

        if (!this.isDead) {
            rootManager.currentScore++;
        }
        //Update collisions position
        collisionDimensions.setX(this.getX());
        collisionDimensions.setY(this.getY());

        // call super method to update the actor
        super.act(delta);
    }

    public void moveUp() {
        if (this.getY() <= rootManager.screenSizeY*0.7) {
            this.setY(this.getY() + (3f + objectGravity));
        }


    }

    public void moveDown() {
        if (this.getY() > 0) {
            this.setY(this.getY() - (3f - objectGravity));
        }

    }

    private void submarineGravity(){
        this.setY(this.getY() - 1.5f);
    }

    public Rectangle getCollisionDimensions() {
        return collisionDimensions;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public boolean isDead() {
        return isDead;
    }

}