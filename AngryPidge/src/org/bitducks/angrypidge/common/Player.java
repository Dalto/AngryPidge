package org.bitducks.angrypidge.common;

import java.io.Serializable;

public class Player implements Serializable {
	
	private static final long serialVersionUID = 492645509840395687L;
	
	private String name;

	private int id;
	private boolean ready; 
	
	public Player()
	{
		
	}

	public Player(int id, String name)
	{
		this.id = id;
		this.name = name;
	}
	
	//Getters and setters
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String toString()
	{
		return this.name + " has id " + this.id + (this.ready ? " and is ready" : " and is not ready");
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof Player && this.id == ((Player)obj).id && this.name.equals(((Player)obj).name);
	}

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}
}
