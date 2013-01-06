package org.bitducks.angrypidge.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.bitducks.angrypidge.common.Bird.BirdType;
import org.bitducks.angrypidge.common.Player;
import org.bitducks.angrypidge.common.Team;
import org.bitducks.angrypidge.network.event.clientevent.BirdEvent;
import org.bitducks.angrypidge.network.event.clientevent.EndGameEvent;
import org.bitducks.angrypidge.network.event.clientevent.ForceStopEvent;
import org.bitducks.angrypidge.network.event.clientevent.JoinTeamEvent;
import org.bitducks.angrypidge.network.event.clientevent.MoveEvent;
import org.bitducks.angrypidge.network.event.clientevent.NewGameEvent;
import org.bitducks.angrypidge.network.event.clientevent.ShotResultEvent;
import org.bitducks.angrypidge.network.event.clientevent.UpdateScoreEvent;
import org.bitducks.angrypidge.network.event.clientevent.YourTurnEvent;

public class ServerManager implements ServerManagerStub {

	//teams devrait être dans Server; afin de faire comme le client et de libérer cette classe pour les callbacks
	private ArrayList<Team> teams;
	private int nextID = 0;
	private int actualPlayer = 0;
	private int actualRound = 0;
	private int scoreTeam1 = 0;
	private int scoreTeam2 = 0;

	public ServerManager() {
		teams = new ArrayList<>();
		teams.add(new Team(0)); // Team 1
		teams.add(new Team(1)); // Team 2
	}

	@Override
	public void ready(int playerId) {
		Player player = getPlayerById(playerId);
		if (player != null) {
			player.setReady(!player.isReady());

			int iTeam = -1;

			for ( int i = 0; i < teams.size(); ++i )
			{
				if ( teams.get(i).getPlayers().contains(player) )
				{
					iTeam = i;
				}
			}

			Server.getInstance().sendEvent(new JoinTeamEvent(player, iTeam));
			checkReadyStatus();
		}
	}

	@Override
	public void replay() {
		// TODO Auto-generated method stub

	}

	@Override
	public void join(String playerName, int teamId) {
		//TODO : wtf, check if player exist...
		Player p = null;

		//0- Deny joining a team that is full
		if ( teamId != -1 && teams.get(teamId).getPlayers().size() >= 2 )
			return;

		//1- if playerName is already present, dont create a new player
		if ( teamId != -1 )
		{
			for ( int i = 0; i < teams.size(); ++i )
			{
				for ( int j = 0; j < teams.get( i ).getPlayers().size(); ++j )
				{
					if ( playerName.equals(teams.get( i ).getPlayers().get( j ).getName()))
					{
						p = teams.get( i ).getPlayers().get( j );
						//we'll add it in every scenario in the end of the function
						//if (i != teamId )
						teams.get(i).getPlayers().remove(j);
					}
				}
			}
		}

		//2- else : create the player
		if ( p == null )
		{
			p = new Player();
			p.setName(playerName);
			p.setId(nextID);
			nextID++;
		}
		if ( teamId == -1 )
		{
			//Need to assign player a team for the first time

			//But first, we need to resend all events to all players (
			for ( int j = 0; j < teams.size() ; ++j )
			{
				for ( int i = 0; i < teams.get( j ).getPlayers().size(); ++i )
				{
					Server.getInstance().sendEvent(
							new JoinTeamEvent(teams.get( j ).getPlayers().get( i ), j));
				}
			}

			if ( !teams.get( 0 ).isFull() )
			{
				teamId = 0;
			}
			else if ( !teams.get( 1 ).isFull() )
			{
				teamId = 1;
			}
			else
			{
				System.out.println("WTF HOW IS THIS HAPPENING ? ; there should never be 5 no-team packets " + 
						teams.get( 0 ).getPlayers().size() + teams.get( 1 ).getPlayers().size());
			}
		}

		teams.get(teamId).addPlayer(p);

		//We broadcast the new Player and the new Team to everyone
		Server.getInstance().sendEvent(new JoinTeamEvent(p, teamId));
	}

	@Override
	public void shoot(int nbTargetShot) {
		boolean isShotSuccessful = false;
		if (nbTargetShot > 0) isShotSuccessful = true;
		//Server.getInstance().sendEvent(new ShootEvent());		//On s'en sert pas sur le client
		adjustScore(nbTargetShot);
		Server.getInstance().sendEvent(new ShotResultEvent(isShotSuccessful));
		Server.getInstance().sendEvent(new UpdateScoreEvent(scoreTeam1, scoreTeam2));
		selectNextPlayer();
		nextTurn();
	}
	
	public int getActualPlayer() {
		return actualPlayer;
	}

	public void setActualPlayer(int actualPlayer) {
		this.actualPlayer = actualPlayer;
	}

	public int getActualRound() {
		return actualRound;
	}

	public void setActualRound(int actualRound) {
		this.actualRound = actualRound;
	}

	private Player getPlayerById(int id) {
		for(Team t : teams) {
			for(Player p : t.getPlayers()) {
				if (p.getId() == id) {
					return p;
				}
			}
		}
		return null;
	}

	private void checkReadyStatus() {
		int count = 0;
		for(Team t : teams) {
			for(Player p : t.getPlayers()) {
				if (p.isReady()) {
					count ++;
				}
			}
		}

		if (count == 4) {
			beginGame();
		}
	}

	private void beginGame() {
		Server.getInstance().sendEvent(new NewGameEvent());
		System.out.println("Let the game begin!");
		nextTurn();
	}

	private void nextTurn() {
		if(actualRound < 8) {
			Player p = getPlayerById(actualPlayer);
			Server.getInstance().sendEvent(new YourTurnEvent());
			Server.getInstance().sendEvent(new BirdEvent(randomizeBirdType(),p));
		} else {
			Server.getInstance().sendEvent(new EndGameEvent()); //TODO: Envoyer les scores finaux
		}
	}

	private BirdType randomizeBirdType() {
		//int lower = 0;
		//int higher = 1;
		//return BirdType.values()[(int)(Math.random() * (higher-lower)) + lower];
		Random rand = new Random(); 
		return BirdType.values()[rand.nextInt(2)];
	}

	private void selectNextPlayer() {
		if(actualPlayer == 3) {
			actualPlayer = 0;
			actualRound ++;
			if(actualRound < 8) {
				Server.getInstance().sendEvent(new MoveEvent());
			}
		} else {
			actualPlayer ++;
		}
	}

	private void adjustScore(int nbTargetShot) {
		if (teams.get(0).getPlayers().contains(getPlayerById(actualPlayer)))
		{
			scoreTeam1 += nbTargetShot;
		}
		else
		{
			scoreTeam2 += nbTargetShot;
		}
	}
}
