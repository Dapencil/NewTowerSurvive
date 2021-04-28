package com.tower.game.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Entity {
    public String name;
    public int hp,maxHp,armor,atk;
    public int x,y;
    public Sprite sprite;
    public Animation<TextureRegion> stayani,attackani,hitedani,deadani;
    public EntityState state;
    public boolean isDead;
    public int type;
    public float chance;

    public Entity(String name){
        this.name = name;
        isDead = false;
    }


    //for override in child class
    public void renderMapEntity(SpriteBatch batch,float scaleX,float scaleY){
        batch.draw(sprite,(32f + (64 * x))*scaleX,(32f + (64 * y))*scaleY,64f*scaleX,64*scaleY);
    }
    public void renderBattle(SpriteBatch batch, float deltaTime,float scaleX,float scaleY){}
    public void setStat(int atk){}

    public void getHit(int damage){
        this.hp -= (damage-armor/2);
        if(hp<=0) {
            hp = 0;
            isDead = true;
        }
    }
}