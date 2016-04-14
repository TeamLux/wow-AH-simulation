package function;

import java.util.concurrent.ThreadLocalRandom;

public class Sleep {
	double[] function;
	private RaidSchedule rs;
	public Sleep(double[] function, RaidSchedule rs){
		this.function = function;
		this.rs = rs;
	}
	
	public boolean deco(int day, int hour){
		if(rs.timeToRaid(day, hour)){
			return false;
		}
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
