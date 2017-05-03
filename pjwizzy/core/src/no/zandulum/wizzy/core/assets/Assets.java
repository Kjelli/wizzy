package no.zandulum.wizzy.core.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
	public static Texture wizzy, hand;
	public static Texture cursor;
	public static Texture fire, fire2, fire3;

	// GUI
	public static Texture menuBtns;
	public static Texture border_large;

	// Fonts
	public static BitmapFont font, font2;
	public static BitmapFont announcerFont;
	
	//Skin
	
	public static Skin skin_sdx;

	public static void load() {
		wizzy = load("wizzy.png");
		hand = load("hand.png");
		cursor = load("cursor.png");
		fire = load("fire.png");
		fire2 = load("fire2.png");
		fire3 = load("fire3.png");

		// GUI
		menuBtns = load("menu-btns.png");
		border_large = load("ui/card_long_border.png");
		
		// Skin
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("default-skin/uiskin.atlas"));
		skin_sdx = new Skin(Gdx.files.internal("default-skin/uiskin.json"));
		skin_sdx.addRegions(atlas);
		

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
		font2params.size = 150;
		font = font2gen.generateFont(font2params);
		font.getData().setScale(0.2f, 0.2f);
	}

	private static Texture load(String filename) {
		Texture newTexture = new Texture(filename);
		return newTexture;
	}

}
