package no.zandulum.wizzy.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import no.zandulum.wizzy.core.WizzyGame;
import no.zandulum.wizzy.core.defs.Defs;

public class wizzyDesktop {
	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.useHDPI = true;
		config.foregroundFPS = 120;
		// config.fullscreen = true;
		config.width = (int) Defs.WIDTH;
		config.height = (int) Defs.HEIGHT;
		new LwjglApplication(new WizzyGame(), config);
	}
}
