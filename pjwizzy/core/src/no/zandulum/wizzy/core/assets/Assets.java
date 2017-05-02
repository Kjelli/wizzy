package no.zandulum.wizzy.core.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Assets {
	public static Texture wizzy, hand;
	public static Texture cursor;
	public static Texture fire, fire2, fire3;

	// GUI
	public static Texture menuBtns;

	// Fonts
	public static BitmapFont font, font2;
	public static BitmapFont announcerFont;

	public static void load() {
		wizzy = load("wizzy.png");
		hand = load("hand.png");
		cursor = load("cursor.png");
		fire = load("fire.png");
		fire2 = load("fire2.png");
		fire3 = load("fire3.png");

		// GUI
		menuBtns = load("menu-btns.png");

		// FreeTypeFontGenerator fontgen = new
		// FreeTypeFontGenerator(Gdx.files.internal("vonique64.ttf"));
		// FreeTypeFontParameter font20params = new FreeTypeFontParameter();
		// font20params.minFilter = Texture.TextureFilter.Nearest;
		// font20params.magFilter = Texture.TextureFilter.MipMapLinearNearest;
		// font20params.size = 48;
		//
		// font = fontgen.generateFont(font20params);
		//

		FreeTypeFontGenerator font2gen = new FreeTypeFontGenerator(Gdx.files.internal("vonique64.ttf"));
		FreeTypeFontParameter font2params = new FreeTypeFontParameter();
		font2params.minFilter = Texture.TextureFilter.Linear;
		font2params.size = 50;
		font2params.flip = true;
		font = font2gen.generateFont(font2params);
		font.getData().setScale(0.2f, 0.2f);
	}

	private static Texture load(String filename) {
		Texture newTexture = new Texture(filename);
		return newTexture;
	}

}
