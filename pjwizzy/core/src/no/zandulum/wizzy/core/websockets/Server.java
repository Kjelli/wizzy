package no.zandulum.wizzy.core.websockets;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;

import no.zandulum.wizzy.core.defs.Defs;

public class Server extends org.java_websocket.server.WebSocketServer {

	private static Server instance;

	public List<ServerListener> listeners;
	public HashMap<String, String> connectedNames;

	public Server(InetSocketAddress address) throws UnknownHostException {
		super(address);
		listeners = new ArrayList<>();
		connectedNames = new HashMap<>();
	}

	public static Server getInstance() throws UnknownHostException {
		if (instance == null) {
			InetSocketAddress address = new InetSocketAddress("192.168.38.105", 1337);
			instance = new Server(address);
		}
		return instance;
	}

	public static Server newInstance(InetSocketAddress address) throws UnknownHostException {
		if (instance == null) {
			instance = new Server(address);
		}
		return instance;
	}

	public void addListener(ServerListener listener) {
		listeners.add(listener);
	}

	public static boolean isInitialized() {
		return instance != null;
	}

	@Override
	public void start() {
		super.start();
		log("Hosting at " + getAddress() + "...");
	}

	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		log("Opened connection: " + conn.getRemoteSocketAddress());

		for (ServerListener sl : listeners) {
			sl.onOpen(conn);
		}
	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		log("Closed connection: " + conn + "(" + reason + ")" + " REMOTE:" + remote);

		for (ServerListener sl : listeners) {
			sl.onClose(conn, code, reason, remote);
		}

		sendToAllExcept(conn, PacketBuilder.goodbye(connectedNames.get(conn.getRemoteSocketAddress().toString())));
		connectedNames.remove(conn.getRemoteSocketAddress().toString());
	}

	@Override
	public void onMessage(WebSocket conn, String message) {
		handleClientMessage(conn, message);

		for (ServerListener sl : listeners) {
			sl.onMessage(conn, message);
		}
	}

	private void handleClientMessage(WebSocket conn, String message) {
		switch (message.charAt(0)) {
		case Defs.MOVE_PACKET:
			sendToAllExcept(conn, message);
			break;
		case Defs.HELLO_PACKET:
			log("Received hello from " + conn.getRemoteSocketAddress());
			System.out.println(message);
			String name = message.split(Defs.DELIMITER)[1];

			connectedNames.put(conn.getRemoteSocketAddress().toString(), name);

			sendToAllExcept(conn, message);

			// Reply with helloBack
			Collection<String> allNames = new ArrayList<String>(connectedNames.values());
			allNames.remove(name);

			String[] names = new String[allNames.size()];
			Iterator<String> it = allNames.iterator();
			for (int i = 0; i < names.length; i++) {
				names[i] = it.next();
			}

			log("Connected players (" + connectedNames.size() + "): " + String.join(", ", connectedNames.values()));
			log("Sending helloback on behalf of: " + String.join(", ", names));

			conn.send(PacketBuilder.helloBack(names));
			break;
		}
	}

	private void log(String string) {
		String timeStamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
		System.out.printf("[%s] SERVER: %s \n", timeStamp, string);
	}

	@Override
	public void onError(WebSocket conn, Exception ex) {
		log("Connection " + conn + " raised exception: ");
		ex.printStackTrace();

	}

	public void sendToAllExcept(WebSocket exception, String message) {
		Collection<WebSocket> allConnections = connections();
		synchronized (allConnections) {
			for (WebSocket connection : allConnections) {
				if (connection.equals(exception)) {
					continue;
				}
				connection.send(message);
			}
		}
	}
}
