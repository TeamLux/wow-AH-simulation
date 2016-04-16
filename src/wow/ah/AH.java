package wow.ah;

import java.util.ArrayList;
import java.util.HashMap;

import wow.action.Buy;
import wow.object.WowObject;

public class AH {
	private static ArrayList<Sale> sales = new ArrayList<Sale>();
	private static ArrayList<Buy> buys = new ArrayList<Buy>();
	
	private static ArrayList<Sale> hasBeenBuy = new ArrayList<Sale>();
	private static ArrayList<Sale> hasBeenUpToSell = new ArrayList<Sale>();
	
	private static AH singleton = new AH();
	private static HashMap<WowObject,Sale> bestSales = new HashMap<WowObject,Sale>();
	private AH() {}
	
	public static AH getInstance(){
		return singleton;
	}
	
	public synchronized void addSale(Sale sale){
		hasBeenUpToSell.add(sale);
		sales.add(sale);
		buys.add(new Buy(sale));
		if(bestSales.get(sale.getObject()) == null){
			bestSales.put((sale.getObject()),sale);
		}
		else if(sale.getPrice() < bestSales.get(sale.getObject()).getPrice()){
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
		hasBeenBuy.clear();
		hasBeenUpToSell.clear();
		for (int i = sales.size()-1; i >= 0; i--) {
			Sale sale = sales.get(i);
			if(sale.oneHourAhead()){
				this.removeSale(sale);
				sale.getSeller().notSale(sale);
			}
		}
	}
	
	public ArrayList<Buy> getBuyActions(){
		return buys;
	}
	
	public synchronized Buy getBestBuyActions(WowObject o){
		if(bestSales.get(o)!=null)
			return buys.get(sales.indexOf(bestSales.get(o)));
		return null;
	}
	
	public synchronized Sale getBestSale(WowObject o){
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
	
	public synchronized void buy(Sale sale){
		hasBeenBuy.add(sale);
	}
	
	public int nSale(WowObject o){
		int n =0;
		for(Sale sale: sales){
			if(sale.getObject().equals(o))
				n++;
		}
		return n;
	}
	
	public int nBuy(WowObject o){
		int n =0;
		for(Sale sale: hasBeenBuy){
			if(sale.getObject().equals(o))
				n++;
		}
		return n;
	}
	
	public int getPriceBuy(WowObject o){
		int n =0;
		int price = 0;
		for(Sale sale: hasBeenBuy){
			if(sale.getObject().equals(o)){
				n++;
				price += sale.getPrice();
			}
				
		}
		return n!=0?price/n:0;
	}
	
	public int getPriceSell(WowObject o){
		int n =0;
		long price = 0;
		for(Sale sale: hasBeenUpToSell){
			if(sale.getObject().equals(o)){
				n++;
				price += sale.getPrice();
			}
				
		}
		return (int)(n!=0?price/n:0);
	}
	
	public int getPriceAvg(WowObject o){
		int n =0;
		long price = 0;
		for(Sale sale: sales){
			if(sale.getObject().equals(o)){
				n++;
				price += sale.getPrice();
			}
				
		}
		return (int)(n!=0?price/n:0);
	}
	
	public int nSell(WowObject o){
		int n =0;
		for(Sale sale: hasBeenUpToSell){
			if(sale.getObject().equals(o))
				n++;
		}
		return n;
	}

}
