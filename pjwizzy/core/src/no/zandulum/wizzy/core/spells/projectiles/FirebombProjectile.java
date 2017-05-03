package no.zandulum.wizzy.core.spells.projectiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;

import aurelienribon.tweenengine.Tween;
import no.zandulum.wizzy.core.assets.Assets;
import no.zandulum.wizzy.core.gameobjects.particles.FireParticle;
import no.zandulum.wizzy.core.gameobjects.players.CircularHitbox;
import no.zandulum.wizzy.core.gameobjects.players.Hand;
import no.zandulum.wizzy.core.spells.Fireball;
import no.zandulum.wizzy.core.spells.Firebomb;
import no.zandulum.wizzy.core.tweens.TweenGlobal;
import no.zandulum.wizzy.core.tweens.accessors.ColorAccessor;

public class FirebombProjectile extends AbstractProjectile {

	public static final int WIDTH = 12, HEIGHT = 12;
	public static final float SPEED = 200f, TTL = 2.0f, DELAY = 0.9f;

	private static Texture[] textures = new Texture[] { Assets.fire, Assets.fire2, Assets.fire3 };
	private int ti;
	private Color fireColor;

	private boolean isFired = false;
	private float charge;

	public FirebombProjectile(Firebomb spell, float charge, float x, float y) {
		super(spell, x, y, WIDTH, HEIGHT, TTL);

		this.charge = charge;

		setSprite(new Sprite(textures[0]));
		fireColor = new Color(Color.RED);
		sprite.setColor(fireColor);
		TweenGlobal.start(Tween.to(fireColor, ColorAccessor.TYPE_RGBA, TTL / 4)
				.target(fireColor.r, 1.0f, fireColor.b, fireColor.a).repeatYoyo(4, 0));
	}

	@Override
	public void onSpawn() {
		super.onSpawn();
		getGameContext().bringToFront(this);
	}

	@Override
	public void updateProjectile(float delta) {
		if (!isFired) {
			timeToLive += delta;
			followHand(spell.getCastingHand());
		}
		sprite.setColor(fireColor);
		if (getGameContext().getTicks() % 8 == 0) {
			getGameContext().spawn(
					new FireParticle(getCenterX() - FireParticle.WIDTH / 2, getCenterY() - FireParticle.HEIGHT / 2));
			sprite.setTexture(textures[(ti++) % textures.length]);
		}
	}

	private void followHand(Hand castingHand) {
		setScale(1.0f + charge);
		setRotation(castingHand.getRotation());
		float targetX = (float) (castingHand.getCenterX() - getWidth() / 2 + Math.cos(getRotation() + Math.PI / 2) * -5f
				+ Math.sin(getRotation() + Math.PI / 2) * (getWidth() + charge * 10f));
		float targetY = (float) (castingHand.getCenterY() - getHeight() / 2
				+ Math.sin(getRotation() + Math.PI / 2) * -5f
				- Math.cos(getRotation() + Math.PI / 2) * (getWidth() + charge * 10f));
		setX(getX() * DELAY + targetX * (1 - DELAY));
		setY(getY() * DELAY + targetY * (1 - DELAY));
	}

	public void setCharge(float charge) {
		this.charge = charge;
	}

	public void fire() {
		isFired = true;
		setRotation(spell.getCaster().getRotation());
		velocity().x = spell.getCaster().velocity().x + (float) (Math.cos(rot) * SPEED);
		velocity().y = spell.getCaster().velocity().y + (float) (Math.sin(rot) * SPEED);
	}

}
