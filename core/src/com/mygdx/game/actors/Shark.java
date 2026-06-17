package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Shark extends Actor {

    float objectReduction = 0.4f;
    //Adjust this parameter to set the object higher or lower
    float objectPositionAdjustment = 0.1f;

    private Texture sharkTexture;
    private TextureRegion sharkRegion;
    int screenWidth, screenHeight;
    private Rectangle collisionDimensions;


    //Constrcutor
    public Shark() {
        //Set name for then having a reference to take for using this object
        this.setName("shark");

        //Device dimensions
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        sharkTexture = new Texture("images/shark.png");

        setBounds(getX(), getY(), sharkTexture.getWidth(), sharkTexture.getHeight());

        sharkRegion = new TextureRegion(sharkTexture, sharkTexture.getWidth(), sharkTexture.getHeight());

        //Set default dimensions and location
        this.setY((float)(Gdx.graphics.getHeight() * (Math.random() * 7 / 10)));
        this.setX((Gdx.graphics.getWidth()));
        this.setWidth((sharkRegion.getRegionWidth() - (sharkRegion.getRegionWidth()*objectReduction)));
        this.setHeight( sharkRegion.getRegionHeight() - (sharkRegion.getRegionHeight()*objectReduction));

        //Collision dimensions
        collisionDimensions = new Rectangle(this.getX(), this.getY(), this.getWidth()*0.8f, this.getHeight()*0.8f);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Draw the shark texture
        batch.draw(sharkRegion, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
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
        this.setX(this.getX() - 5.5f);
    }

    public Rectangle getCollisionDimensions() {
        return collisionDimensions;
    }
}
