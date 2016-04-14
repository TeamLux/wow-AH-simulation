package function;

import java.util.concurrent.ThreadLocalRandom;

public class AHSchedule {
	private double[] function;
	private RaidSchedule rs;
	public AHSchedule(double[] function, RaidSchedule rs){
		this.function = function;
		this.rs = rs;
	}
	
	public boolean timeToAH(int day, int hour){
		if(rs.timeToRaid(day, hour)){
			return true;
		}
		double r = ThreadLocalRandom.current().nextDouble();
		int i = day*24+hour;
		if(r<=function[i]){
			return true;
		}
		return false;
	}
}
