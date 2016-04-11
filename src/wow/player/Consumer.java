package wow.player;

import wow.object.WowObject;

public interface Consumer {
	public void consum(WowObject o, int quantity);
}
