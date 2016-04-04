package wow.profession;


import java.util.ArrayList;

import wow.action.Action;
import wow.object.Plante;
import wow.player.Player;

public final class Herbalism extends Job {
	
	private static Herbalism singleton = new Herbalism();
	private Herbalism() {}
	
	public static Herbalism getHerbalism(){
		return singleton;
	}

	@Override
	public ArrayList<Action> getActions() {
		ArrayList<Action> actions = new ArrayList<Action>();
		actions.add(HerbGathering.getHerbGathering());
		return actions;
	}
	
	private final static class HerbGathering extends Action {

		private static final HerbGathering singleton = new HerbGathering();
		private HerbGathering() {}
		
		public static HerbGathering getHerbGathering(){
			return singleton;
		}
		
		/**
		 * Crée 100 plantes pour le joueur p 
		 */
		@Override
		public void run(Player p) {
			assert(this.isrunnable(p));
			p.busyFor(1);
			p.getBag().add(Plante.getPlante(), 100);
			
		}

		@Override
		public boolean isrunnable(Player p) {
			return !p.isBusy();
		}
		
	}
}
