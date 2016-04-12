package wow.player;

import java.util.ArrayList;

import function.AHSchedule;
import function.RaidSchedule;
import function.Sleep;
import function.Utility;
import learning.SellLearing;
import wow.action.Action;
import wow.action.Raid;
import wow.action.Sell;
import wow.ah.Sale;
import wow.envrionment.Environment;
import wow.object.Bag;
import wow.object.WowObject;
import wow.object.WowObjects;
import wow.profession.Job;

public class Player implements Runnable, Buyer, Consumer,Producer,Seller{
	public ArrayList<Job> jobs = new ArrayList<Job>(4);
	private int busy = 0;
	private boolean sleep = true;
	private Bag bag = new Bag();
	private int gold;
	private Utility u;
	private RaidSchedule rs;
	private Sleep s;
	private Environment e;
	private AHSchedule ahs;
	private SellLearing[] sls;
	private int tired = 0;
	public boolean hasDoQuest = false;
	
	public final static int MAX_STUFF =  16;
	private int stuff = 0;
	
	public Player(Utility u, RaidSchedule rs, Sleep s, Environment e, AHSchedule ahs, ArrayList<Job> jobs, double alpha){
		this.u = u;
		this.rs = rs;
		this.s = s;
		this.e = e;
		this.jobs = jobs;
		this.ahs = ahs;
		this.sls = new SellLearing[WowObjects.size()];
		for (int i = 0; i < sls.length; i++) {
			sls[i] = new SellLearing(alpha);
		}
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
			this.runAH();
			Raid.getInstance().run(this, this.e);
			return;
		}
		else if(ahs.timeToAH()){
			this.runAH();
		}
		else{
			this.runNonAh();
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
	
	public void tiredFor(int h){
		this.tired += h;
	}
	
	public int isTiredFor(){
		return this.tired;
	}
	
	public void busyFor(int h){
		this.busy *= h;
	}
	
	public boolean isBusy(){
		return busy > 0;
	}
	
	public boolean canDo(){
		return !this.sleep && !this.isBusy();
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
	
	public double currentUtility(){
		return this.u.f(this.gold,this.stuff,this.tired,this.bag);
	}
	
	public int getStuff(){
		return this.stuff;
	}
	
	private void runAH(){
		int[] qSell = new int[WowObjects.size()];
		for (int i = 0; i < qSell.length; i++) {
			qSell[i] = sls[i].nbToSell(Integer.MAX_VALUE, e.getdayOfWeek(), e.getHour());
		}
		while(true){
			ArrayList<Action> actions = new ArrayList<Action>();
			actions.addAll(this.getPorducerAction(true));
			for (Action action : actions) {
				while(action.isrunnable(this, e)){
					action.run(this, e);
				}
			}
			
			actions = new ArrayList<Action>();
			actions.addAll(this.getBuyerAction());
			actions.addAll(this.getSellerAction(qSell));
			Action best = null;
			double bestU = 0;
			for (Action action : actions) {
				if(!action.isrunnable(this, e))
					continue;
				double actionU = action.potentielUtility(this, e);
				if(bestU < actionU){
					best = action;
					bestU = actionU;
				}
			}
			if(best == null)
				break;
			best.run(this, e);
			
			if(this.bag.lastAction() == Bag.REMOVE){
				qSell[bag.lastRemove().id()] -= 1;
			}
		}
	}
	
	private void runNonAh(){
		ArrayList<Action> actions = new ArrayList<Action>();
		actions.addAll(this.getPorducerAction(false));
		actions.addAll(e.getActions());
		Action best = null;
		double bestU = 0;
		for (Action action : actions) {
			if(!action.isrunnable(this, e))
				continue;
			double actionU = action.potentielUtility(this, e);
			if(bestU < actionU){
				best = action;
				bestU = actionU;
			}
		}
		if(best == null)
			return;
		best.run(this, e);
		
	}

	@Override
	public ArrayList<Action> getSellerAction(int[] qSell) {
		ArrayList<Action> sells = new ArrayList<Action>();
		for (int i = 0; i < qSell.length; i++) {
			WowObject o = WowObjects.get(i);
			if(qSell[i]==0 || !bag.has(o, 1))
				continue;
			Sale bestSale = e.ah().getBestSale(o);
			int min = this.u.minPriceForSale(this.gold, o);
			int price = sls[i].sellPrice(min, bestSale.getPrice(), bestSale.getSeller().equals(this));
			sells.add(new Sell(o,price));
			
		}
		return null;
	}

	@Override
	public ArrayList<Action> getPorducerAction(boolean isFast) {
		ArrayList<Action> porducer = new ArrayList<Action>();
		
		for (int i = 0; i < jobs.size(); i++) {
			if((isFast && jobs.get(i).isFast()) || (!isFast && !jobs.get(i).isFast()))
				porducer.addAll(jobs.get(i).getActions());
		}
		return porducer;
	}
	

	@Override
	public ArrayList<Action> getBuyerAction() {
		ArrayList<Action> buys = new ArrayList<Action>();
		
		for (int i = 0; i < WowObjects.size(); i++) {
			buys.add(this.e.ah().getBestBuyActions(WowObjects.get(i)));
		}
		return buys;
	}
}
