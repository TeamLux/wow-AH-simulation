package wow.action;

import java.util.concurrent.ThreadLocalRandom;

import wow.envrionment.Environment;
import wow.object.Potion;
import wow.player.Player;

public final class Raid extends Action {

	private static final Raid singleton = new Raid();
	private Raid() {}
	
	public static Raid getInstance(){
		return singleton;
	}
	
	@Override
	public boolean run(Player p, Environment e) {
		if(this.isrunnable(p, e)){
			int r = ThreadLocalRandom.current().nextInt(0,Player.MAX_STUFF);
			int q = p.getBag().howMany(Potion.getInstance());
			if(r < (q+p.getStuff()))
				p.addOneStuff();
			p.consum(Potion.getInstance(), Math.min(q,Player.MAX_STUFF));
			return true;
		}
		return false;
	}

	@Override
	public boolean isrunnable(Player p, Environment e) {
		//TODO
		return !p.isBusy();
	}

	@Override
	public int potentielUtility(Player p, Environment e) {
		// TODO Auto-generated method stub
		return 0;
	}

}
