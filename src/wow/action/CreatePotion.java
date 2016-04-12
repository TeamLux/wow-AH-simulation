package wow.action;

import wow.envrionment.Environment;
import wow.object.Bag;
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
		return p.canDo() && p.getBag().has(Plante.getInstance(), 3);
	}

	@Override
	public double potentielUtility(Player p, Environment e) {
		Bag tmpBag = new Bag(p.getBag());
		tmpBag.add(Potion.getInstance(), 1);
		tmpBag.remove(Plante.getInstance(), 3);
		double newU = p.getUtility().f(p.getGold(), p.getStuff(),p.isTiredFor(),tmpBag);
		return (newU - p.currentUtility());
	}
	
}
