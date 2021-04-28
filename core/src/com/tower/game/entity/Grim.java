package com.tower.game.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tower.game.utill.ResourceManager;

public class Grim extends Entity{

    public Grim(int x, int y, ResourceManager rm){
        super("Grim Reaper");
        sprite = new Sprite(rm.bossIcon);
        state = EntityState.ENTITY_NONE;
        this.x = x;
        this.y = y;
        type = 0;
        chance = 0.45f;
        animationInit(rm);
    }

    @Override
    public void setStat(int atk){
        this.atk = atk;
        int toAss = (int)(atk*1.45f);
        hp = maxHp = toAss;
        armor = maxHp/9;
    }

    public void animationInit(ResourceManager rm){
        TextureRegion[] aniFrame =  new TextureRegion[2];
        for(int i =0;i<2;i++){
            aniFrame[i] = rm.bossStay[0][i];
        }
        stayani = new Animation<TextureRegion>(0.5f,aniFrame);

        aniFrame = new TextureRegion[5];
        for(int i =0;i<5;i++){
            aniFrame[i] = rm.bossAttack[0][i];
        }
        attackani = new Animation<TextureRegion>(1f/5f,aniFrame);

        aniFrame = new TextureRegion[7];
        for(int i =0;i<7;i++){
            aniFrame[i] = rm.bossHited[0][i];
        }
        hitedani = new Animation<TextureRegion>(1f/7f,aniFrame);

        aniFrame = new TextureRegion[15];
        for(int i =0;i<15;i++){
            aniFrame[i] = rm.bossDeath[0][i];
        }
        deadani = new Animation<TextureRegion>(1f/15f,aniFrame);
    }

    public void renderBattle(SpriteBatch batch, float deltaTime,float scaleX,float scaleY){
        switch(state){
            case ENTITY_NONE:
                batch.draw(stayani.getKeyFrame(deltaTime,true),160*scaleX,180*scaleY,225*scaleX,240*scaleY);
                break;
            case ENTITY_ATTACK:
                batch.draw(attackani.getKeyFrame(deltaTime,true),160*scaleX,180*scaleX,225*scaleX,240*scaleY);
                break;
            case ENTITY_HITED:
                batch.draw(hitedani.getKeyFrame(deltaTime,true),160*scaleX,180*scaleX,225*scaleX,240*scaleY);
                break;
            case ENTITY_DEAD:
                batch.draw(deadani.getKeyFrame(deltaTime,true),160*scaleX,180*scaleX,225*scaleX,240*scaleY);
                break;
            case ENTITY_REALDEAD:
                batch.draw(deadani.getKeyFrame(deltaTime,false),160*scaleX,180*scaleX,225*scaleX,240*scaleY);
                break;
        }

    }
}
