package wow.object;

import java.util.Hashtable;
import java.util.Map;

public class Bag {
	Map<Integer, Integer> objects;
	
	public Bag(){
		this.objects = new Hashtable<Integer, Integer>();
	}
	
	public Bag(Bag bag){
		this.objects = new Hashtable<Integer, Integer>(bag.objects);
	}
	
	public void add(WowObject o, int q){
		objects.put(o.id(), q);
	}
	
	public boolean has(WowObject o, int q){
		return objects.get(o.id()) >= q;
	}
	
	public void remove(WowObject o, int q){
		objects.put(o.id(), objects.get(o.id()) - q);
	}
	
	public int howMany(WowObject o){
		return objects.get(o.id());
	}
	
	public int howMany(int i){
		return objects.get(i);
	}
}
