package org.bitducks.angrypidge.network.event.util;

import org.bitducks.angrypidge.client.ClientManagerStub;
import org.bitducks.angrypidge.server.ServerManagerStub;

public class EventUtil {

	public static boolean isValidServerManagerStub(ServerManagerStub manager) {

		if (manager != null) {
			return true;
		} else {
			System.out.println("Invalide ServerManager");
			return false;
		}
	}

	public static boolean isValidClientManagerStub(ClientManagerStub manager) {

		if (manager != null) {
			return true;
		} else {
			System.out.println("Invalide ClientManager");
			return false;
		}
	}
}
