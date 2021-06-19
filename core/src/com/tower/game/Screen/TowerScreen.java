package com.tower.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tower.game.entity.Player;
import com.tower.game.utill.ResourceManager;
import com.tower.game.TowerSur;
import com.tower.game.map.TileMap;
import com.tower.game.map.Tile;


public class TowerScreen implements Screen {
    public TowerSur game;
    public SpriteBatch batch;
    public ResourceManager rm;
    public TileMap map;
    public Table table,containerButton,bossDesc,skelDesc,bonfireDesc;
    public Player player;
    public Stage stage;
    public Button potionButton,foodButton,sleepButton;
    public Label floorLevel,armor,atk,sleep,bossAmt,skelAmt,bonfireAmt;
    public final Label health,potionNum,foodNum;


    public TowerScreen(final TowerSur game){
        this.game = game;
        this.batch = game.batch;
        this.rm = game.rm;
        this.player = game.player;
        map = new TileMap(rm,game.scaleX,game.scaleY);
        potionButton = new Button(rm.skin);
        foodButton = new Button(rm.skin);
        sleepButton = new Button(rm.skin);

        floorLevel = new Label("Floor "+map.floorNum,rm.skin);
        floorLevel.setFontScale(0.7f* game.scaleX);
        floorLevel.setPosition(260*game.scaleX,430*game.scaleY);

        health = new Label("HP: "+player.hp+"/"+player.maxHp,rm.skin);
        health.setFontScale(0.4f*game.scaleX);

        player.labelStamina = new Label("Stamina: "+player.stamina+"/"+player.maxSta,rm.skin);
        player.labelStamina.setFontScale(0.35f*game.scaleX);

        armor = new Label("Armor: "+player.armor,rm.skin);
        armor.setFontScale(0.4f*game.scaleX);

        atk = new Label("Damage: "+player.atk,rm.skin);
        atk.setFontScale(0.4f*game.scaleX);

        sleep = new Label("Sleep",rm.skin);
        sleep.setFontScale(0.35f*game.scaleX);

        potionNum = new Label("x "+player.potionAmt,rm.skin);
        potionNum.setFontScale(0.3f*game.scaleX);

        foodNum = new Label("x "+player.foodAmt,rm.skin);
        foodNum.setFontScale(0.3f*game.scaleX);

        //init potionButton
        potionButton.add(new Image(rm.potionIcon)).size(64*game.scaleX,64*game.scaleX).row(); //add potion icon
        potionButton.add(potionNum).padTop(3* game.scaleY);
        potionButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                player.heal();
                health.setText("HP: "+player.hp+"/"+player.maxHp);
                potionNum.setText("x "+player.potionAmt);
            }
        });

        //init foodButton
        foodButton.add(new Image(rm.foodIcon)).size(64*game.scaleX,64*game.scaleX).row();
        foodButton.add(foodNum).padTop(3*game.scaleY);
        foodButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                player.eat();
                player.labelStamina.setText("Stamina: "+player.stamina+"/"+player.maxSta);
                foodNum.setText("x "+player.foodAmt);
            }
        });

        //init sleepButton
        Image sleepImg = new Image(rm.sleepIcon);
        sleepImg.setSize(64,64);
        sleepButton.add(sleepImg).size(32*game.scaleX,32*game.scaleX).row();
        sleepButton.add(sleep).padTop(6* game.scaleY);
        sleepButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.winScreen);
//                player.sleep();
//                health.setText("HP: "+player.hp+"/"+player.maxHp);
//                player.labelStamina.setText("Stamina: "+player.stamina+"/"+player.maxSta);
//                if(player.isDead){
//                    game.setScreen(game.deathScreen);
//                }
            }
        });

        //init Right Hud
        table = new Table();
        table.align(Align.top);
        table.setBounds(608* game.scaleX,0,192* game.scaleX,480* game.scaleY);
        table.setBackground(rm.skin.getDrawable("right")); // fix it

        //init potion and food button container
        containerButton = new Table(rm.skin);
        containerButton.add(potionButton).padRight(8*game.scaleX);
        containerButton.add(foodButton);

        //init entity desc I
        bossAmt = new Label(""+map.entityArray[0]+"/"+1,rm.skin);
        bossAmt.setFontScale(0.4f*game.scaleX);
        bossDesc = new Table(rm.skin);
        Image bossImg = new Image(rm.bossIcon);
        bossDesc.add(bossImg).size(48* game.scaleX,48*game.scaleX).padRight(10*game.scaleX);
        bossDesc.add(bossAmt);

        //init entity desc II
        skelAmt = new Label(""+map.entityArray[1]+"/"+7,rm.skin);
        skelAmt.setFontScale(0.4f*game.scaleX);
        skelDesc = new Table(rm.skin);
        Image skelImg = new Image(rm.skelIcon);
        skelDesc.add(skelImg).size(48*game.scaleX,48* game.scaleX).padRight(10*game.scaleX);
        skelDesc.add(skelAmt);

        //init entity desc III
        bonfireAmt = new Label(""+map.entityArray[2]+"/"+2,rm.skin);
        bonfireAmt.setFontScale(0.4f*game.scaleX);
        bonfireDesc = new Table(rm.skin);
        Image bonfireImg = new Image(rm.bonfireIcon);
        bonfireDesc.add(bonfireImg).size(48*game.scaleX,48*game.scaleX).padRight(10*game.scaleX);
        bonfireDesc.add(bonfireAmt);

        //init tilemap and player
        map.initEnemyPower(player);
        map.placeEnemy();
        map.createActorForTile(player,game);

        //init Right Hud II
        table.add(containerButton).padTop(16* game.scaleY).row();
        table.add(sleepButton).padTop(8* game.scaleY).row();
        table.add(health).padTop(16* game.scaleY).row();
        table.add(player.labelStamina).padTop(16*game.scaleY).row();
        table.add(atk).padTop(16*game.scaleY).row();
        table.add(armor).padTop(16*game.scaleY).row();
        table.add(bossDesc).padTop(16*game.scaleY).row();
        table.add(skelDesc).padTop(8*game.scaleY).row();
        table.add(bonfireDesc).padTop(8*game.scaleY).row();

        //init stage
        stage = new Stage(new ScreenViewport());
        for(Tile[] tileArray: map.tiles){
            for(Tile tile: tileArray){
                stage.addActor(tile);
            }
        }
        stage.addActor(table);
        stage.addActor(floorLevel);
    }

    @Override
    public void show() {
        health.setText("HP: "+player.hp+"/"+player.maxHp);
        player.labelStamina.setText("Stamina: "+player.stamina+"/"+player.maxSta);
        atk.setText("Damage: "+player.atk);
        armor.setText("Armor: "+player.armor);
        skelAmt.setText(""+map.entityArray[1]+"/"+7);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(147f/255.0f,161f/255.0f, 188.0f/255.0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        batch.begin();
        map.renderTilemap(batch);
        player.renderMapEntity(batch, game.scaleX, game.scaleY);
        stage.draw();
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        rm.dispose();
        stage.dispose();
    }
}
