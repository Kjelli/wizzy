package no.zandulum.wizzy.core.websockets;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import no.zandulum.wizzy.core.defs.Defs;

public class Client extends WebSocketClient {

	private static Client instance;

	private List<ClientListener> listeners;

	public static String[] dummyAdjective = new String[] { "Green", "Big", "Lazy", "Cool", "Sharp", "Easy", "Heavy",
			"Little", "Hot" };
	public static String[] dummySubjective = new String[] { "Baby", "Wanker", "Coder", "Rapist", "Cripple", "Fatass",
			"Poopface", "Charizard" };

	private String name;

	private Client(URI serverURI) {
		super(serverURI);
		this.listeners = new ArrayList<>();
		this.name = dummyAdjective[(int) (Math.random() * dummyAdjective.length)]
				+ dummySubjective[(int) (Math.random() * dummySubjective.length)];
	}

	public static Client getInstance() {
		if (instance == null) {
			init();
		}
		return instance;
	}

	private static void init() {
		URI uri;
		try {
			uri = new URI("http://127.0.0.1:54545");
			instance = new Client(uri);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onOpen(ServerHandshake handshake) {
		log("Opened connection: " + handshake);

		for (ClientListener cl : listeners) {
			cl.onOpen(name);
		}
	}

	@Override
	public void onClose(int code, String reason, boolean remote) {
		log("Closed connection: (" + reason + ")" + " REMOTE:" + remote);

		for (ClientListener cl : listeners) {
			cl.onClose(code, reason, remote);
		}
	}

	@Override
	public void onMessage(String message) {
		handleServerMessage(message);

		for (ClientListener cl : listeners) {
			cl.onMessage(message);
		}
	}

	private void handleServerMessage(String message) {
		String[] data = message.split(Defs.DELIMITER);
		switch (message.charAt(0)) {
		case Defs.MOVE_PACKET:
			String moving_name = data[1];
			float new_x = Float.parseFloat(data[2]);
			float new_y = Float.parseFloat(data[3]);
			int new_dir = Integer.parseInt(data[4]);

			for (ClientListener cl : listeners) {
				cl.onMove(moving_name, new_x, new_y, new_dir);
			}
			break;
		case Defs.GOODBYE_PACKET:
			for (ClientListener cl : listeners) {
				String name = data[1];
				cl.onGoodbye(name);
			}
			break;
		case Defs.HELLO_PACKET:
			String name = data[1];
			log(name + " joined!");

			for (ClientListener cl : listeners) {
				cl.onHello(name);
			}
			break;
		case Defs.HELLO_BACK_PACKET:
			log("Hello Back: " + message);
			if (data.length == 1) {
				log("No one said hello back...");
				break;
			}
			String[] names = data[1].split(Defs.SUB_DELIMITER);
			log(String.join(", ", names) + " said hello back!");

			for (ClientListener cl : listeners) {
				cl.onHelloBack(names);
			}
			break;
		default:
			log("Which packet is this: " + message);
		}
	}

	private void log(String string) {
		String timeStamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
		System.out.printf("[%s] CLIENT: %s \n", timeStamp, string);
	}

	@Override
	public void onError(Exception ex) {
		log("Raised exception: ");
		ex.printStackTrace();

	}

	public String getName() {
		return name;
	}

	public void addListener(ClientListener clientListener) {
		listeners.add(clientListener);
	}

}
