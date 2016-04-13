package function;

import java.util.concurrent.ThreadLocalRandom;

public class AHSchedule {
	double[] function;
	public AHSchedule(double[] function){
		this.function = function;
	}
	
	public boolean timeToAH(int day, int hour){
		double r = ThreadLocalRandom.current().nextDouble();
		int i = day*24+hour;
		if(r<=function[i]){
			return true;
		}
		return false;
	}
}
