package wow.ah;

import java.util.ArrayList;

public class AH {
	private static ArrayList<Sale> sales = new ArrayList<Sale>();
	
	private static AH singleton = new AH();
	private AH() {}
	
	public static AH getInstance(){
		return singleton;
	}
	
	public static ArrayList<Sale> getSales(){
		return sales;
	}
	
	public void addSale(Sale sale){
		sales.add(sale);
	}
	
	public void removeSale(Sale sale){
		sales.remove(sale);
	}
	
	public boolean hasSale(Sale sale){
		return sales.contains(sale);
	}
	
	public void oneHourAhead(){
		for (Sale sale : sales){
			if(sale.oneHourAhead()){
				sales.remove(sale);
			}
		}
	}
}
