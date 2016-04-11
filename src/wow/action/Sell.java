package wow.action;

import wow.ah.Sale;
import wow.envrionment.Environment;
import wow.object.Bag;
import wow.object.WowObject;
import wow.player.Player;

public final class Sell extends Action {
	
	private WowObject object;
	private int price;
	
	public Sell(WowObject o, int price){
		this.object = o;
		this.price = price;
	}

	@Override
	public boolean run(Player p, Environment e) {
		if(this.isrunnable(p, e)){	
			Sale sale = new Sale(p, this.object, this.price);
			e.ah().addSale(sale);
			p.getBag().remove(this.object, 1);
			p.earn((int)(-0.6*this.object.sellPrice())); //Depot
			return true;
		}
		return false;
	}

	@Override
	public boolean isrunnable(Player p, Environment e) {
		return p.canDo() && p.getBag().has(this.object, 1);
	}

	@Override
	public double potentielUtility(Player p, Environment e) {
		Bag tmpBag = new Bag(p.getBag());
		tmpBag.remove(this.object, 1);
		double newU = p.getUtility().f(p.getGold()+(int)(this.price*0.95), p.getStuff(),tmpBag);
		return (newU - p.currentUtility());
	}

}
