package wow.player;

import java.util.ArrayList;

import function.RaidSchedule;
import function.Sleep;
import function.Utility;
import wow.action.Raid;
import wow.envrionment.Environment;
import wow.object.Bag;
import wow.object.WowObject;
import wow.profession.Job;

public class Player implements Runnable, Buyer, Consumer,Producer,Seller{
	ArrayList<Job> jobs = new ArrayList<Job>(4);
	private int busy = 0;
	private boolean sleep = true;
	private Bag bag = new Bag();
	private int gold;
	private Utility u;
	private RaidSchedule rs;
	private Sleep s;
	private Environment e;
	
	public final static int MAX_STUFF =  16;
	private int stuff = 0;
	
	public Player(Utility u, RaidSchedule rs, Sleep s, Environment e, ArrayList<Job> jobs){
		this.u = u;
		this.rs = rs;
		this.s = s;
		this.e = e;
		this.jobs = jobs;
	}
	
	@Override
	public void run() {
		if (!this.sleep && this.s.deco()){
			this.sleep = true;
			return;
		}
		else if(this.sleep && this.s.wakeUp()){
			this.sleep = false;
		}
		else if(this.sleep || this.isBusy()){
			this.busy = Math.max(this.busy-1, 0);
			return;
		}
		
		//Player doesn't sleep and isn't busy
		
		if(rs.timeToRaid()){
			Raid.getInstance().run(this, this.e);
			return;
		}
	}
	
	@Override
	public void consume(WowObject o, int quantity) {
		this.bag.remove(o, quantity);
		
	}
	
	public void earn(int gold){
		this.gold += gold;
	}
	
	public void addOneStuff(){
		if(this.stuff < Player.MAX_STUFF)
			this.stuff++;
	}
	
	public void clearStuff(){
		this.stuff = 0;
	}
	
	/**
	 * Bloque le joueur pour h heures 
	 * @param h
	 */
	public void busyFor(int h){
		this.busy += h;
	}
	
	public boolean isBusy(){
		return busy > 0;
	}
	
	public Bag getBag(){
		return this.bag;
	}
	
	public int getGold(){
		return this.gold;
	}
	
	public Utility getUtility(){
		return this.u;
	}
	
	public int getStuff(){
		return this.stuff;
	}
}
