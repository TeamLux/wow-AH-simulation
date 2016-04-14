package wow.object;

import java.util.Hashtable;
import java.util.Map;

public class Bag {
	Map<WowObject, Integer> objects;
	public static final int ADD = 0;
	public static final int REMOVE = 1;
	public static final int NOTHING = -1;
	private int lastAction;
	private WowObject lastAdd = null;
	private WowObject lastRemove = null;
	
	public Bag(){
		this.objects = new Hashtable<WowObject, Integer>();
		for(int i = 0; i<WowObjects.size();i++){
			this.objects.put(WowObjects.get(i), 0);
		}
	}
	
	public Bag(Bag bag){
		this.objects = new Hashtable<WowObject, Integer>(bag.objects);
	}
	
	public void add(WowObject o, int q){
		objects.put(o, objects.get(o)+q);
		lastAction = ADD;
		lastAdd = o;
	}
	
	public boolean has(WowObject o, int q){
		return objects.get(o) >= q;
	}
	
	public void remove(WowObject o, int q){
		objects.put(o, objects.get(o) - q);
		lastAction = REMOVE;
		lastRemove = o;
	}
	
	public int howMany(WowObject o){
		return objects.get(o);
	}
	
	public int howMany(int i){
		return objects.get(WowObjects.get(i));
	}
	
	public int lastAction(){
		return lastAction;
	}
	
	public WowObject lastAdd(){
		return lastAdd;
	}
	
	public WowObject lastRemove(){
		return lastRemove;
	}
}
