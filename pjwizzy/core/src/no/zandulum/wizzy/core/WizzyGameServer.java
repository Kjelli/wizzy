package no.zandulum.wizzy.core;

import java.net.UnknownHostException;

import no.zandulum.wizzy.core.assets.Assets;
import no.zandulum.wizzy.core.screens.ServerScreen;
import no.zandulum.wizzy.core.websockets.Server;

public class WizzyGameServer extends WizzyGame {

	@Override
	public void create() {
		Assets.load();
				
		try {
			Server s = Server.getInstance();
			s.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		setScreen(new ServerScreen(this));
	}

}
