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
			p.hasDoQuest = true;
			return true;
		}
		return false;
	}

	@Override
	public boolean isrunnable(Player p, Environment e) {
		return p.canDo() && !p.hasDoQuest;
	}

	@Override
	public double potentielUtility(Player p, Environment e) {
		double newU = p.getUtility().f(p.getGold()+100, p.getStuff(),p.isTiredFor(), p.getBag());
		return (newU - p.currentUtility());
	}

}
