package org.bitducks.angrypidge.network.event.clientevent;

import org.bitducks.angrypidge.client.ClientManagerStub;
import org.bitducks.angrypidge.common.Bird.BirdType;
import org.bitducks.angrypidge.common.Player;
import org.bitducks.angrypidge.network.event.util.EventUtil;

public class BirdEvent implements ClientEvent {

	private static final long serialVersionUID = 1L;

	BirdType type;
	Player p;

	public BirdEvent(BirdType type, Player p) {
		this.type = type;
		this.p = p;
	}

	@Override
	public void run(ClientManagerStub manager) {
		if (EventUtil.isValidClientManagerStub(manager)) {
			manager.bird(type,p);
		}
	}
}
