package wow.object;

public class Potion extends WowObject {

	private static Potion singleton = new Potion();
	private Potion() {}
	
	public static Potion getPotion(){
		return singleton;
	}
	
	@Override
	public int id() {
		return 2;
	}

	@Override
	public int sellPrice() {
		return 2500;
	}

}
