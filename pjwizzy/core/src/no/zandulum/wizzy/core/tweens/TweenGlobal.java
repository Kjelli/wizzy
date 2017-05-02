package no.zandulum.wizzy.core.tweens;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenAccessor;
import aurelienribon.tweenengine.TweenManager;
import no.zandulum.wizzy.core.tweens.accessors.ColorAccessor;
import no.zandulum.wizzy.core.tweens.accessors.FloatAccessor;
import no.zandulum.wizzy.core.tweens.accessors.Vector2Accessor;
import no.zandulum.wizzy.core.tweens.accessors.Vector3Accessor;

public class TweenGlobal {
	private static TweenManager manager;
	private static Map<Class<?>, TweenAccessor<?>> accessors;

	public static void init() {
		// Required for tweening colors
		Tween.setCombinedAttributesLimit(4);
		manager = new TweenManager();
		accessors = new HashMap<>();
		accessors.put(Vector2.class, new Vector2Accessor());
		accessors.put(Vector3.class, new Vector3Accessor());
		accessors.put(Color.class, new ColorAccessor());
		accessors.put(TweenableFloat.class, new FloatAccessor());
		register();
	}

	private static void register() {
		for (Entry<Class<?>, TweenAccessor<?>> entry : accessors.entrySet()) {
			Tween.registerAccessor(entry.getKey(), entry.getValue());
		}
	}

	public static void update(float delta) {
		manager.update(delta);
	}

	public static void start(Tween tween) {
		tween.start(manager);
	}
	public static void start(Timeline timeline) {
		timeline.start(manager);
	}
}
