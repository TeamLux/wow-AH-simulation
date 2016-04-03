package wow.profession;

import java.util.ArrayList;

import wow.action.Action;

public class UselessJob extends Job {

	@Override
	public ArrayList<Action> getActions() {
		return new ArrayList<Action>();
	}

}
