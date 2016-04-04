package wow.action;

import wow.ah.Sale;
import wow.envrionment.Environment;
import wow.player.Player;

public class Buy extends Action {
	
	Sale sale;
	public Buy(Sale sale){
		this.sale = sale;
	}

	@Override
	public boolean run(Player p, Environment e) {
		if(this.isrunnable(p, e)){		
			e.ah().removeSale(this.sale);
			//Buyer
			p.earn(-this.sale.getPrice());
			p.getBag().add(this.sale.getObject(), 1);
			//Seller
			this.sale.getSeller().earn((int)(this.sale.getPrice()*0.95));
			return true;
		}
		return false;
	}

	@Override
	public boolean isrunnable(Player p, Environment e) {
		return !p.isBusy() && p.getGold() > this.sale.getPrice() && e.ah().hasSale(this.sale);
	}

	@Override
	public int potentielUtility(Player p, Environment e) {
		// TODO Auto-generated method stub
		return 0;
	}

}
