package wow.ah;

import java.util.ArrayList;

import wow.action.Action;
import wow.action.Buy;
import wow.action.Sell;
import wow.envrionment.Environment;
import wow.player.Player;

public class AH {
	private static ArrayList<Sale> sales = new ArrayList<Sale>();
	private static ArrayList<Buy> buys = new ArrayList<Buy>();
	
	private static AH singleton = new AH();
	private AH() {}
	
	public static AH getInstance(){
		return singleton;
	}
	
	public synchronized void addSale(Sale sale){
		sales.add(sale);
		buys.add(new Buy(sale));
	}
	
	public synchronized void removeSale(Sale sale){
		int i = sales.indexOf(sale);
		sales.remove(i);
		buys.remove(i);
	}
	
	public synchronized boolean hasSale(Sale sale){
		return sales.contains(sale);
	}
	
	public void oneHourAhead(){
		for (Sale sale : sales){
			if(sale.oneHourAhead()){
				this.removeSale(sale);
			}
		}
	}
	
	public ArrayList<Buy> getBuyAnctions(){
		return buys;
	}

}
