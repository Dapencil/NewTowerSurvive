package com.tower.game.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tower.game.utill.ResourceManager;

public class Skeleton extends Entity {

    public Skeleton(int x, int y, ResourceManager rm) {
        super("Skeleton");
        sprite = new Sprite(rm.skelIcon);
        state = EntityState.ENTITY_NONE;
        this.x = x;
        this.y = y;
        type = 1;
        chance = 0.8f;
        animationInit(rm);
    }

    @Override
    public void setStat(int atk) {
        this.atk = hp = maxHp = atk;
        armor = 0;
    }

    public void animationInit(ResourceManager rm){
        TextureRegion[] aniFrame =  new TextureRegion[2];

        for(int i =0;i<2;i++){
            aniFrame[i] = rm.skelStay[0][i];
        }
        stayani = new Animation<TextureRegion>(0.5f,aniFrame);

        aniFrame = new TextureRegion[6];
        for(int i =0;i<6;i++){
            aniFrame[i] = rm.skelAttack[0][i];
        }
        attackani = new Animation<TextureRegion>(1f/6f,aniFrame);

        aniFrame = new TextureRegion[4];
        for(int i =0;i<4;i++){
            aniFrame[i] = rm.skelHited[0][i];
        }
        hitedani = new Animation<TextureRegion>(1f/6f,aniFrame);

        aniFrame = new TextureRegion[13];
        for(int i =0;i<13;i++){
            aniFrame[i] = rm.skelDeath[0][i];
        }
        deadani = new Animation<TextureRegion>(1f/13f,aniFrame);
    }

    public void renderBattle(SpriteBatch batch, float deltaTime,float scaleX,float scaleY){
        switch (state){
            case ENTITY_NONE:
                batch.draw(stayani.getKeyFrame(deltaTime,true),180*scaleX,180*scaleY,150*scaleX,160*scaleY);
                break;
            case ENTITY_ATTACK:
                batch.draw(attackani.getKeyFrame(deltaTime,true),180*scaleX,180*scaleY,150*scaleX,160*scaleY);
                break;
            case ENTITY_HITED:
                batch.draw(hitedani.getKeyFrame(deltaTime,true),180*scaleX,180*scaleY,150*scaleX,160*scaleY);
                break;
            case ENTITY_DEAD:
                batch.draw(deadani.getKeyFrame(deltaTime,true),180*scaleX,180*scaleY,150*scaleX,160*scaleY);
                break;
            case ENTITY_REALDEAD:
                batch.draw(deadani.getKeyFrame(deltaTime,false),180*scaleX,180*scaleY,150*scaleX,160*scaleY);
                break;
        }

    }
}
