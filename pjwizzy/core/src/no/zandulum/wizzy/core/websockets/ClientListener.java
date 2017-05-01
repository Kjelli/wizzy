package no.zandulum.wizzy.core.websockets;

public interface ClientListener {
	void onOpen(String name);

	void onMessage(String message);

	void onHello(String name);
	
	void onMove(String name, float x, float y, int dir);

	void onHelloBack(String[] names);

	void onGoodbye(String name);

	void onClose(int code, String reason, boolean remote);

}
