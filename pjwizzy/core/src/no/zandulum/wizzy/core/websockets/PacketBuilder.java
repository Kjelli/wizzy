package no.zandulum.wizzy.core.websockets;

import java.util.Locale;

import no.zandulum.wizzy.core.defs.Defs;

public class PacketBuilder {
	public static final String hello(String name) {
		return String.format("%s%s%s", Defs.HELLO_PACKET, Defs.DELIMITER, name);
	}

	public static final String goodbye(String name) {
		return String.format("%s%s%s", Defs.GOODBYE_PACKET, Defs.DELIMITER, name);
	}

	public static final String helloBack(String[] names) {
		return String.format("%s%s%s", Defs.HELLO_BACK_PACKET, Defs.DELIMITER, String.join(Defs.SUB_DELIMITER, names));
	}

	public static final String move(String name, float x, float y, int dir) {
		return String.format(Locale.ROOT, "%s%s%s%s%f%s%f%s%d", Defs.MOVE_PACKET, Defs.DELIMITER, name, Defs.DELIMITER,
				x, Defs.DELIMITER, y, Defs.DELIMITER, dir);
	}
}
