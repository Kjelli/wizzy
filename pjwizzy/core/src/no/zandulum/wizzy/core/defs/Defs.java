package no.zandulum.wizzy.core.defs;

public class Defs {
	public static final float WIDTH = 800, HEIGHT = 600;

	// ################# UI #################

	
	public static final int BTN_TEXTURE_WIDTH = 128;
	public static final int BTN_TEXTURE_HEIGHT = 32;
	public static final float UNSCALED_SPACING = 5;
	public static final float BTN_SCALE = 1.7f;

	public static final float BTN_WIDTH = BTN_TEXTURE_WIDTH * BTN_SCALE;
	public static final float BTN_HEIGHT = BTN_TEXTURE_HEIGHT * BTN_SCALE;
	public static final float SPACING = UNSCALED_SPACING * BTN_SCALE;

	
	// ################# Preference keys #################

	public static final String PREFERENCE_NAME = "WIZZYPREFS";

	

	// ################# Network definitions #################
	
	public static final String DELIMITER = "#";
	public static final String SUB_DELIMITER = ";";
	public static final char MOVE_PACKET = 'm';
	public static final char CONNECT_PACKET = 'c';
	public static final char HELLO_PACKET = 'h';
	public static final char HELLO_BACK_PACKET = 'H';
	public static final char GOODBYE_PACKET = 'g';

}
