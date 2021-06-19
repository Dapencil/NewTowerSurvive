package com.tower.game.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.tower.game.utill.ResourceManager;

public class Player extends Entity {
    public int stamina,maxSta,potionAmt,foodAmt;
    public Label labelStamina;

    public Player(ResourceManager rm){
        super("Alan");
        sprite = new Sprite(rm.playerIcon);
        x = 4;
        y = 0;
        stamina = 15;
        maxSta = 15;
        atk = 5;
        maxHp = hp = atk+15;
        armor = maxHp/7;
        potionAmt = 2;
        foodAmt = 100;
        state = EntityState.ENTITY_NONE;
        animationInit(rm);
    }

    public void updateStat(int opponentAtk){
        int prevMaxHp = maxHp;
        this.atk += Math.ceil(opponentAtk/10)==0? 1:Math.ceil(opponentAtk/10);
        maxHp = atk+15;
        hp += maxHp-prevMaxHp;
        armor = (int)Math.ceil(maxHp/4);
    }

    public void heal(){
        if(potionAmt-1<0) return;
        potionAmt--;
        hp += maxHp/2;
        if(hp>maxHp) hp = maxHp;
    }

    public  void eat(){
        if(foodAmt-1<0) return;
        foodAmt--;
        stamina+=5;
        if(stamina>maxSta) stamina = maxSta;
    }

    public void sleep(){
        hp -= armor;
        stamina +=3;
        if(stamina>maxSta) stamina = maxSta;
        if(hp<=0){
            hp = 0;
            isDead = true;
        }
    }

    public void walk(){
        stamina--;
        if(stamina<=0){
            stamina = 0;
            isDead = true;
        }
    }

    public  void getItem(){
        potionAmt++;
        if(MathUtils.randomBoolean(0.75f)) foodAmt++;
    }

    public void resetCoor(){
        x = 4;
        y = 0;
    }

    public void animationInit(ResourceManager rm){
        TextureRegion[] aniFrame =  new TextureRegion[2];
        for(int i =0;i<2;i++){
            aniFrame[i] = rm.playerStay[0][i];
        }
        stayani = new Animation<TextureRegion>(0.5f,aniFrame);

        aniFrame = new TextureRegion[5];
        for(int i =0;i<5;i++){
            aniFrame[i] = rm.playerAttack[0][i];
        }
        attackani = new Animation<TextureRegion>(1f/5f,aniFrame);

        aniFrame = new TextureRegion[4];
        for(int i =0;i<4;i++){
            aniFrame[i] = rm.playerHited[0][i];
        }
        hitedani = new Animation<TextureRegion>(1f/4f,aniFrame);

        aniFrame = new TextureRegion[7];
        for(int i =0;i<7;i++){
            aniFrame[i] = rm.playerDeath[0][i];
        }
        deadani = new Animation<TextureRegion>(1f/7f,aniFrame);
    }

    @Override
    public void renderBattle(SpriteBatch batch, float deltaTime,float scaleX,float scaleY) {
        switch (state){
            case ENTITY_NONE:
                batch.draw(stayani.getKeyFrame(deltaTime,true),520*scaleX,180*scaleY,150*scaleX,160*scaleY);
                break;
            case ENTITY_ATTACK:
                batch.draw(attackani.getKeyFrame(deltaTime,true),520*scaleX,180*scaleY,150*scaleX,160*scaleY);
                break;
            case ENTITY_HITED:
                batch.draw(hitedani.getKeyFrame(deltaTime,true),520*scaleX,180*scaleY,150*scaleX,160*scaleY);
                break;
            case ENTITY_DEAD:
                batch.draw(deadani.getKeyFrame(deltaTime,true),520*scaleX,180*scaleY,150*scaleX,160*scaleY);
                break;
            case ENTITY_REALDEAD:
                batch.draw(deadani.getKeyFrame(deltaTime,false),520*scaleX,180*scaleY,150*scaleX,160*scaleY);
                break;
        }
    }
}

