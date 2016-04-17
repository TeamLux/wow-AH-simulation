package game;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import function.AHSchedule;
import function.RaidSchedule;
import function.Sleep;
import function.Utility;
import wow.action.HerbGathering;
import wow.action.Quest;
import wow.ah.Sale;
import wow.envrionment.Environment;
import wow.object.Plante;
import wow.object.Potion;
import wow.object.WowObject;
import wow.player.Player;
import wow.profession.Alchemy;
import wow.profession.Herbalism;
import wow.profession.Job;
import wow.profession.UselessJob;

public class Game {
	
	static final double[] zeros = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	//                             0h ,1h  ,2h  ,3h  ,4h  ,5h  ,6h  ,7h   ,8h  ,9h  ,10h ,11h ,12h ,13h ,14h ,15h,16h  ,17h  ,18h ,19h ,20h ,21h,22h ,23h
	static final double[] hours = {0.4,0.25,0.16,0.08,0.04,0.04,0.08,0.125,0.15,0.16,0.21,0.29,0.33,0.41,0.46,0.5,0.375,0.375,0.83,0.66,0.58,0.5,0.41,0.41};
	static final double[] days = {0.88,0.83,0.79,0.91,0.84,0.86,0.90};
	static final double[] ahDays = {0.70,0.67,0.58,0.75,0.63,0.65,0.74};
	//                               0h   ,1h    ,2h    ,3h    ,4h    ,5h   ,6h    ,7h    ,8h    ,9h    ,10h   ,11h ,12h   ,13h ,14h   ,15h   ,16h  ,17h   ,18h   ,19h   ,20h   ,21h   ,22h   ,23h
	static final double[] ahHours = {0.463,0.1625,0.0936,0.0486,0.0299,0.026,0.0255,0.0219,0.0528,0.0663,0.0886,0.13,0.1825,0.19,0.1961,0.3026,0.325,0.3538,0.3782,0.4223,0.9696,0.8226,0.2808,0.1138};
	
	Player[] players;
	Environment e;
	Thread[] threads;
	double alpha;
	int nPlayer;
	
	double pHerb;
	double pAlchemy ;
	
	public Game(double alpha, int nPlante, double goldEarn, int nPlayer, double pHerbe, double pAlchemy){
		this.alpha = alpha;
		e = Environment.getInstance();
		Quest.earnGold = (int)(goldEarn*10000);
		HerbGathering.nbPlantes = nPlante;
		players = new Player[nPlayer];
		threads = new Thread[nPlayer];
		this.pHerb = pHerbe;
		this.pAlchemy = pAlchemy;
		this.nPlayer = nPlayer;
		
		for(int i = 0; i < nPlayer; i++){
			players[i] = createPlayer();
		}
		
	}
	
	public Game(double alpha, int nPlante, double goldEarn, int nPlayer){
		this(alpha, nPlante, goldEarn, nPlayer, 0.3439, 0.3439);	
	}
	
	public void serveurUpdate(){
		for(int i = 0; i < nPlayer; i++){
			players[i].clearStuff();
		}
	}
	private void onHourAHead(){
		for(int i = 0; i < nPlayer; i++){
			players[i].oneHourAhead();
		}
		e.oneHourAhead();
	}
	public void run(int n) throws InterruptedException {

		
		for(int k = 0; k < n;k++){
			for(int i = 0; i < nPlayer; i++){
				threads[i] = new Thread(players[i]);
			}
			for(int i = 0; i < nPlayer; i++){
				threads[i].start();
			}
			for(int i = 0; i < nPlayer; i++){
				threads[i].join();
			}
			
			
			WowObject o = Potion.getInstance();
			Sale best = e.ah().getBestSale(o);
			if(best != null)
				System.out.println(e.getDate()+"\t"+e.ah().getPriceAvg(o)/10000.0+"\t"+best.getPrice()/10000.0+"\t"+e.ah().nSale(o)+"\t"+e.ah().getPriceBuy(o)/10000.0+"\t"+e.ah().nBuy(o)+"\t"+e.ah().getPriceSell(o)/10000.0+"\t"+e.ah().nSell(o));
			else
				System.out.println(e.getDate()+"\t"+0+"\t"+0+"\t"+0+"\t"+e.ah().getPriceBuy(o)/10000.0+"\t"+e.ah().nBuy(o)+"\t"+e.ah().getPriceSell(o)/10000.0+"\t"+e.ah().nSell(o));
			this.onHourAHead();
			
		}
	}
	
	public Player createPlayer(){
		double r;
		//Sleep & AHSchedule & RS
		double[] sleepFunction = new double[24*7];
		double[] ahFunction = new double[24*7];
		boolean[] raidScheduleFunction = new boolean[7*24];
		for (int i = 0; i < days.length; i++) {
			r = ThreadLocalRandom.current().nextDouble();
			if(r<=days[i]){
				for (int j = 0; j < hours.length; j++) {
					sleepFunction[i*24+j] = hours[j];
				}
				r = ThreadLocalRandom.current().nextDouble();
				if(r<=ahDays[i]){
					for (int j = 0; j < ahHours.length; j++) {
						ahFunction[i*24+j] = hours[j];
					}
					r = ThreadLocalRandom.current().nextDouble();
					int hour = 21;
					if(r<0.5){
						hour+= ThreadLocalRandom.current().nextInt(-1, 2);
					}
					for (int j = 0; j < 24; j++) {
						if(j%24 == hour){
							raidScheduleFunction[i*24+j]=true;
						}
						else{
							raidScheduleFunction[i*24+j]=false;
						}
					}
				}
				else{
					for (int j = 0; j < zeros.length; j++) {
						ahFunction[i*24+j] = zeros[j];
					}
				}
			}
			else{
				for (int j = 0; j < zeros.length; j++) {
					sleepFunction[i*24+j] = zeros[j];
					ahFunction[i*24+j] = zeros[j];
				}
			}
		}
		RaidSchedule rs = new RaidSchedule(raidScheduleFunction);
		
		Sleep s = new Sleep(sleepFunction, rs);
		
		AHSchedule ahs = new AHSchedule(ahFunction, rs);
			
		//Job & utility
		ArrayList<Job> jobs = new ArrayList<Job>();
		Utility u;
		r = ThreadLocalRandom.current().nextDouble();
		if(r<=pHerb)
			jobs.add(Herbalism.getInstance());
		r = ThreadLocalRandom.current().nextDouble();
		if(r<=pAlchemy)
			jobs.add(Alchemy.getInstance());
		r = ThreadLocalRandom.current().nextDouble();
		double tmp;
		if(jobs.size() == 0){
			tmp = 8.5+r;
			double[] parameters = {1,16*9*10000,-1*10000,0,tmp*10000};
			u = new Utility(false, parameters, e, rs);
		}
		else if (jobs.size() == 2){
			tmp = 2.5+r;
			double[] parameters = {1,16*9*10000,-1,tmp/3*10000,tmp*10000};
			u = new Utility(false, parameters, e);
		}
		else if(jobs.contains(Herbalism.getInstance())){
			tmp = 7.5+r;
			double[] parameters = {1,16*9*10000,-1,0.8*10000,tmp*10000};
			u = new Utility(false, parameters, e, rs);
		}
		else{
			tmp = 5.5+r;
			double[] parameters = {1,16*9*10000,-1,tmp/3*10000,tmp*10000};
			u = new Utility(false, parameters, e);
		}
		for(int i = jobs.size(); i <4; i++){
			jobs.add(UselessJob.getInstance());
		}
		
		return new Player(u,rs,s,e,ahs,jobs,this.alpha);

	}

}

