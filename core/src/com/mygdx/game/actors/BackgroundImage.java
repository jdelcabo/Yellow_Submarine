package com.mygdx.game.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class BackgroundImage extends Image {

    public BackgroundImage(int screenX, int screenY) {
        super(new Texture("images/background_landscape.png"));
        this.setWidth(screenX);
        this.setHeight(screenY);
        this.setTouchable(Touchable.disabled);
    }

}
