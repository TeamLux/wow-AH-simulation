package wow.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

import utility.Utility;
import wow.object.Bag;
import wow.profession.Alchemy;
import wow.profession.Herbalism;
import wow.profession.Job;
import wow.profession.UselessJob;

public class Player {
	ArrayList<Job> jobs = new ArrayList<Job>(4);
	private int busy = 0;
	private Bag bag = new Bag();
	private int gold;
	private Utility u;
	
	public final static int MAX_STUFF =  16;
	private int stuff = 0;
	
	public Player(Utility u){
		this.setJobs();
		this.u = u;
	}
	
	private void setJobs(){
		ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=0; i<10; i++) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);
		for (int i = 0; i < jobs.size(); i++) {
			if(list.get(i).equals(0)){
				jobs.set(i, Herbalism.getInstance());
			}
			else if(list.get(i).equals(1)){
				jobs.set(i, Alchemy.getInstance());
			}
			else{
				jobs.set(i, UselessJob.getInstance());
			}
		}
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
