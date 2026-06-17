package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class Boat extends Actor {
    float objectReduction = 0.2f;
    //Adjust this parameter to set the object higher or lower
    float objectPositionAdjustment = 0.1f;
    private Rectangle collisionDimensions;

    private Texture boatTexture;
    private TextureRegion boatRegion;
    int screenWidth, screenHeight;

    public Boat() {
        this.setName("boat");

        //Device dimensions
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        boatTexture = new Texture("images/boat_edited.png");

        setBounds(getX(), getY(), boatTexture.getWidth(), boatTexture.getHeight());

        boatRegion = new TextureRegion(boatTexture, boatTexture.getWidth(), boatTexture.getHeight());

        //Set default dimensions and location
        this.setY((float) (Gdx.graphics.getHeight() * 0.70));
        this.setX((Gdx.graphics.getWidth()));
        this.setWidth((boatRegion.getRegionWidth() - (boatRegion.getRegionWidth() * objectReduction)));
        this.setHeight(boatRegion.getRegionHeight() - (boatRegion.getRegionHeight() * objectReduction));
        this.setTouchable(Touchable.disabled);

        //Collision dimensions
        collisionDimensions = new Rectangle(this.getX(), this.getY(), this.getWidth()*0.6f, this.getHeight()*0.1f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Draw the shark texture
        batch.draw(boatRegion, getX(), getY(), getOriginX(), getOriginY(),
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

        //Update collisions position
        collisionDimensions.setX(this.getX());
        collisionDimensions.setY(this.getY());

        // call super method to update the actor
        super.act(delta);
    }

    public void moveLeft(){
        this.setX(this.getX() - 3.0f);
    }

    public Rectangle getCollisionDimensions() {
        return collisionDimensions;
    }

}
