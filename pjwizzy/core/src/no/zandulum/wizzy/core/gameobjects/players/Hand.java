package no.zandulum.wizzy.core.gameobjects.players;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import no.zandulum.wizzy.core.assets.Assets;
import no.zandulum.wizzy.core.gameobjects.AbstractGameObject;
import no.zandulum.wizzy.core.graphics.Draw;
import no.zandulum.wizzy.core.spells.Fireball;
import no.zandulum.wizzy.core.spells.Firebomb;
import no.zandulum.wizzy.core.spells.Firebreath;
import no.zandulum.wizzy.core.spells.AbstractSpell;
import no.zandulum.wizzy.core.spells.AbstractSpell.CastType;
import no.zandulum.wizzy.core.tweens.TweenGlobal;
import no.zandulum.wizzy.core.tweens.TweenableFloat;
import no.zandulum.wizzy.core.tweens.accessors.FloatAccessor;

public class Hand extends AbstractGameObject {

	public static final int WIDTH = 8, HEIGHT = 8, LEFT = -1, RIGHT = 1, DIAMETER = Player.WIDTH * 2 / 3;
	public static final float DELAY = 0.5f;

	public static enum HandState {
		IDLE, READYING, CASTING, FINISHING
	}

	Player player;
	int orientation;
	TweenableFloat frontOffset;
	TweenableFloat sideOffset;

	private boolean isHandCasting = false;
	private HandState state = HandState.IDLE;

	public AbstractSpell spell;

	public Hand(Player player, float x, float y, int orientation) {
		super(x, y, WIDTH, HEIGHT);
		Sprite sprite = new Sprite(Assets.hand);
		setSprite(sprite);
		this.player = player;
		this.orientation = orientation;
		frontOffset = new TweenableFloat(0);
		sideOffset = new TweenableFloat(0);

		// TODO choose what spell
		if (orientation == RIGHT) {
			spell = new Firebomb(player, this);
		} else if (orientation == LEFT) {
			spell = new Firebreath(player, this);
		}
	}

	@Override
	public void draw(SpriteBatch batch) {
		Draw.sprite(batch, sprite, getX(), getY(), getWidth() * getScale(), getHeight() * getScale(), scale, scale, rot,
				Color.RED, orientation == LEFT);
	}

	@Override
	public void update(float delta) {
		followPlayer(player, delta);
		spellLogic(delta);
	}

	private void spellLogic(float delta) {
		spell.update(delta);

		// Try casting
		if (state == HandState.IDLE && isHandCasting && spell.getCooldown().isReady()) {
			doCastAnimation(true);
		}

		if (state == HandState.CASTING) {
			if (spell.getCastType() == CastType.SINGLE) {
				spell.cast(true);
				doCastAnimation(false);
			}

			if (spell.getCastType() == CastType.CHARGE && isHandCasting) {
				spell.cast(true);
				doCastAnimation(true);
			}

			if (spell.getCastType() == CastType.CHARGE && !isHandCasting) {
				spell.cast(false);
				doCastAnimation(false);
			}

			if (spell.getCastType() == CastType.HOLD && isHandCasting) {
				spell.cast(true);
			}

			if (spell.getCastType() == CastType.HOLD && !isHandCasting) {
				spell.cast(false);
				doCastAnimation(false);
			}
		}
	}

	private void followPlayer(Player player, float delta) {
		float targetX = (float) (player.getCenterX()
				+ Math.cos(getRotation() + Math.PI / 2) * (orientation * (DIAMETER + sideOffset.getValue())) - WIDTH / 2
				+ Math.sin(getRotation() + Math.PI / 2) * (WIDTH + frontOffset.getValue()));
		float targetY = (float) (player.getCenterY() - HEIGHT / 2
				+ Math.sin(getRotation() + Math.PI / 2) * (orientation * (DIAMETER + sideOffset.getValue()))
				- Math.cos(getRotation() + Math.PI / 2) * (WIDTH + frontOffset.getValue()));
		setX(getX() * DELAY + targetX * (1 - DELAY));
		setY(getY() * DELAY + targetY * (1 - DELAY));
		setRotation(player.getRotation());
	}

	public void doCastAnimation(boolean cast) {
		if (cast) {
			if (state == HandState.IDLE && spell.getCooldown().isReady()) {
				TweenGlobal.start(holdCastStart());
			}
		} else if (!cast) {
			if (state == HandState.CASTING) {
				TweenGlobal.start(holdCastStop());
			}
		}
	}

	// Tweens

	private Timeline holdCastStart() {
		state = HandState.READYING;
		Timeline holdCastStart = Timeline.createParallel()
				.push(Tween.to(sideOffset, FloatAccessor.TYPE_VALUE, 0.1f).target(-20f).ease(TweenEquations.easeInExpo))
				.push(Tween.to(frontOffset, FloatAccessor.TYPE_VALUE, 0.1f).target(16f)
						.setCallback(new TweenCallback() {
							@Override
							public void onEvent(int arg0, BaseTween<?> arg1) {
								if (arg0 == TweenCallback.COMPLETE) {
									state = HandState.CASTING;
								}
							}
						}));
		return holdCastStart;
	}

	private Timeline holdCastStop() {
		state = HandState.FINISHING;
		Timeline holdCastStop = Timeline.createParallel()
				.push(Tween.to(sideOffset, FloatAccessor.TYPE_VALUE, 0.2f).target(0).ease(TweenEquations.easeOutExpo))
				.push(Tween.to(frontOffset, FloatAccessor.TYPE_VALUE, 0.2f).target(0).setCallback(new TweenCallback() {
					@Override
					public void onEvent(int arg0, BaseTween<?> arg1) {
						if (arg0 == TweenCallback.COMPLETE) {
							state = HandState.IDLE;
						}
					}
				}));

		return holdCastStop;
	}

	public void requestCast(boolean cast) {
		isHandCasting = cast;
	}

}
