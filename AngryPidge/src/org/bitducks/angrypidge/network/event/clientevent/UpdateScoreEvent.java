package org.bitducks.angrypidge.network.event.clientevent;

import org.bitducks.angrypidge.client.ClientManagerStub;
import org.bitducks.angrypidge.network.event.util.EventUtil;

public class UpdateScoreEvent implements ClientEvent {

	private static final long serialVersionUID = 1L;

	int scoreTeam1;
	int scoreTeam2;

	public UpdateScoreEvent(int scoreTeam1, int scoreTeam2) {
		super();
		this.scoreTeam1 = scoreTeam1;
		this.scoreTeam2 = scoreTeam2;
	}

	@Override
	public void run(ClientManagerStub manager) {
		if (EventUtil.isValidClientManagerStub(manager)) {
			manager.updateScore(scoreTeam1, scoreTeam2);
		}
	}
}
