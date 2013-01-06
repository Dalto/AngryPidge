package org.bitducks.angrypidge.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.bitducks.angrypidge.network.event.clientevent.ClientEvent;
import org.bitducks.angrypidge.network.event.serverevent.ServerEvent;
import org.bitducks.angrypidge.server.ServerManagerStub;

public class NetworkHandlerServer extends NetworkHandler {

	protected ServerManagerStub manager;
	protected ServerSocket serverSocket;
	protected List<Socket> sockets = new ArrayList<Socket>();
	private boolean isServerRunning = false;

	// Variable pour la gestion du maximum de connexion
	private static int MAXIMUM_SOCKET_CONNECTION = 4* 1000;
	private int socketCount = 1;
	private boolean isServerFull = false;

	public NetworkHandlerServer(ServerManagerStub manager) {
		this.manager = manager;
	}

	public void startServer(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		isServerRunning = true;

		listenIncomingConnection();
	}

	public void closeServer() throws IOException {
		isServerRunning = false;

		for (Socket socket : sockets) {
			socket.close();
		}

		serverSocket.close();
	}

	protected void listenIncomingConnection() {

		Thread thread = new Thread(new Runnable() {
			public void run() {

				System.out.println("The server is waiting for connection.");

				// On casse la loop d'incoming connection quand on a atteint le
				// max de player soit 4
				while (isServerRunning && socketCount < MAXIMUM_SOCKET_CONNECTION) {

					Socket socket = null;
					try {
						socket = serverSocket.accept();
						sockets.add(socket);
						startSocketLister(socket);

						System.out.println("Connection incoming from " + socket.getInetAddress().getHostAddress());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				System.out.println("The server is full, incoming connection will be refuse.");
				isServerFull = true;
			}
		});

		thread.start();
	}

	private void startSocketLister(final Socket socket) {

		Thread thread = new Thread(new Runnable() {
			public void run() {
				try {
					++socketCount;
					while (socket.isConnected()) {
						ObjectInputStream objectFromInput = new ObjectInputStream(socket.getInputStream());
						Object object = objectFromInput.readObject();

						if (object instanceof ServerEvent) {
							runEvent((ServerEvent) object);
						} else {
							System.out.println("Wrong event type, ServerEvent was expected!");
						}
					}
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				} finally {

					--socketCount;
					// Puisqu'un client ses disconnect si le server etait full
					// on corrige la situation
					if (isServerFull) {
						isServerFull = false;
						listenIncomingConnection();
					}
				}

			}
		});

		thread.start();
	}

	public void sendEvent(ClientEvent event) {
		// System.out.println("Send Event Server to " + sockets.size()
		// + " clients");
		try {
			for (Socket socket : sockets) {
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				out.writeObject(event);
				out.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void runEvent(ServerEvent event) {
		// System.out.println("run Event Server");
		event.run(manager);
	}

	public boolean isServerRunning() {
		return isServerRunning;
	}
}
