package wow.player;

import java.util.ArrayList;

import wow.action.Action;

public interface Seller {
	ArrayList<Action> getSellerAction(int[] qSell);
}
