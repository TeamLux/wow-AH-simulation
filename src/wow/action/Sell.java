package wow.action;

import wow.ah.Sale;
import wow.envrionment.Environment;
import wow.object.WowObject;
import wow.player.Player;

public class Sell extends Action {
	
	private WowObject object;
	private int price;
	
	public Sell(WowObject o, int price){
		this.object = o;
		this.price = price;
	}

	@Override
	public void run(Player p, Environment e) {
		assert(this.isrunnable(p, e));
		Sale sale = new Sale(p, this.object, this.price);
		e.ah().addSale(sale);
		p.getBag().remove(this.object, 1);
		p.earn((int)(-0.6*this.object.sellPrice())); //Depot
	}

	@Override
	public boolean isrunnable(Player p, Environment e) {
		return !p.isBusy() && p.getBag().has(this.object, 1);
	}

	@Override
	public int potentielUtility(Player p, Environment e) {
		// TODO Auto-generated method stub
		return 0;
	}

}
