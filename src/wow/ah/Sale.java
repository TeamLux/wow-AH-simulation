package wow.ah;

import wow.object.WowObject;
import wow.player.Player;

public class Sale {
	Player seller;
	WowObject object;
	int price;
	int timeLeft;
	
	public Sale(Player p, WowObject o, int price){
		this.seller = p;
		this.object = o;
		this.price = price;
		this.timeLeft = 48;
	}
	
	/**
	 * Decrement the time left of one hour
	 * @return True if the time left == 0 whereas False
	 */
	public boolean oneHourAhead(){
		this.timeLeft -= 1;
		if(this.timeLeft == 0){
			seller.getBag().add(object, 1);
			return true;
		}
		else
			return false;
	}
	
	public WowObject getObject(){
		return this.object;
	}
	
	public Player getSeller(){
		return this.seller;
	}
	
	public int getPrice(){
		return this.price;
	}
}
