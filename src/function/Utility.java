package function;

import wow.envrionment.Environment;
import wow.object.Bag;
import wow.object.Plante;
import wow.object.WowObject;
import wow.player.Player;

public class Utility {
	private boolean logGold;
	private double[] parameters;
	private RaidSchedule rs;
	private Environment e;
	public Utility(boolean logGold, double[] parameters, Environment e, RaidSchedule rs){
		this.logGold = logGold;
		this.parameters = parameters;
		this.rs = rs;
		this.e = e;
	}
	
	public Utility(boolean logGold, double[] parameters, Environment e){
		this.logGold = logGold;
		this.parameters = parameters;
		this.e = e;
		this.rs = null;
	}
	
	public double f(long gold, int stuff, int tired, Bag bag){
		double res = 0;
		if(logGold)
			res+=Math.log(gold)/Math.log(this.parameters[0]);
		else
			res+=this.parameters[0]*gold;
		res+=this.parameters[1]*stuff;
		
		res+=tired*this.parameters[2];
		for (int i = 3; i < parameters.length; i++) {
			res+=this.parameters[i]*bag.howMany(i-3);
		}

		if(rs!=null){
			int i = Plante.getInstance().id();
			res-=this.parameters[i]*bag.howMany(i);
			res+=(this.parameters[i]/(rs.timeTonextRaid(e.getdayOfWeek(), e.getHour())+1))*Math.min(bag.howMany(i)+stuff, Player.MAX_STUFF);
		}
		
		return res;
	}
	
	public int minPriceForSale(long gold, WowObject o){
		int res;
		if(logGold){
			 res = (int) (gold*(Math.pow(this.parameters[0],this.parameters[o.id()+3])-1))+1;
		}
		else{
			res = (int) (this.parameters[o.id()+3]/this.parameters[0])+1;
		}
		return (int)(res/0.95)+1;
		
	}
}
