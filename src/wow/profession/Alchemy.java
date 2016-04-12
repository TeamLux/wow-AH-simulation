package wow.profession;

import java.util.ArrayList;

import wow.action.Action;
import wow.action.CreatePotion;

public final class Alchemy extends Job {

	
	private static Alchemy singleton = new Alchemy();
	private Alchemy() {}
	
	public static Alchemy getInstance(){
		return singleton;
	}

	@Override
	public ArrayList<Action> getActions() {
		ArrayList<Action> actions = new ArrayList<Action>();
		actions.add(CreatePotion.getInstance());
		return actions;
	}

	@Override
	public boolean isFast() {
		return true;
	}
}
