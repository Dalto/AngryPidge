package org.bitducks.angrypidge.network.event.serverevent;

import org.bitducks.angrypidge.network.event.util.EventUtil;
import org.bitducks.angrypidge.server.ServerManagerStub;

public class ShootEvent implements ServerEvent {

	private static final long serialVersionUID = 1L;

	int nbPigeonHit;


	public ShootEvent(int playerId, int nbPigeonHit) {
		super();
		this.nbPigeonHit = nbPigeonHit;
	}

	@Override
	public void run(ServerManagerStub manager) {
		
		if (EventUtil.isValidServerManagerStub(manager)) {
			manager.shoot(nbPigeonHit);
		}
	}
}
