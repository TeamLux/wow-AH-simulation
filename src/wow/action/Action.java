package wow.action;

import wow.envrionment.Environment;
import wow.player.Player;

public abstract class Action {
	public abstract boolean run(Player p, Environment e);
	public abstract boolean isrunnable(Player p, Environment e);
	public abstract int potentielUtility(Player p, Environment e);
}
