package wow.action;

import wow.envrionment.Environment;
import wow.object.Plante;
import wow.object.Potion;
import wow.player.Player;

public final class CreatePotion extends Action {

	private static final CreatePotion singleton = new CreatePotion();
	private CreatePotion() {}
	
	public static CreatePotion getInstance(){
		return singleton;
	}
	
	/**
	 * Crée une potion
	 */
	@Override
	public boolean run(Player p, Environment e) {
		if(this.isrunnable(p, e)){	
			p.consume(Plante.getInstance(), 3);
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
