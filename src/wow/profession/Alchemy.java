package wow.profession;

import java.util.ArrayList;

import wow.action.Action;
import wow.ah.AH;
import wow.envrionment.Environment;
import wow.object.Plante;
import wow.object.Potion;
import wow.player.Player;

public final class Alchemy extends Job {

	
	private static Alchemy singleton = new Alchemy();
	private Alchemy() {}
	
	public static Alchemy getInstance(){
		return singleton;
	}

	@Override
	public ArrayList<Action> getActions() {
		ArrayList<Action> actions = new ArrayList<Action>();
		actions.add(CreatPotion.getInstance());
		return actions;
	}
	
	private final static class CreatPotion extends Action {

		private static final CreatPotion singleton = new CreatPotion();
		private CreatPotion() {}
		
		public static CreatPotion getInstance(){
			return singleton;
		}
		
		/**
		 * Crée une potion
		 */
		@Override
		public void run(Player p, Environment e) {
			assert(this.isrunnable(p, e));
			p.getBag().remove(Plante.getInstance(), 3);
			p.getBag().add(Potion.getInstance(), 1);
			
		}

		@Override
		public boolean isrunnable(Player p, Environment e) {
			return !p.isBusy() && p.getBag().has(Plante.getInstance(), 3);
		}
		
	}
}
