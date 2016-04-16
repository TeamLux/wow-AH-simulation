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
			p.consume(Potion.getInstance(), Math.min(q,Player.MAX_STUFF));
			p.busyFor(3);
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
		return 0;
	}

}
