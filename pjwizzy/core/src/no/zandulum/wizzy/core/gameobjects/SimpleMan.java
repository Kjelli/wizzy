package no.zandulum.wizzy.core.gameobjects;

import com.badlogic.gdx.graphics.g2d.Sprite;

import no.zandulum.wizzy.core.assets.Assets;

public class SimpleMan extends AbstractGameObject {
	public static final int WIDTH = 100;
	public static final int HEIGHT = 100;

	public SimpleMan(float x, float y) {
		super(x, y, WIDTH, HEIGHT);
		setSprite(new Sprite(Assets.plain));
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub

	}

}
