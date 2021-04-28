package com.tower.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tower.game.Screen.*;
import com.tower.game.entity.Player;
import com.tower.game.utill.ResourceManager;

public class TowerSur extends Game {
	public SpriteBatch batch;
	public ResourceManager rm;
	public DeathScreen deathScreen;
	public WinScreen winScreen;
	public Player player;
	public TowerScreen prevScreen;
	public float scaleX,scaleY;

	@Override
	public void create () {
		scaleX = (float)Gdx.graphics.getWidth()/800f;
		scaleY = (float)Gdx.graphics.getHeight()/480f;
		batch = new SpriteBatch();
		rm = new ResourceManager();
		player = new Player(rm);
		winScreen = new WinScreen(this);
		deathScreen = new DeathScreen(this);
		this.setScreen(new MainScreen(this));
	}
	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		rm.dispose();
	}
}