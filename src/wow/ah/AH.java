package wow.ah;

import java.util.ArrayList;
import java.util.Hashtable;

import wow.action.Buy;
import wow.object.WowObject;

public class AH {
	private static ArrayList<Sale> sales = new ArrayList<Sale>();
	private static ArrayList<Buy> buys = new ArrayList<Buy>();
	
	private static AH singleton = new AH();
	private static Hashtable<WowObject,Sale> bestSales = new Hashtable<WowObject,Sale>();
	private AH() {}
	
	public static AH getInstance(){
		return singleton;
	}
	
	public synchronized void addSale(Sale sale){
		sales.add(sale);
		buys.add(new Buy(sale));
		if(sale.getPrice() <bestSales.get(sale.getObject()).getPrice()){
			bestSales.put((sale.getObject()),sale);
		}
	}
	
	public synchronized void removeSale(Sale sale){
		int i = sales.indexOf(sale);
		sales.remove(i);
		buys.remove(i);
		if(bestSales.get(sale.getObject()).equals(sale)){
			this.updateBest(sale.getObject());
		}
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
	
	public ArrayList<Buy> getBuyActions(){
		return buys;
	}
	
	public Buy getBestBuyActions(WowObject o){
		return buys.get(sales.indexOf(bestSales.get(o)));
	}
	
	public Sale getBestSale(WowObject o){
		return bestSales.get(o);
	}
	
	private synchronized void updateBest(WowObject o){
		Sale best = null;
		for (Sale sale : sales) {
			if(!sale.getObject().equals(o))
				continue;
			if(best == null || best.getPrice()>sale.getPrice()){
				best = sale;
			}
		}
		bestSales.put(o, best);
	}

}
