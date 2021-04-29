package com.tower.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tower.game.entity.Player;
import com.tower.game.utill.ResourceManager;
import com.tower.game.TowerSur;
import com.tower.game.map.TileMap;

public class DeathScreen implements Screen {
    public final TowerSur game;
    SpriteBatch batch;
    ResourceManager rm;
    Label death,tap;
    Stage stage;

    public DeathScreen(TowerSur game) {
        this.game = game;
        batch = game.batch;
        rm = game.rm;
        stage = new Stage(new ScreenViewport());
        Label.LabelStyle red = new Label.LabelStyle(rm.skin.getFont("default-font"),new Color(1,0,0,0.8f));
        death = new Label("- You are death -",red);
        death.setFontScale(0.6f*game.scaleX);
        death.setPosition(240*game.scaleX,250*game.scaleY);
        tap = new Label("tap to replay",red);
        tap.setFontScale(0.4f*game.scaleX);
        tap.setPosition(320*game.scaleX,200*game.scaleY);
        tap.addAction(Actions.forever(Actions.sequence(Actions.fadeOut(0.5f),Actions.fadeIn(0.5f))));
        stage.addActor(death);
        stage.addActor(tap);
    }

    @Override
    public void show() {
        game.player = new Player(game.rm);
        TileMap.floorNum = 0;
        game.prevScreen = new TowerScreen(game);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        batch.begin();
        stage.draw();
        batch.end();
        if(Gdx.input.isTouched()){
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
