package no.zandulum.wizzy.core.gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import no.zandulum.wizzy.core.assets.Assets;
import no.zandulum.wizzy.core.graphics.Draw;
import no.zandulum.wizzy.core.spells.Fireball;
import no.zandulum.wizzy.core.spells.Firebreath;
import no.zandulum.wizzy.core.spells.AbstractSpell;
import no.zandulum.wizzy.core.tweens.TweenGlobal;
import no.zandulum.wizzy.core.tweens.TweenableFloat;
import no.zandulum.wizzy.core.tweens.accessors.FloatAccessor;

public class Hand extends AbstractGameObject {

	public static final int WIDTH = 8, HEIGHT = 8, LEFT = -1, RIGHT = 1, DIAMETER = Player.WIDTH * 2 / 3;
	public static final float DELAY = 0.5f;

	Player player;
	int orientation;
	TweenableFloat frontOffset;
	TweenableFloat sideOffset;
	boolean isHandCasting = false;

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
			spell = new Fireball(player, this);
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

	public void cast(boolean cast) {
		switch (spell.getCastType()) {
		case CHARGE:
			break;
		case HOLD:
			if (cast) {
				TweenGlobal.start(holdCastStart());
				isHandCasting = true;
			} else if (!cast) {
				TweenGlobal.start(holdCastStop());
				isHandCasting = false;
				spell.cast(false);
			}
			break;
		case SINGLE:
			if (cast && !isHandCasting) {
				isHandCasting = true;
				TweenGlobal.start(singleCast());
			}
			break;
		default:
			break;
		}
	}

	// Tweens

	private Timeline holdCastStart() {
		Timeline holdCastStart = Timeline.createParallel()
				.push(Tween.to(sideOffset, FloatAccessor.TYPE_VALUE, 0.1f).target(-20f).ease(TweenEquations.easeInExpo))
				.push(Tween.to(frontOffset, FloatAccessor.TYPE_VALUE, 0.1f).target(16f)
						.setCallback(new TweenCallback() {
							@Override
							public void onEvent(int arg0, BaseTween<?> arg1) {
								if (arg0 == TweenCallback.COMPLETE) {
									if (isHandCasting) {
										spell.cast(true);
									}
								}
							}
						}));
		return holdCastStart;
	}

	private Timeline holdCastStop() {
		Timeline holdCastStop = Timeline.createParallel()
				.push(Tween.to(sideOffset, FloatAccessor.TYPE_VALUE, 0.2f).target(0).ease(TweenEquations.easeOutExpo))
				.push(Tween.to(frontOffset, FloatAccessor.TYPE_VALUE, 0.2f).target(0).setCallback(new TweenCallback() {
					@Override
					public void onEvent(int arg0, BaseTween<?> arg1) {
						if (arg0 == TweenCallback.COMPLETE) {
							isHandCasting = false;
						}
					}
				}));

		return holdCastStop;
	}

	private Timeline singleCast() {
		Timeline singleCast = Timeline.createParallel()
				.push(Tween.to(sideOffset, FloatAccessor.TYPE_VALUE, 0.1f).target(-20f).ease(TweenEquations.easeInExpo))
				.push(Tween.to(frontOffset, FloatAccessor.TYPE_VALUE, 0.2f).target(16f)
						.setCallback(new TweenCallback() {
							@Override
							public void onEvent(int arg0, BaseTween<?> arg1) {
								spell.cast(true);
								if (arg0 == TweenCallback.COMPLETE) {
									TweenGlobal.start(Timeline.createParallel()
											.push(Tween.to(sideOffset, FloatAccessor.TYPE_VALUE, 0.2f).target(0f)
													.ease(TweenEquations.easeOutBack))
											.push(Tween.to(frontOffset, FloatAccessor.TYPE_VALUE, 0.2f).target(0f)
													.setCallback(new TweenCallback() {
														@Override
														public void onEvent(int arg0, BaseTween<?> arg1) {
															if (arg0 == TweenCallback.COMPLETE) {
																spell.cast(false);
																isHandCasting = false;
															}
														}
													})));
								}
							}
						}));
		return singleCast;
	}

}
