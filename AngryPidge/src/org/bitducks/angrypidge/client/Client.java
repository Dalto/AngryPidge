package org.bitducks.angrypidge.client;

import java.util.ArrayList;
import java.util.Random;

import org.bitducks.angrypidge.client.gui.GuiManager;
import org.bitducks.angrypidge.common.Player;
import org.bitducks.angrypidge.common.Team;
import org.bitducks.angrypidge.network.NetworkHandlerClient;

public class Client {

	public static final int PORT = 1337;
	public static final String DEFAULT_NAME = "player";

	private static Client instance = null;
	GuiManager guiManager;
	ClientManagerStub clientManager;
	NetworkHandlerClient networkHandler;
	
	private int randomClientId = -1;
	private String playerName = DEFAULT_NAME;
	private int playerId = -1;
	private ArrayList< Team > teamArray = new ArrayList< Team >(2);

	private Client()
	{
		guiManager = new GuiManager();
		clientManager = new ClientManager();
		networkHandler = new NetworkHandlerClient(clientManager);
		guiManager.displayGuiFrame();

		teamArray.add(new Team());
		teamArray.add(new Team());
		
		giveClientName();
	}
	
	private void giveClientName()
	{
		//Used to differentiate different users that join the server
		//If really unlucky, could get same as another user (llolololol)
		randomClientId = new Random().nextInt();
		if ( randomClientId < 0 )
			randomClientId = -randomClientId;
		
		this.playerName += Integer.toString(randomClientId);
	}

	public static Client getInstance()
	{
		if ( instance == null )
		{
			instance = new Client();
		}
		return instance;
	}

	public static boolean start()
	{
		//GuiManager manager = new GuiManager();
		Client.getInstance();
		return false;
	}

	public ArrayList< Team > getTeamList()
	{
		return teamArray;
	}
	
	public int findPlayerTeamId( Player player )
	{
		for ( int i = 0; i < teamArray.size(); ++i )
		{			
			if ( teamArray.get( i ).getPlayers().contains( player ) )
			{
				return i;
			}
		}

		return -1;
	}
	
	//used for debug
	public void printListAllPlayers()
	{
		System.out.println("All players :");
		for ( int i = 0; i < teamArray.size(); ++i )
		{
			for ( int j = 0; j < teamArray.get( i ).getPlayers().size(); ++j )
			{
				System.out.println(teamArray.get( i ).getPlayers().get( j ));
			}
		}
	}
	
	//===== Getters & setters

	public GuiManager getGuiManager()
	{
		return this.guiManager;
	}

	public ClientManagerStub getClientManager()
	{
		return this.clientManager;
	}

	public NetworkHandlerClient getNetworkHandlerClient()
	{
		return this.networkHandler;
	}

	public int getRandomClientId() {
		return randomClientId;
	}

	public String getClientName() {
		return playerName;
	}

	public void setClientName(String newName) {
		
		if ( newName.equals("") || newName.equals(DEFAULT_NAME))
			return; //take default
		
		this.playerName = newName;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public int getPlayerCount()
	{
		int cptPlayers = 0;
		
		for ( int i = 0; i < teamArray.size(); ++i )
		{
			cptPlayers += teamArray.get( i ).getPlayers().size();
		}
		
		return cptPlayers;
	}
}
