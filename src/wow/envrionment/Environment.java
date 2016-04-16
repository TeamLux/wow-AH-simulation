package wow.envrionment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import wow.action.Action;
import wow.action.Quest;
import wow.ah.AH;

public class Environment {
	private Calendar cal;
	private AH ah;
	
	private static Environment singleton = new Environment();
	private Environment() {
		this.ah = AH.getInstance();
		this.cal = Calendar.getInstance();
	}
	
	public static Environment getInstance(){
		return singleton;
	}
	
	public void oneHourAhead(){
		cal.add(Calendar.HOUR_OF_DAY, 1);
		ah.oneHourAhead();
	}
	
	public int getHour(){
		return cal.get(Calendar.HOUR_OF_DAY);
	}
	
	public int getdayOfWeek(){
		return cal.get(Calendar.DAY_OF_WEEK)-1;
	}
	
	public Date getDate(){
		return cal.getTime();
	}
	public AH ah(){
		return ah;
	}
	
	public ArrayList<Action> getActions(){
		ArrayList<Action> actions = new ArrayList<Action>();
		actions.add(Quest.getInstance());
		return actions;
	}
}
