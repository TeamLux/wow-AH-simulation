package wow.object;

public class Potion extends WowObject {

	private static Potion singleton = new Potion();
	private Potion() {}
	
	public static Potion getInstance(){
		return singleton;
	}
	
	@Override
	public int id() {
		return 1;
	}

	@Override
	public int sellPrice() {
		return 2500;
	}

}
