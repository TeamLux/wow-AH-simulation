package wow.action;

import wow.envrionment.Environment;
import wow.object.Plante;
import wow.object.Potion;
import wow.player.Player;

public final class CreatPotion extends Action {

	private static final CreatPotion singleton = new CreatPotion();
	private CreatPotion() {}
	
	public static CreatPotion getInstance(){
		return singleton;
	}
	
	/**
	 * Crée une potion
	 */
	@Override
	public boolean run(Player p, Environment e) {
		if(this.isrunnable(p, e)){	
			p.getBag().remove(Plante.getInstance(), 3);
			p.getBag().add(Potion.getInstance(), 1);
			return true;
		}
		return false;	
	}

	@Override
	public boolean isrunnable(Player p, Environment e) {
		return !p.isBusy() && p.getBag().has(Plante.getInstance(), 3);
	}

	@Override
	public int potentielUtility(Player p, Environment e) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
