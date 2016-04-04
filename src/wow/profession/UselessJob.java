package wow.profession;

import java.util.ArrayList;

import wow.action.Action;

public class UselessJob extends Job {
	
	private static final UselessJob singleton = new UselessJob();
	private UselessJob() {}
	
	public static UselessJob getInstance(){
		return singleton;
	}

	@Override
	public ArrayList<Action> getActions() {
		return new ArrayList<Action>();
	}

}
