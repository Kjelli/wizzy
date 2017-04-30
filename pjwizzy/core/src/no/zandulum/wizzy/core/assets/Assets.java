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
	public static Texture plain;
	
	// Fonts
	public static BitmapFont font, fontSmall, fontLarge;
	public static BitmapFont announcerFont;

	public static void load() {
		plain = load("plain.png");
		
		FreeTypeFontGenerator fontgen = new FreeTypeFontGenerator(Gdx.files.internal("vonique64.ttf"));
		FreeTypeFontParameter font20params = new FreeTypeFontParameter();
		font20params.minFilter = Texture.TextureFilter.Nearest;
		font20params.magFilter = Texture.TextureFilter.MipMapLinearNearest;
		font20params.size = 35;

		font = fontgen.generateFont(font20params);
	}

	private static Texture load(String filename) {
		Texture newTexture = new Texture(filename);
		return newTexture;
	}

}
