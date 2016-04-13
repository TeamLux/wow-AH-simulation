package learning;

import java.util.concurrent.ThreadLocalRandom;

public class SellLearing {
	double[] nbToSell = new double[7*24];
	int[] history = new int[7*24];
	double alpha;
	int avgPrice = 0;
	
	
	public SellLearing(double alpha){
		for (int i = 0; i < nbToSell.length; i++) {
			nbToSell[i] = 0;
			history[i] = 1;
		}
		this.alpha = alpha;
	}
	
	public int nbToSell(int max, int day, int hour){
		double r = ThreadLocalRandom.current().nextDouble();
		int i = day*24 + hour;
		int res = 0;
		if(r<1/history[i]){
			//Explore
			res = max;
		}
		else{
			//Exploit
			res = (int)nbToSell[i];
		}
		return res;
	}
	
	public int sellPrice(int min, int marketPrice, boolean imTheBest){
		if(imTheBest){
			return marketPrice;
		}
		else if(marketPrice == 0){//No object on the market
			return Math.max(min, avgPrice);
		}
		else{
			double r = ThreadLocalRandom.current().nextDouble();
			return (int)(marketPrice-r*(marketPrice-min));
		}
	}
	
	public void update(int reward, int day, int hour, int price){
		int i = day*24 + hour;
		history[i]+=1;
		nbToSell[i] += this.alpha*(reward-nbToSell[i]);
		if(price>0)
			avgPrice += this.alpha*(price-avgPrice);
	}
}
