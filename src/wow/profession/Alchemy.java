package wow.profession;

import java.util.ArrayList;

import wow.action.Action;
import wow.action.CreatPotion;

public final class Alchemy extends Job {

	
	private static Alchemy singleton = new Alchemy();
	private Alchemy() {}
	
	public static Alchemy getInstance(){
		return singleton;
	}

	@Override
	public ArrayList<Action> getActions() {
		ArrayList<Action> actions = new ArrayList<Action>();
		actions.add(CreatPotion.getInstance());
		return actions;
	}
}
