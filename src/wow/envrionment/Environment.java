package wow.envrionment;

import java.util.Calendar;

import wow.ah.AH;

public class Environment {
	Calendar cal;
	AH ah;
	
	private static Environment singleton = new Environment();
	private Environment() {
		
	}
	
	public static Environment getInstance(){
		return singleton;
	}
	
	public void oneHourAhead(){
		cal.add(Calendar.HOUR_OF_DAY, 1);
	}
	
	public int getHour(){
		return cal.get(Calendar.HOUR_OF_DAY);
	}
	
	public int getdayOfWeek(){
		return cal.get(Calendar.DAY_OF_WEEK);
	}
	
	public AH ah(){
		return ah;
	}
}
