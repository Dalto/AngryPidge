package org.bitducks.angrypidge.server;

import java.io.IOException;

import org.bitducks.angrypidge.network.NetworkHandlerServer;
import org.bitducks.angrypidge.network.event.clientevent.ClientEvent;
import org.bitducks.angrypidge.network.event.serverevent.JoinTeamEvent;
import org.bitducks.angrypidge.network.event.serverevent.ReadyEvent;

public class Server {

	private ServerManager serverManager;
	private NetworkHandlerServer networkHandler;
	// private final int PORT = 55855;
	private final int PORT = 1337;
	private static Server instance = null;

	public static Server getInstance() {

		if (instance == null) {
			instance = new Server();
		}
		return instance;
	}

	public void start() throws IOException {
		// This is to make sure that the server is not start 2 times
		if (networkHandler == null
				|| (networkHandler != null && !networkHandler.isServerRunning())) {
			serverManager = new ServerManager();
			networkHandler = new NetworkHandlerServer(serverManager);

			networkHandler.startServer(PORT);

		}
	}

	public void stop() throws IOException {
		if (networkHandler != null && networkHandler.isServerRunning()) {
			networkHandler.closeServer();
		}
	}

	public void sendEvent(ClientEvent event) {
		if (networkHandler != null && networkHandler.isServerRunning()) {
			networkHandler.sendEvent(event);
		} else {
			System.out.println("Erreur le serveur n'est pas demarrer!");
		}
	}

	public NetworkHandlerServer getNetworkHandlerServer() {
		return networkHandler;
	}

	public ServerManager getServerManager() {
		return serverManager;
	}
}
