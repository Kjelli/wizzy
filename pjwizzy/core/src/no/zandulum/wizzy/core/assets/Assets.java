package no.zandulum.wizzy.core.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Array;

public class Assets {
	public static Texture wizzy, hand;
	
	// GUI
	public static Texture menuBtns;
	
	// Fonts
	public static BitmapFont font, font2;
	public static BitmapFont announcerFont;

	public static void load() {
		wizzy = load("wizzy.png");
		hand = load("hand.png");
		
		//GUI
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

		FreeTypeFontGenerator font2gen = new FreeTypeFontGenerator(Gdx.files.internal("EMLATIN6.ttf"));
		FreeTypeFontParameter font2params = new FreeTypeFontParameter();
		font2params.minFilter = Texture.TextureFilter.Nearest;
		font2params.magFilter = Texture.TextureFilter.MipMapLinearNearest;
		font2params.size = 48;

		font = font2gen.generateFont(font2params);
	}

	private static Texture load(String filename) {
		Texture newTexture = new Texture(filename);
		return newTexture;
	}

}
