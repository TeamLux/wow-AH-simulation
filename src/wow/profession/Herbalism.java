package wow.profession;


import java.util.ArrayList;

import wow.action.Action;
import wow.action.HerbGathering;

public final class Herbalism extends Job {
	
	private static Herbalism singleton = new Herbalism();
	private Herbalism() {}
	
	public static Herbalism getInstance(){
		return singleton;
	}

	@Override
	public ArrayList<Action> getActions() {
		ArrayList<Action> actions = new ArrayList<Action>();
		actions.add(HerbGathering.getInstance());
		return actions;
	}

	@Override
	public boolean isFast() {
		return false;
	}
}
