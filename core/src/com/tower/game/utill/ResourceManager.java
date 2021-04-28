package com.tower.game.utill;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ResourceManager{
    public AssetManager assetManager;
    public TextureAtlas atlas;
    public TextureRegion battleBG,mainBG,rightHud;
    public TextureRegion greenTile,blueTile,redTile,blackTile;
    public TextureRegion[] darkTile,brightTile,battleIcon;
    public TextureRegion atkIcon,bossIcon,foodIcon,hpIcon,playerIcon,potionIcon,bonfireIcon;
    public TextureRegion shieldIcon,skelIcon,sleepIcon,staminaIcon;
    public TextureRegion battleShied,battleSword,battleAura;
    public TextureRegion[][] skelStay,skelAttack,skelHited,skelDeath;
    public TextureRegion[][] playerStay,playerAttack,playerHited,playerDeath;
    public TextureRegion[][] bossStay,bossAttack,bossHited,bossDeath;
    public Skin skin;

    public ResourceManager(){
        assetManager = new AssetManager();
        atlas = new TextureAtlas();
        assetManager = new AssetManager();
        darkTile = new TextureRegion[4];
        brightTile = new TextureRegion[4];
        assetManager.load("gameTexture.atlas",TextureAtlas.class);
        assetManager.finishLoading();
        atlas = assetManager.get("gameTexture.atlas",TextureAtlas.class);

        //init skin
        skin = new Skin(atlas);
        skin.load(Gdx.files.internal("ui-skin.json"));


        //init tile
        greenTile = atlas.findRegion("greenTile");
        blueTile = atlas.findRegion("blueTile");
        redTile = atlas.findRegion("redTile");
        blackTile = atlas.findRegion("darkTile");
        darkTile[0] = atlas.findRegion("dark-one");
        darkTile[1] = atlas.findRegion("dark-two");
        darkTile[2] = atlas.findRegion("dark-three");
        darkTile[3] = atlas.findRegion("dark-four");
        brightTile[0] = atlas.findRegion("bright-one");
        brightTile[1] = atlas.findRegion("bright-two");
        brightTile[2] = atlas.findRegion("bright-three");
        brightTile[3] = atlas.findRegion("bright-four");

        //init battle
        battleShied = atlas.findRegion("shield");
        battleAura = atlas.findRegion("aura");
        battleSword = atlas.findRegion("sword");

        //init icon
        battleIcon = new TextureRegion[3];
        battleIcon[0] = atlas.findRegion("sword");
        battleIcon[1] = atlas.findRegion("shield");
        battleIcon[2] = atlas.findRegion("aura");

        atkIcon = atlas.findRegion("atk-icon");
        bossIcon = atlas.findRegion("boss-icon");
        foodIcon = atlas.findRegion("food-icon");
        hpIcon = atlas.findRegion("hp-icon");
        playerIcon = atlas.findRegion("player-icon");
        potionIcon = atlas.findRegion("potion-icon");
        shieldIcon = atlas.findRegion("shield-icon");
        skelIcon = atlas.findRegion("skel-icon");
        sleepIcon = atlas.findRegion("sleep-icon");
        staminaIcon = atlas.findRegion("stamina-icon");
        bonfireIcon = atlas.findRegion("bonfire-icon");

        //init background
        battleBG = atlas.findRegion("battle-screen-bg");
        mainBG = atlas.findRegion("main-screen-bg");
        rightHud = atlas.findRegion("right");

        //init animation

        //skeleton
        skelAttack = atlas.findRegion("skel-attack").split(75,80);
        skelHited = atlas.findRegion("skel-hited").split(75,80);
        skelDeath = atlas.findRegion("skel-dead").split(75,80);
        skelStay = atlas.findRegion("skel-stay").split(75,80);

        //player
        playerAttack = atlas.findRegion("player-attack").split(75,80);
        playerHited = atlas.findRegion("player-hited").split(75,80);
        playerDeath = atlas.findRegion("player-dead").split(75,80);
        playerStay = atlas.findRegion("player-stay").split(75,80);

        //boss
        bossAttack = atlas.findRegion("boss-attack").split(75,80);
        bossHited = atlas.findRegion("boss-hited").split(75,80);
        bossDeath = atlas.findRegion("boss-dead").split(75,80);
        bossStay = atlas.findRegion("boss-stay").split(75,80);
    }

    public void dispose(){
        assetManager.dispose();
        atlas.dispose();
    }
}
