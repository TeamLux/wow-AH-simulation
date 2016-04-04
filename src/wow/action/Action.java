package wow.action;

import wow.envrionment.Environment;
import wow.player.Player;

public abstract class Action {
	public abstract void run(Player p, Environment e);
	public abstract boolean isrunnable(Player p, Environment e);
}
