package function;

public class RaidSchedule {
	boolean[] function;
	public RaidSchedule(boolean[] function){
		this.function = function;
	}
	
	public boolean timeToRaid(int day, int hour){
		return function[day*24+hour];
	}
	
	public int timeTonextRaid(int day, int hour){
		int offset = day*24+hour;
		for (int i = 0; i < function.length; i++) {
			if(function[(i+offset)%function.length])
				return i;
		}
		return Integer.MAX_VALUE;
	}
}
