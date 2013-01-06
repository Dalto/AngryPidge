package org.bitducks.angrypidge.network.event.clientevent;

import org.bitducks.angrypidge.client.ClientManagerStub;
import org.bitducks.angrypidge.network.event.util.EventUtil;

public class ShootEvent implements ClientEvent {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void run(ClientManagerStub manager) {
		if (EventUtil.isValidClientManagerStub(manager)) {
			manager.shoot();
		}
	}
}
