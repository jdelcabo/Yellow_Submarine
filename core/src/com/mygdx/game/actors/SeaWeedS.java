package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class SeaWeedS extends Actor {
    float objectReduction = 0.4f;
    //Adjust this parameter to set the object higher or lower
    float objectPositionAdjustment = 0.1f;

    private Texture seaWeedSTexture;
    private TextureRegion seaWeedSRegion;
    int screenWidth, screenHeight;

    public SeaWeedS() {
        this.setName("seaWeedS");

        //Device dimensions
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        seaWeedSTexture = new Texture("images/seaweed.png");

        setBounds(getX(), getY(), seaWeedSTexture.getWidth(), seaWeedSTexture.getHeight());

        seaWeedSRegion = new TextureRegion(seaWeedSTexture, seaWeedSTexture.getWidth(), seaWeedSTexture.getHeight());

        //Set default dimensions and location
        this.setY((float) (Gdx.graphics.getHeight() * 0));
        this.setX((Gdx.graphics.getWidth()));
        this.setWidth((seaWeedSRegion.getRegionWidth() - (seaWeedSRegion.getRegionWidth() * objectReduction)));
        this.setHeight(seaWeedSRegion.getRegionHeight() - (seaWeedSRegion.getRegionHeight() * objectReduction));
        this.setTouchable(Touchable.disabled);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Draw the shark texture
        batch.draw(seaWeedSRegion, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    @Override
    public void act(float delta) {
        //make submarine have some gravity
        if (this.getX() >= (0 - this.getWidth())){
            moveLeft();
        }else {
            this.remove();
        }

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
        this.setX(this.getX() - 4.5f);
    }
}

