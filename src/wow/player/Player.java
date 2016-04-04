package wow.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

import wow.object.Bag;
import wow.profession.Alchemy;
import wow.profession.Herbalism;
import wow.profession.Job;
import wow.profession.UselessJob;

public class Player {
	private ArrayList<Job> jobs = new ArrayList<Job>(4);
	private int busy = 0;
	private Bag bag = new Bag();
	
	public Player(){
		this.setJobs();
		
	}
	
	private void setJobs(){
		ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=0; i<10; i++) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);
		for (int i = 0; i < jobs.size(); i++) {
			if(list.get(i).equals(0)){
				jobs.set(i, Herbalism.getHerbalism());
			}
			else if(list.get(i).equals(1)){
				jobs.set(i, Alchemy.getAlchemy());
			}
			else{
				jobs.set(i, UselessJob.getUselessJob());
			}
		}
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
}
