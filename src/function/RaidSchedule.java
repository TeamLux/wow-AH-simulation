package function;

public class RaidSchedule {
	boolean[] function;
	public RaidSchedule(boolean[] function){
		this.function = function;
	}
	
	public boolean timeToRaid(int day, int hour){
		return function[day*24+hour];
	}
}
