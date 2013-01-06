package org.bitducks.angrypidge.network.event.clientevent;

import org.bitducks.angrypidge.client.ClientManagerStub;
import org.bitducks.angrypidge.network.event.util.EventUtil;

public class ShotResultEvent implements ClientEvent {

	private static final long serialVersionUID = 2973009383388218128L;
	boolean isSuccessful;
	
	public ShotResultEvent(boolean isSuccessful) {
		this.isSuccessful = isSuccessful;
	}

	@Override
	public void run(ClientManagerStub manager) {
		if (EventUtil.isValidClientManagerStub(manager)) {
			manager.shotResult(isSuccessful);
		}
	}
}
