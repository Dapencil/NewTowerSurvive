package com.tower.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tower.game.entity.EntityState;
import com.tower.game.entity.Player;
import com.tower.game.utill.ResourceManager;
import com.tower.game.TowerSur;
import com.tower.game.entity.Entity;

public class BattleScreen implements Screen {
    public final TowerSur game;
    public SpriteBatch batch;
    public ResourceManager rm;
    public Player player;
    public Entity opponent;
    public Stage stage;
    public float elapsedTime;

    public BattleScreen(final TowerSur game,Entity opponent){
        this.game = game;
        this.batch = game.batch;
        this.rm = game.rm;
        player = game.player;
        this.opponent = opponent;
        stage = new Stage(new ScreenViewport());
    }

    @Override
    public void show() {
        final Button swordButton = new Button(rm.skin);
        final Button shieldButton = new Button(rm.skin);
        final Button auraButton = new Button(rm.skin);
        Table background = new Table();
        background.setBounds(0,0,800* game.scaleX,480*game.scaleY);
        background.setBackground(rm.skin.getDrawable("battle-screen-bg"));



        //init swordButton
        swordButton.setSize(64f*game.scaleX,64f*game.scaleX);
        swordButton.setPosition(272* game.scaleX,60*game.scaleY);
        swordButton.add(new Image(rm.battleSword)).size(48f*game.scaleX,48f*game.scaleX);

        //init shieldButton
        shieldButton.setSize(64f* game.scaleX,64f*game.scaleX);
        shieldButton.setPosition(368*game.scaleX,60* game.scaleY);
        shieldButton.add(new Image(rm.battleShied)).size(48f*game.scaleX,48f*game.scaleX);

        //init auraButton
        auraButton.setSize(64f*game.scaleX,64f*game.scaleX);
        auraButton.setPosition(464* game.scaleX,60*game.scaleY);
        auraButton.add(new Image(rm.battleAura)).size(48f*game.scaleX,48f*game.scaleX);

        ///////////////////////////
        //init EnemyInfo
        Table opponentInfo = new Table();
        opponentInfo.setPosition(120*game.scaleX,78*game.scaleY);
        //name
        Label labelOpponentName = new Label(opponent.name,rm.skin);
        labelOpponentName.setColor(rm.skin.getColor("black"));
        labelOpponentName.setFontScale(0.3f*game.scaleX);
        //hp
        Table opponentHp = new Table();
        Image imgOpponentHp = new Image(rm.hpIcon);
        final Label labelOpponentHp = new Label(opponent.hp+"/"+opponent.maxHp, rm.skin);
        labelOpponentHp.setFontScale(0.4f*game.scaleX);
        opponentHp.add(imgOpponentHp).size(32* game.scaleX,32* game.scaleX).padRight(10*game.scaleY);
        opponentHp.add(labelOpponentHp);

        //atk
        final Table opponentAtk = new Table();
        Image imgOpponentAtk = new Image(rm.atkIcon);
        Label labelOpponentAtk = new Label(opponent.atk+"",rm.skin);
        labelOpponentAtk.setFontScale(0.4f* game.scaleX);
        opponentAtk.add(imgOpponentAtk).size(32* game.scaleX,32*game.scaleX).padRight(20*game.scaleY);
        opponentAtk.add(labelOpponentAtk);

        //armor
        Table opponentArmor = new Table();
        Image imgOpponentArmor = new Image(rm.shieldIcon);
        Label labelOpponentArmor = new Label(opponent.armor+"",rm.skin);
        labelOpponentArmor.setFontScale(0.4f*game.scaleX);
        opponentArmor.add(imgOpponentArmor).size(32* game.scaleX,32*game.scaleX).padRight(20*game.scaleY);
        opponentArmor.add(labelOpponentArmor);

        //adding content to opponentInfo
        opponentInfo.add(labelOpponentName).row();
        opponentInfo.add(opponentHp).padTop(4).row();
        opponentInfo.add(opponentAtk).padTop(5*game.scaleY).row();
        opponentInfo.add(opponentArmor).padTop(5* game.scaleY);
        ///////////////////////////////

        //////////////////////////////
        //init PlayerInfo
        Table playerInfo = new Table();
        playerInfo.setPosition(680*game.scaleX,78*game.scaleY);
        //name
        Label labelPlayerName = new Label(player.name,rm.skin);
        labelPlayerName.setColor(rm.skin.getColor("black"));
        labelPlayerName.setFontScale(0.3f*game.scaleX);
        //hp
        Table playerHp = new Table();
        Image imgPlayerHp = new Image(rm.hpIcon);
        final Label labelPlayerHp = new Label(player.hp+"/"+player.maxHp, rm.skin);
        labelPlayerHp.setFontScale(0.4f*game.scaleX);
        playerHp.add(imgPlayerHp).size(32*game.scaleX,32*game.scaleX).padRight(10*game.scaleY);
        playerHp.add(labelPlayerHp);

        //atk
        Table playerAtk = new Table();
        Image imgPlayerAtk = new Image(rm.atkIcon);
        Label labelPlayerAtk = new Label(player.atk+"",rm.skin);
        labelPlayerAtk.setFontScale(0.4f*game.scaleX);
        playerAtk.add(imgPlayerAtk).size(32*game.scaleX,32*game.scaleX).padRight(20*game.scaleY);
        playerAtk.add(labelPlayerAtk);

        //armor
        Table playerArmor = new Table();
        Image imgPlayerArmor = new Image(rm.shieldIcon);
        Label labelPlayerArmor = new Label(player.armor+"",rm.skin);
        labelPlayerArmor.setFontScale(0.4f*game.scaleX);
        playerArmor.add(imgPlayerArmor).size(32*game.scaleX,32*game.scaleX).padRight(20*game.scaleY);
        playerArmor.add(labelPlayerArmor);


        //adding content to opponentInfo
        playerInfo.add(labelPlayerName).row();
        playerInfo.add(playerHp).padTop(4).row();
        playerInfo.add(playerAtk).padTop(5*game.scaleY).row();
        playerInfo.add(playerArmor).padTop(5*game.scaleY);
        ///////////////////////////////

        //init overlay when win
        final Table overlay = new Table();
        final Button attack = new Button(rm.skin);
        final Button potionUse = new Button(rm.skin);
        Label labelAttack = new Label("attack",rm.skin);
        labelAttack.setFontScale(0.3f*game.scaleX);
        attack.add(new Image(rm.atkIcon)).size(64*game.scaleX,64*game.scaleX).row();
        attack.add(labelAttack);
        final Label potionAmt = new Label("x "+player.potionAmt, rm.skin);
        Label labelPotion = new Label("use potion", rm.skin);
        potionAmt.setFontScale(0.3f*game.scaleX);
        labelPotion.setFontScale(0.3f* game.scaleX);
        potionUse.add(new Image(rm.potionIcon)).size(64*game.scaleX,64*game.scaleX).row();
        potionUse.add(potionAmt).padTop(8*game.scaleY).row();
        potionUse.add(labelPotion);
        //adding to overlay
        overlay.add(attack).padRight(20* game.scaleX);
        overlay.add(potionUse);
        overlay.setPosition(400*game.scaleX,430*game.scaleY);
        overlay.setVisible(false);


        //add listener to button
        potionUse.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                overlay.setVisible(false);
                player.heal();
                labelPlayerHp.setText(player.hp+"/"+player.maxHp);
                potionAmt.setText("x "+player.potionAmt);
                swordButton.setTouchable(Touchable.enabled);
                shieldButton.setTouchable(Touchable.enabled);
                auraButton.setTouchable(Touchable.enabled);
            }
        });

        attack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                overlay.setVisible(false);
                Timer timer = new Timer();
                Timer.Task taskAttack = new Timer.Task(){
                    @Override
                    public void run() {
                        if(opponent.isDead){
                            opponent.state = EntityState.ENTITY_DEAD;
                        }else{
                            opponent.state = EntityState.ENTITY_NONE;
                        }
                        player.state = EntityState.ENTITY_NONE;
                    }
                } ;
                Timer.Task taskRealDead = new Timer.Task() {
                    @Override
                    public void run() {
                        opponent.state = EntityState.ENTITY_REALDEAD;
                    }
                };
                Timer.Task testtask = new Timer.Task() {
                    @Override
                    public void run() {
                        opponent.state = EntityState.ENTITY_REALDEAD;
                        if(opponent.type==0){
                            player.resetCoor();
                            game.setScreen(game.winScreen);
                        }else{
                            game.prevScreen.map.entityArray[1]--;
                            game.prevScreen.map.tiles[opponent.y][opponent.x].entity = null;
                            game.setScreen(game.prevScreen);
                        }
                    }
                };
                //
                opponent.state = EntityState.ENTITY_HITED;
                player.state = EntityState.ENTITY_ATTACK;
                opponent.getHit(player.atk);
                timer.scheduleTask(taskAttack,0.72f);
                labelOpponentHp.setText(opponent.hp+"/"+opponent.maxHp);
                if(opponent.isDead) {
                    player.getItem();
                    player.updateStat(opponent.atk);
                    game.prevScreen.potionNum.setText("x "+player.potionAmt);
                    game.prevScreen.foodNum.setText("x "+player.foodAmt);
                    opponent.state = EntityState.ENTITY_DEAD;
                    timer.scheduleTask(testtask,0.9f);
                }
                swordButton.setTouchable(Touchable.enabled);
                shieldButton.setTouchable(Touchable.enabled);
                auraButton.setTouchable(Touchable.enabled);
            }
        });

        swordButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                swordButton.setTouchable(Touchable.disabled);
                shieldButton.setTouchable(Touchable.disabled);
                auraButton.setTouchable(Touchable.disabled);
                if(MathUtils.randomBoolean(opponent.chance)){
                    if(player.potionAmt==0){
                        potionUse.setTouchable(Touchable.disabled);
                    }else{
                        potionUse.setTouchable(Touchable.enabled);
                    }
                    overlay.setVisible(true);
                }else{
                    Timer timer = new Timer();
                    Timer.Task task = new Timer.Task() {
                        @Override
                        public void run() {
                            opponent.state = EntityState.ENTITY_NONE;
                            player.state = EntityState.ENTITY_NONE;
                        }
                    };
                    Timer.Task deadAni = new Timer.Task() {
                        @Override
                        public void run() {
                            player.state = EntityState.ENTITY_REALDEAD;
                        }
                    };
                    Timer.Task screenChange = new Timer.Task() {
                        @Override
                        public void run() {
                            game.setScreen(game.deathScreen);
                        }
                    };
                    opponent.state = EntityState.ENTITY_ATTACK;
                    player.state = EntityState.ENTITY_HITED;
                    player.getHit(opponent.atk);
                    timer.scheduleTask(task,0.73f);
                    labelPlayerHp.setText(player.hp+"/"+ player.maxHp);
                    if(player.isDead){
                        player.state = EntityState.ENTITY_DEAD;
                        timer.scheduleTask(deadAni,0.8f);
                        timer.scheduleTask(screenChange,2f);
                    }
                    swordButton.setTouchable(Touchable.enabled);
                    shieldButton.setTouchable(Touchable.enabled);
                    auraButton.setTouchable(Touchable.enabled);
                }

            }
        });

        shieldButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                swordButton.setTouchable(Touchable.disabled);
                shieldButton.setTouchable(Touchable.disabled);
                auraButton.setTouchable(Touchable.disabled);
                if(MathUtils.randomBoolean(opponent.chance)){
                    if(player.potionAmt==0){
                        potionUse.setTouchable(Touchable.disabled);
                    }else{
                        potionUse.setTouchable(Touchable.enabled);
                    }
                    overlay.setVisible(true);
                }else{
                    Timer timer = new Timer();
                    Timer.Task task = new Timer.Task() {
                        @Override
                        public void run() {
                            opponent.state = EntityState.ENTITY_NONE;
                            player.state = EntityState.ENTITY_NONE;
                        }
                    };
                    Timer.Task deadAni = new Timer.Task() {
                        @Override
                        public void run() {
                            player.state = EntityState.ENTITY_REALDEAD;
                        }
                    };
                    Timer.Task screenChange = new Timer.Task() {
                        @Override
                        public void run() {
                            game.setScreen(game.deathScreen);
                        }
                    };
                    opponent.state = EntityState.ENTITY_ATTACK;
                    player.state = EntityState.ENTITY_HITED;
                    player.getHit(opponent.atk);
                    timer.scheduleTask(task,0.73f);
                    labelPlayerHp.setText(player.hp+"/"+ player.maxHp);
                    if(player.isDead){
                        player.state = EntityState.ENTITY_DEAD;
                        timer.scheduleTask(deadAni,0.8f);
                        timer.scheduleTask(screenChange,2f);
                    }
                    swordButton.setTouchable(Touchable.enabled);
                    shieldButton.setTouchable(Touchable.enabled);
                    auraButton.setTouchable(Touchable.enabled);
                }

            }
        });

        auraButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                swordButton.setTouchable(Touchable.disabled);
                shieldButton.setTouchable(Touchable.disabled);
                auraButton.setTouchable(Touchable.disabled);
                if(MathUtils.randomBoolean(opponent.chance)){
                    if(player.potionAmt==0){
                        potionUse.setTouchable(Touchable.disabled);
                    }else{
                        potionUse.setTouchable(Touchable.enabled);
                    }
                    overlay.setVisible(true);
                }else{
                    Timer timer = new Timer();
                    Timer.Task task = new Timer.Task() {
                        @Override
                        public void run() {
                            opponent.state = EntityState.ENTITY_NONE;
                            player.state = EntityState.ENTITY_NONE;
                        }
                    };
                    Timer.Task deadAni = new Timer.Task() {
                        @Override
                        public void run() {
                            player.state = EntityState.ENTITY_REALDEAD;
                        }
                    };
                    Timer.Task screenChange = new Timer.Task() {
                        @Override
                        public void run() {
                            game.setScreen(game.deathScreen);
                        }
                    };
                    opponent.state = EntityState.ENTITY_ATTACK;
                    player.state = EntityState.ENTITY_HITED;
                    player.getHit(opponent.atk);
                    timer.scheduleTask(task,0.73f);
                    labelPlayerHp.setText(player.hp+"/"+ player.maxHp);
                    if(player.isDead){
                        player.state = EntityState.ENTITY_DEAD;
                        timer.scheduleTask(deadAni,0.8f);
                        timer.scheduleTask(screenChange,2f);

                    }
                    swordButton.setTouchable(Touchable.enabled);
                    shieldButton.setTouchable(Touchable.enabled);
                    auraButton.setTouchable(Touchable.enabled);
                }

            }
        });

        stage.addActor(background);
        stage.addActor(overlay);
        stage.addActor(swordButton);
        stage.addActor(shieldButton);
        stage.addActor(auraButton);
        stage.addActor(opponentInfo);
        stage.addActor(playerInfo);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        elapsedTime += delta;
        stage.act(delta);
        batch.begin();
        stage.draw();
        player.renderBattle(batch,elapsedTime,game.scaleX,game.scaleY);
        opponent.renderBattle(batch,elapsedTime,game.scaleX, game.scaleY);
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
        stage.dispose();
        batch.dispose();

    }
}
