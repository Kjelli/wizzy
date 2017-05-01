package no.zandulum.wizzy.core.websockets;

import org.java_websocket.WebSocket;

public interface ServerListener {
	void onOpen(WebSocket conn);
	void onHello(WebSocket conn, String name);
	void onMessage(WebSocket conn, String message);
	void onClose(WebSocket conn, int code, String reason, boolean remote);
}
