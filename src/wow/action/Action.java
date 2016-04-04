package wow.action;

import wow.player.Player;

public abstract class Action {
	public abstract void run(Player p);
	public abstract boolean isrunnable(Player p);
}
