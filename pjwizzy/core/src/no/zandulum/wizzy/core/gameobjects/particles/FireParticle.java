package no.zandulum.wizzy.core.gameobjects.particles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;

import no.zandulum.wizzy.core.assets.Assets;

public class FireParticle extends AbstractParticle {

	public static final int WIDTH = 4, HEIGHT = 4;
	public static final float TTL = 0.7f;

	public FireParticle(float x, float y) {
		super(x, y, WIDTH, HEIGHT, TTL);
		setSprite(new Sprite(Assets.fire3));
		sprite.setColor(Color.ORANGE);
	}

	@Override
	public void onSpawn() {
		super.onSpawn();
		getGameContext().bringToBack(this);
	}

	@Override
	public void updateParticle(float delta) {
		setScale(1 - getAliveTime() / TTL);
	}

}
