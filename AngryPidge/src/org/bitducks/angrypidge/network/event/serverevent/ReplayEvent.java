package org.bitducks.angrypidge.network.event.serverevent;

import org.bitducks.angrypidge.network.event.util.EventUtil;
import org.bitducks.angrypidge.server.ServerManagerStub;

public class ReplayEvent implements ServerEvent {

	private static final long serialVersionUID = 1L;

	@Override
	public void run(ServerManagerStub manager) {
		
		if (EventUtil.isValidServerManagerStub(manager)) {
			manager.replay();
		}
	}
}
