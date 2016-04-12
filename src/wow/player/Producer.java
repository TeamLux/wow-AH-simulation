package wow.player;

import java.util.ArrayList;

import wow.action.Action;

public interface Producer {
	ArrayList<Action> getPorducerAction(boolean isFast);
}
