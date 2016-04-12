package learning;

import java.util.concurrent.ThreadLocalRandom;

public class SellLearing {
	double[] nbToSell = new double[7*24];
	int[] history = new int[7*24];
	double alpha;
	
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
			res = Math.min((int)nbToSell[i],max);
		}
		return res;
	}
	
	public void update(int reward, int day, int hour){
		int i = day*24 + hour;
		history[i]+=1;
		nbToSell[i] += this.alpha*(reward-nbToSell[i]);
	}
}
