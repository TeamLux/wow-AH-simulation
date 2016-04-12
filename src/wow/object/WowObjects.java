package wow.object;

public class WowObjects {
	private static WowObjects singleton = new WowObjects();
	private WowObject[] objects = new WowObject[2];
	private WowObjects() {
		objects[Plante.getInstance().id()] = Plante.getInstance(); 
		objects[Potion.getInstance().id()] = Potion.getInstance(); 
	}
			
	public static WowObjects getInstance(){
		return singleton;
	}
	
	public WowObject get(int i){
		return objects[i];
	}
	
	public int size(){
		return objects.length;
	}
}
