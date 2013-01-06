package org.bitducks.angrypidge.network.event;

import java.io.Serializable;

import org.bitducks.angrypidge.common.Manager;

public interface NetworkEvent<T extends Manager> extends Serializable {
	
	public void run(T manager);
	
}
