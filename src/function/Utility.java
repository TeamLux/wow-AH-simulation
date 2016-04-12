package function;

import wow.object.Bag;
import wow.object.WowObject;

public class Utility {
	boolean logGold;
	double[] parameters;
	public Utility(boolean logGold, double[] parameters){
		this.logGold = logGold;
		this.parameters = parameters;
	}
	
	public double f(int gold, int stuff, int tired, Bag bag){
		double res = 0;
		if(logGold)
			res+=Math.log(gold)/Math.log(this.parameters[0]);
		else
			res+=this.parameters[0]*gold;
		res+=this.parameters[1]*stuff;
		
		res-=tired*this.parameters[2];
		
		for (int i = 3; i < parameters.length; i++) {
			res+=this.parameters[i]*bag.howMany(i-3);
		}
		return res;
	}
	
	public int minPriceForSale(int gold, WowObject o){
		int res;
		if(logGold){
			 res = (int) (gold*(Math.pow(this.parameters[0],this.parameters[o.id()+3])-1))+1;
		}
		else{
			res = (int) (this.parameters[o.id()+3]/this.parameters[0])+1;
		}
		return res;
		
	}
}
