package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class Ship extends Actor {
    float objectReduction = 0.0f;
    //Adjust this parameter to set the object higher or lower
    float objectPositionAdjustment = 0.1f;
    private Rectangle collisionDimensions;

    private Texture shipTexture;
    private TextureRegion shipRegion;
    int screenWidth, screenHeight;

    public Ship() {
        this.setName("ship");

        //Device dimensions
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        shipTexture = new Texture("images/tanker_edited.png");

        setBounds(getX(), getY(), shipTexture.getWidth(), shipTexture.getHeight());

        shipRegion = new TextureRegion(shipTexture, shipTexture.getWidth(), shipTexture.getHeight());

        //Set default dimensions and location
        this.setY((float) (Gdx.graphics.getHeight() * 0.74));
        this.setX((Gdx.graphics.getWidth()));
        this.setWidth((shipRegion.getRegionWidth() - (shipRegion.getRegionWidth() * objectReduction)));
        this.setHeight(shipRegion.getRegionHeight() - (shipRegion.getRegionHeight() * objectReduction));
        this.setTouchable(Touchable.disabled);

        //Collision dimensions
        collisionDimensions = new Rectangle(this.getX(), this.getY(), this.getWidth()*0.6f, this.getHeight()*0.1f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Draw the shark texture
        batch.draw(shipRegion, getX(), getY(), getOriginX(), getOriginY(),
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

    public void moveUp() {
        this.setY(this.getY() + 1f);
    }

    public void moveDown() {
        this.setY(this.getY() - 1.5f);
    }

    public void moveLeft(){
        this.setX(this.getX() - 3.0f);
    }

    public Rectangle getCollisionDimensions() {
        return collisionDimensions;
    }
}
