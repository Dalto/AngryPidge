package org.bitducks.angrypidge.client;

import org.bitducks.angrypidge.common.Bird.BirdType;
import org.bitducks.angrypidge.common.Manager;
import org.bitducks.angrypidge.common.Player;

public interface ClientManagerStub extends Manager {

	public void forceStop();
	public void newGame();
	public void joinTeam(Player player, int teamId);
	public void yourTurn();
	public void shoot();
	public void bird(BirdType type, Player p);
	public void updateScore(int scoreTeam1, int scoreTeam2);
	public void move();
	public void endGame();
	public void shotResult(boolean isSuccessful);
}
