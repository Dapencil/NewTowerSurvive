package com.tower.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tower.game.entity.Player;
import com.tower.game.utill.ResourceManager;
import com.tower.game.TowerSur;

public class WinScreen implements Screen {
    public final TowerSur game;
    public SpriteBatch batch;
    public Player player;
    public ResourceManager rm;
    public Stage stage;
    public Label win,tap;

    public WinScreen(final TowerSur game){
        this.game = game;
        this.rm = game.rm;
        batch = game.batch;
        this.player = game.player;

        stage = new Stage(new ScreenViewport());
        Label.LabelStyle black = new Label.LabelStyle(rm.skin.getFont("default-font"),rm.skin.getColor("black") );
        win = new Label("- You are survive -",black);
        win.setFontScale(0.6f*game.scaleX);
        win.setPosition(240*game.scaleX,250*game.scaleY);
        tap = new Label("tap to next floor",black);
        tap.setFontScale(0.4f*game.scaleX);
        tap.setPosition(320* game.scaleX,200*game.scaleY);
        tap.addAction(Actions.forever(Actions.sequence(Actions.fadeOut(0.5f),Actions.fadeIn(0.5f))));
        stage.addActor(win);
        stage.addActor(tap);
    }
    @Override
    public void show() {
        player.resetCoor();
        game.prevScreen = new TowerScreen(game);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(251f/255f,251f/255f , 99f/255f, 0.7f);
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
