package org.bitducks.angrypidge.network.event.serverevent;

import org.bitducks.angrypidge.network.event.util.EventUtil;
import org.bitducks.angrypidge.server.ServerManagerStub;

public class ReadyEvent implements ServerEvent {

	private static final long serialVersionUID = 1L;

	int playerId;

	public ReadyEvent(int playerId) {
		super();
		this.playerId = playerId;
	}

	@Override
	public void run(ServerManagerStub manager) {

		if (EventUtil.isValidServerManagerStub(manager)) {
			manager.ready(playerId);
		}
	}
}
