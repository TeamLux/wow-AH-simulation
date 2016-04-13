package function;

import java.util.concurrent.ThreadLocalRandom;

public class Sleep {
	double[] function;
	public Sleep(double[] function){
		this.function = function;
	}
	
	public boolean deco(int day, int hour){
		double r = ThreadLocalRandom.current().nextDouble();
		int i = day*24+hour;
		if(r>function[i]){
			return true;
		}
		return false;
	}
	
	public boolean wakeUp(int day, int hour){
		return !deco(day,hour);
	}
}
