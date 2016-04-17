package wow.action;

import wow.envrionment.Environment;
import wow.object.Bag;
import wow.object.Plante;
import wow.player.Player;

public final class HerbGathering extends Action {

	private static final HerbGathering singleton = new HerbGathering();
	private HerbGathering() {}
	public static int tired = 3;
	public static int nbPlantes = 200;
	
	public static HerbGathering getInstance(){
		return singleton;
	}
	
	/**
	 * Crée 100 plantes pour le joueur p 
	 */
	@Override
	public boolean run(Player p, Environment e) {
		if(this.isrunnable(p, e)){	
			p.busyFor(1);
			p.tiredFor(tired);
			p.getBag().add(Plante.getInstance(), nbPlantes);
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
		Bag tmpBag = new Bag(p.getBag());
		tmpBag.add(Plante.getInstance(), nbPlantes);
		double newU = p.getUtility().f(p.getGold(), p.getStuff(),p.isTiredFor()*tired,tmpBag);
		return (newU - p.currentUtility());
	}
	
}
