package wow.action;

import wow.ah.Sale;
import wow.envrionment.Environment;
import wow.object.Bag;
import wow.player.Player;

public final class Buy extends Action {
	
	Sale sale;
	
	public Buy(Sale sale){
		this.sale = sale;
	}

	@Override
	public synchronized boolean run(Player p, Environment e) {
		if(this.isrunnable(p, e)&& e.ah().hasSale(this.sale)){
			e.ah().buy(this.sale);
			e.ah().removeSale(this.sale);
			//Buyer
			p.earn(-this.sale.getPrice());
			p.getBag().add(this.sale.getObject(), 1);
			//Seller
			this.sale.getSeller().earn((int)(this.sale.getPrice()*0.95)); //vente
			this.sale.getSeller().earn((int)(this.sale.getObject().sellPrice()*0.6));//dépot
			this.sale.getSeller().saleSucceed(this.sale);
		}
		return false;
	}

	@Override
	public boolean isrunnable(Player p, Environment e) {
		return p.canDo() && p.getGold() > this.sale.getPrice();
	}

	@Override
	public double potentielUtility(Player p, Environment e) {
		Bag tmpBag = new Bag(p.getBag());
		tmpBag.add(this.sale.getObject(), 1);
		double newU = p.getUtility().f(p.getGold()-this.sale.getPrice(), p.getStuff(),p.isTiredFor(),tmpBag);
		return (newU - p.currentUtility());
	}

}
