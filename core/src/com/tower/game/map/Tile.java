package com.tower.game.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.tower.game.entity.Entity;

public class Tile extends Widget {
    public TextureRegion sprite;
    public boolean isDark,isNearEntity;
    public Entity entity;
    int x;
    int y;

    public Tile(TextureRegion sprite,int x,int y){
        this.sprite = sprite;
        this.x = x;
        this.y = y;
        isDark = true;
        isNearEntity = false;
    }

    public boolean isContainEntity() { return !(entity == null);}

}
