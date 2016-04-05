package wow.action;

import wow.envrionment.Environment;
import wow.player.Player;

public final class Quest extends Action {
	
	private static final Quest singleton = new Quest();
	private Quest() {}
	
	public static Quest getInstance(){
		return singleton;
	}
	
	@Override
	public boolean run(Player p, Environment e) {
		if(this.isrunnable(p, e)){
			p.busyFor(1);
			p.earn(100);
			return true;
		}
		return false;
	}

	@Override
	public boolean isrunnable(Player p, Environment e) {
		return !p.isBusy();
	}

	@Override
	public int potentielUtility(Player p, Environment e) {
		// TODO Auto-generated method stub
		return 0;
	}

}
