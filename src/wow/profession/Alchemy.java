package wow.profession;

import java.util.ArrayList;

import wow.action.Action;
import wow.object.Plante;
import wow.object.Potion;
import wow.player.Player;

public final class Alchemy extends Job {

	
	private static Alchemy singleton = new Alchemy();
	private Alchemy() {}
	
	public static Alchemy getAlchemy(){
		return singleton;
	}

	@Override
	public ArrayList<Action> getActions() {
		ArrayList<Action> actions = new ArrayList<Action>();
		actions.add(CreatPotion.getCreatPotion());
		return actions;
	}
	
	private final static class CreatPotion extends Action {

		private static final CreatPotion singleton = new CreatPotion();
		private CreatPotion() {}
		
		public static CreatPotion getCreatPotion(){
			return singleton;
		}
		
		/**
		 * Cr�e une potion
		 */
		@Override
		public void run(Player p) {
			assert(this.isrunnable(p));
			p.getBag().remove(Plante.getPlante(), 3);
			p.getBag().add(Potion.getPotion(), 1);
			
		}

		@Override
		public boolean isrunnable(Player p) {
			return !p.isBusy() && p.getBag().has(Plante.getPlante(), 3);
		}
		
	}
}