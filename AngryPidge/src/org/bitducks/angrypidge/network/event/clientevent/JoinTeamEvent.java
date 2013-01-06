package org.bitducks.angrypidge.network.event.clientevent;

import org.bitducks.angrypidge.client.ClientManagerStub;
import org.bitducks.angrypidge.common.Player;
import org.bitducks.angrypidge.network.event.util.EventUtil;

public class JoinTeamEvent implements ClientEvent {
	
	private static final long serialVersionUID = -501652094217849405L;
	private Player player;
	private int teamId;
	
	public JoinTeamEvent(Player p, int teamId) {
		this.player = p;
		this.teamId = teamId;
	}

	@Override
	public void run(ClientManagerStub manager) {

		if (EventUtil.isValidClientManagerStub(manager)) {
			manager.joinTeam(this.player, this.teamId);
		}
	}
}
