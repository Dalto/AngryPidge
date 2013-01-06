package org.bitducks.angrypidge.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.bitducks.angrypidge.client.ClientManager;
import org.bitducks.angrypidge.client.ClientManagerStub;
import org.bitducks.angrypidge.network.event.clientevent.ClientEvent;
import org.bitducks.angrypidge.network.event.serverevent.JoinTeamEvent;
import org.bitducks.angrypidge.network.event.serverevent.ServerEvent;

public class NetworkHandlerClient extends NetworkHandler {

	protected ClientManagerStub manager;

	protected Socket socket;

	public NetworkHandlerClient(ClientManagerStub manager) {
		this.manager = manager;
	}

	public void connect(String host, int port) throws UnknownHostException,
			IOException {
		socket = new Socket(host, port);
		startSocketLister(socket);
	}

	private void startSocketLister(final Socket socket) {
		Thread thread = new Thread(new Runnable() {
			public void run() {
				try {
					while (socket.isConnected()) {
						ObjectInputStream objectFromInput = new ObjectInputStream(
								socket.getInputStream());
						Object object = objectFromInput.readObject();

						if (object instanceof ClientEvent) {
							runEvent((ClientEvent) object);
						} else {
							System.out
									.println("Wrong event type, ClientEvent was expected!");
						}
					}
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});

		thread.start();
	}

	public void sendEvent(ServerEvent event) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(
					socket.getOutputStream());
			out.writeObject(event);
			out.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void runEvent(ClientEvent event) {
		event.run(manager);
	}
}
