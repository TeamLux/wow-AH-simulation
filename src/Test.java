import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ThreadLocalRandom;

import function.AHSchedule;
import function.RaidSchedule;
import function.Sleep;
import function.Utility;
import wow.ah.Sale;
import wow.envrionment.Environment;
import wow.object.Plante;
import wow.object.Potion;
import wow.object.WowObjects;
import wow.player.Player;
import wow.profession.Alchemy;
import wow.profession.Herbalism;
import wow.profession.Job;
import wow.profession.UselessJob;

public class Test {
	
	static final double[] zeros = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	//                0h ,1h  ,2h  ,3h  ,4h  ,5h  ,6h  ,7h   ,8h  ,9h  ,10h ,11h ,12h ,13h ,14h ,15h,16h  ,17h  ,18h ,19h ,20h ,21h,22h ,23h
	static final double[] hours = {0.4,0.25,0.16,0.08,0.04,0.04,0.08,0.125,0.15,0.16,0.21,0.29,0.33,0.41,0.46,0.5,0.375,0.375,0.83,0.66,0.58,0.5,0.41,0.41};
	static final double[] days = {0.88,0.83,0.79,0.91,0.84,0.86,0.90};
	static final double[] ahDays = {0.70,0.67,0.58,0.75,0.63,0.65,0.74};
	//                  0h   ,1h    ,2h    ,3h    ,4h    ,5h   ,6h    ,7h    ,8h    ,9h    ,10h   ,11h ,12h   ,13h ,14h   ,15h   ,16h  ,17h   ,18h   ,19h   ,20h   ,21h   ,22h   ,23h
	static final double[] ahHours = {0.463,0.1625,0.0936,0.0486,0.0299,0.026,0.0255,0.0219,0.0528,0.0663,0.0886,0.13,0.1825,0.19,0.1961,0.3026,0.325,0.3538,0.3782,0.4223,0.9696,0.8226,0.2808,0.1138};
	

	public static void main(String[] args) throws InterruptedException {
		
		Environment e = Environment.getInstance();
		int nPlayer = 1000;
		Player[] players = new Player[nPlayer];
		Thread[] threads = new Thread[nPlayer];

		WowObjects.get(0);
		for(int i = 0; i < nPlayer; i++){
			players[i] = createPlayer(e);
		}
		
		for(int k = 0; k < 8*7*24;k++){
			for(int i = 0; i < nPlayer; i++){
				threads[i] = new Thread(players[i]);
			}
			for(int i = 0; i < nPlayer; i++){
				threads[i].start();
			}
			for(int i = 0; i < nPlayer; i++){
				threads[i].join();
			}
			for(int i = 0; i < nPlayer; i++){
				players[i].oneHourAhead();
			}
			e.oneHourAhead();
			Sale best = e.ah().getBestSale(Potion.getInstance());
			if(best != null)
				System.out.println(best.getPrice());
			System.out.println(e.getdayOfWeek()+":"+e.getHour());
			for(int i = 0; i < nPlayer; i++){
				//System.out.println(players[i].getBag().howMany(Plante.getInstance()));
			}
			
		}
		
		
	}
	
	public static Player createPlayer(Environment e){
		double r;
		//RaidSchedule
		double p = 2.0/7.0;
		boolean[] raidScheduleFunction = new boolean[7*24];
		for (int i = 0; i < raidScheduleFunction.length; i++) {
			if(i%24 == 21){
				r = ThreadLocalRandom.current().nextDouble();
				if(r<=p){
					raidScheduleFunction[i]=true;
				}
				else{
					raidScheduleFunction[i]=false;
				}
			}
			else{
				raidScheduleFunction[i]=false;
			}
		}
		RaidSchedule rs = new RaidSchedule(raidScheduleFunction);
			
		//Job & utility
		ArrayList<Job> jobs = new ArrayList<Job>();
		Utility u;
		r = ThreadLocalRandom.current().nextDouble();
		if(r<=0.3439)
			jobs.add(Herbalism.getInstance());
		r = ThreadLocalRandom.current().nextDouble();
		if(r<=0.3439)
			jobs.add(Alchemy.getInstance());
		
		if(jobs.size() == 0){
			double[] parameters = {2,16*9*10000,-1,0,9*10000};
			u = new Utility(true, parameters, e, rs);
		}
		else if (jobs.size() == 2){
			double[] parameters = {1,16*9*10000,-1,1*10000,3*10000};
			u = new Utility(false, parameters, e, rs);
		}
		else if(jobs.contains(Herbalism.getInstance())){
			double[] parameters = {1,16*9*10000,-1,0.8*10000,8*10000};
			u = new Utility(false, parameters, e, rs);
		}
		else{
			double[] parameters = {1,16*9*10000,-1,2*10000,6*10000};
			u = new Utility(false, parameters, e, rs);
		}
		for(int i = jobs.size(); i <4; i++){
			jobs.add(UselessJob.getInstance());
		}
		
		//Sleep
		double[] sleepFunction = new double[24*7];
		for (int i = 0; i < days.length; i++) {
			r = ThreadLocalRandom.current().nextDouble();
			if(r<=days[i]){
				for (int j = 0; j < hours.length; j++) {
					sleepFunction[i*24+j] = hours[j];
				}
			}
			else{
				for (int j = 0; j < zeros.length; j++) {
					sleepFunction[i*24+j] = zeros[j];
				}
			}
		}
		Sleep s = new Sleep(sleepFunction, rs);
		
		//AHSchedule
		double[] ahFunction = new double[24*7];
		for (int i = 0; i < ahDays.length; i++) {
			r = ThreadLocalRandom.current().nextDouble();
			if(r<=ahDays[i]){
				for (int j = 0; j < ahHours.length; j++) {
					sleepFunction[i*24+j] = hours[j];
				}
			}
			else{
				for (int j = 0; j < zeros.length; j++) {
					sleepFunction[i*24+j] = zeros[j];
				}
			}
		}
		AHSchedule ahs = new AHSchedule(ahFunction, rs);
		
		//alphas
		double alpha = 0.5;
		
		return new Player(u,rs,s,e,ahs,jobs,alpha);

	}

}
