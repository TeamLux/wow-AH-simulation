package wow.action;

import wow.envrionment.Environment;
import wow.object.Plante;
import wow.player.Player;

public final class HerbGathering extends Action {

	private static final HerbGathering singleton = new HerbGathering();
	private HerbGathering() {}
	
	public static HerbGathering getInstance(){
		return singleton;
	}
	
	/**
	 * Cr�e 100 plantes pour le joueur p 
	 */
	@Override
	public boolean run(Player p, Environment e) {
		if(this.isrunnable(p, e)){	
			p.busyFor(1);
			p.getBag().add(Plante.getInstance(), 100);
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