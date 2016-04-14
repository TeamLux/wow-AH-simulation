package wow.action;

import wow.envrionment.Environment;
import wow.player.Player;

public final class Quest extends Action {
	
	private static final Quest singleton = new Quest();
	private Quest() {}
	private static final int earnGold = 100*10000;
	
	public static Quest getInstance(){
		return singleton;
	}
	
	@Override
	public boolean run(Player p, Environment e) {
		if(this.isrunnable(p, e)){
			p.busyFor(1);
			p.earn(earnGold);
			return true;
		}
		return false;
	}

	@Override
	public boolean isrunnable(Player p, Environment e) {
		return p.canDo();
	}

	@Override
	public double potentielUtility(Player p, Environment e) {
		double newU = p.getUtility().f(p.getGold()+earnGold, p.getStuff(),p.isTiredFor(), p.getBag());
		return (newU - p.currentUtility());
	}

}
