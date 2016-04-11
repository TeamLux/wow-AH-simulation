package function;

import wow.object.Bag;

public class Utility {
	boolean logGold;
	double[] parameters;
	public Utility(boolean logGold, double[] parameters){
		this.logGold = logGold;
		this.parameters = parameters;
	}
	
	public double f(int gold, int stuff, Bag bag){
		double res = 0;
		if(logGold)
			res+=this.parameters[0]*Math.log(gold);
		else
			res+=this.parameters[0]*gold;
		res+=this.parameters[1]*stuff;
		for (int i = 2; i < parameters.length; i++) {
			res+=this.parameters[i]*bag.howMany(i-2);
		}
		return res;
	}
}
