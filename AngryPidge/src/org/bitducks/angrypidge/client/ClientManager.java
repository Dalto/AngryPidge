package org.bitducks.angrypidge.client;

import org.bitducks.angrypidge.client.gui.Lobby;
import org.bitducks.angrypidge.common.Bird.BirdType;
import org.bitducks.angrypidge.common.Player;


public class ClientManager implements ClientManagerStub {

	@Override
	public void forceStop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void yourTurn() {
		Client.getInstance().getGuiManager().yourTurn();
	}

	@Override
	public void newGame() {
		if ( Client.getInstance().getGuiManager().getFrame() instanceof 
				org.bitducks.angrypidge.client.gui.Lobby )
		{
			Client.getInstance().getGuiManager().getNextGuiFrame();
		}
	}

	@Override
	//SI team == null, alors le joueur join une team au hasard (first connect)
	//TODO : consider newcomers : when receiving our name, save our ID then check it for futur uses
	public void joinTeam(Player player, int teamId) {

		//System.out.println("ClientName = " + Client.getInstance().getClientName() + " versus playerName = " + 
		//		player.getName() );

		//First : if player is the good one (The client), then change frame
		if ( player.getName().equals(Client.getInstance().getClientName()))
			//TODO rejouer = ?
		{
			if ( Client.getInstance().getPlayerId() == -1 )
			{
				Client.getInstance().setPlayerId( player.getId() );
			}

			if ( Client.getInstance().getGuiManager().getFrame() instanceof 
					org.bitducks.angrypidge.client.gui.Menu )
			{
				Client.getInstance().getGuiManager().getNextGuiFrame();
			}
		}


		//if team to join  is the team the player is already in, do nothing
		int playerCurrentTeamId = Client.getInstance().findPlayerTeamId(player);
		/*System.out.println("There is a total of " + Client.getInstance().getPlayerCount() + " players");
		if ( Client.getInstance().getPlayerCount() > 0 )
		{
			Client.getInstance().printListAllPlayers();
		}*/
		//System.out.println(player.getName() + "'s CurrentTeamId = " + playerCurrentTeamId);

		if ( teamId == -1 )
		{
			System.out.println("WTF, teamId SHOULD NEVER BE -1 HERE ! ; canceling team change");
			return;
		}

		if ( playerCurrentTeamId == -1 )
		{
			//New player !
			Client.getInstance().getTeamList().get( teamId ).addPlayer(player);
		}
		else //change team
		{
			if ( playerCurrentTeamId != teamId ) //same team... if spam quickly over laggy network; or for newcomers
			{
				//Switch
				Client.getInstance().getTeamList().get( 1 - playerCurrentTeamId ).addPlayer(player);
				Client.getInstance().getTeamList().get( playerCurrentTeamId ).getPlayers().remove( player );
			}
			else
			{
				//Re-add; this event is used for setting players Ready or not (Ready isn't part of .equals)
				//Rather than removing/readding, we find its index and replace it, as we don't reorder the list this way

				int playerIdx = Client.getInstance().getTeamList().get( playerCurrentTeamId ).getPlayers().indexOf(player);
				Client.getInstance().getTeamList().get( playerCurrentTeamId ).getPlayers().set(playerIdx, player);
			}

		}

		//if in Lobby
		if ( Client.getInstance().getGuiManager().getFrame() instanceof org.bitducks.angrypidge.client.gui.Lobby )
		{
			((Lobby)Client.getInstance().getGuiManager().getFrame()).reloadPlayerLists();
		}
	}

	@Override
	public void shoot() {
		// TODO Auto-generated method stub

	}

	@Override
	public void bird(BirdType type, Player p) {
		Client.getInstance().getGuiManager().bird(type,p);
	}

	public void updateScore(int scoreTeam1, int scoreTeam2) {
		Client.getInstance().getGuiManager().updateScore(scoreTeam1, scoreTeam2);
	}

	@Override
	public void move() {
		Client.getInstance().getGuiManager().move();
	}

	@Override
	public void endGame() {
		// TODO Auto-generated method stub

	}

	@Override
	public void shotResult(boolean isSuccessful) {
		Client.getInstance().getGuiManager().shotResult(isSuccessful);
	}

}
