package wow.player;

import wow.object.WowObject;

public interface Consumer {
	public void consume(WowObject o, int quantity);
}
