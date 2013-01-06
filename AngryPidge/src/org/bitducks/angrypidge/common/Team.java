package org.bitducks.angrypidge.common;

import java.io.Serializable;
import java.util.ArrayList;

public class Team implements Serializable {
	
	private static final long serialVersionUID = -6324966620595387777L;
	
	private int id;
	private ArrayList<Player> players = new ArrayList<>(2);

	public Team() {}
	public Team(int id) {
		this.id = id;
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void addPlayer(Player player) {
		if ( !isFull() ) {
			players.add(player);
		} else {
			throw new ArrayIndexOutOfBoundsException();
		}
	}
	
	public boolean isFull() {
		return (players.size() >= 2) ? true : false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
