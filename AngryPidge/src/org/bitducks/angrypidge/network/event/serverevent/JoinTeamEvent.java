package org.bitducks.angrypidge.network.event.serverevent;

import org.bitducks.angrypidge.network.event.util.EventUtil;
import org.bitducks.angrypidge.server.ServerManagerStub;

public class JoinTeamEvent implements ServerEvent {

	private static final long serialVersionUID = -3502208130659167735L;

	String playerName;
	int teamId;
	//String ip;

	public JoinTeamEvent(String playerName, int teamId) {
		super();
		this.playerName = playerName;
		this.teamId = teamId;
	}

	@Override
	public void run(ServerManagerStub manager) {
		
		if (EventUtil.isValidServerManagerStub(manager)) {
			manager.join(playerName, teamId);
		}
	}
}
