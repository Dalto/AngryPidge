package org.bitducks.angrypidge.server;

import org.bitducks.angrypidge.common.Manager;

public interface ServerManagerStub extends Manager {

	public void ready(int playerId);
	public void replay();
	public void join(String playerName, int teamId);
	public void shoot(int nbTargetShot);
	
}
