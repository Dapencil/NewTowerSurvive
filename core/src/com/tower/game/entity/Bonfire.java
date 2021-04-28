package com.tower.game.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.tower.game.utill.ResourceManager;

public class Bonfire extends Entity{

    public Bonfire(int x,int y, ResourceManager rm){
        super("Bonfire");
        sprite = new Sprite(rm.bonfireIcon);
        this.x = x;
        this.y = y;
        type = 2;
    }
}
