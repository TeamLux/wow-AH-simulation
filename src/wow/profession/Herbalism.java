package wow.profession;


import java.util.ArrayList;

import wow.action.Action;
import wow.ah.AH;
import wow.envrionment.Environment;
import wow.object.Plante;
import wow.player.Player;

public final class Herbalism extends Job {
	
	private static Herbalism singleton = new Herbalism();
	private Herbalism() {}
	
	public static Herbalism getInstance(){
		return singleton;
	}

	@Override
	public ArrayList<Action> getActions() {
		ArrayList<Action> actions = new ArrayList<Action>();
		actions.add(HerbGathering.getInstance());
		return actions;
	}
	
	private final static class HerbGathering extends Action {

		private static final HerbGathering singleton = new HerbGathering();
		private HerbGathering() {}
		
		public static HerbGathering getInstance(){
			return singleton;
		}
		
		/**
		 * Cr�e 100 plantes pour le joueur p 
		 */
		@Override
		public void run(Player p, Environment e) {
			assert(this.isrunnable(p, e));
			p.busyFor(1);
			p.getBag().add(Plante.getInstance(), 100);
		}

		@Override
		public boolean isrunnable(Player p, Environment e) {
			return !p.isBusy();
		}
		
	}
}
