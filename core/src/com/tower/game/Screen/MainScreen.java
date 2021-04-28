package com.tower.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.tower.game.utill.ResourceManager;
import com.tower.game.TowerSur;


public class MainScreen implements Screen {
    TowerSur game;
    SpriteBatch batch;
    ResourceManager rm;
    final String version = "1.0";
    Label tower,survive,tap,show,background;
    Stage stage;
    Table table;
    public MainScreen(final TowerSur game){
        this.game = game;
        this.batch = game.batch;
        this.rm = game.rm;
        stage = new Stage();
        //init label
        tower = new Label("Tower",rm.skin);
        tower.setFontScale(1f* game.scaleX);
        tower.setPosition(253* game.scaleX,320*game.scaleY);
        survive = new Label("Survive",rm.skin);
        survive.setFontScale(1f* game.scaleX);
        survive.setPosition(360*game.scaleX,280*game.scaleY);
        show = new Label("Version: "+this.version,rm.skin);
        show.setFontScale(0.3f* game.scaleX);
        show.setPosition(360* game.scaleX,0);
        background = new Label("Background: AlbertoV",rm.skin);
        background.setFontScale(0.4f* game.scaleX);
        background.setPosition(0,0);
        tap = new Label("Tap to begin!",rm.skin);
        tap.setPosition(270*game.scaleX,100* game.scaleY);
        tap.setFontScale(0.7f*game.scaleX);
        tap.addAction(Actions.forever(Actions.sequence(Actions.fadeOut(1f),Actions.fadeIn(1f))));
        //
        table = new Table(rm.skin);
        table.setBounds(0,0,800* game.scaleX,480* game.scaleY);
        table.setBackground(rm.skin.getDrawable("main-screen-bg"));
        stage.addActor(table);
        stage.addActor(show);
        stage.addActor(background);
        stage.addActor(tap);
        stage.addActor(tower);
        stage.addActor(survive);

    }
    @Override
    public void show() {
        System.out.println(game.scaleX);
        System.out.println(game.scaleY);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        batch.begin();
        stage.draw();
        batch.end();
        if(Gdx.input.isTouched()){
            game.prevScreen = new TowerScreen(game);
            game.setScreen(game.prevScreen);
        }
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

    }
}