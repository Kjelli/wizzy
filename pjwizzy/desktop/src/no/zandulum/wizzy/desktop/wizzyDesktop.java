package no.zandulum.wizzy.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import no.zandulum.wizzy.core.WizzyGame;

public class wizzyDesktop {
	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new WizzyGame(), config);
	}
}
