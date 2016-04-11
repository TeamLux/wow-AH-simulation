package wow.object;

public final class Plante extends WowObject {

	private static Plante singleton = new Plante();
	private Plante() {}
	
	public static Plante getInstance(){
		return singleton;
	}
	
	@Override
	public int id() {
		return 0;
	}

	@Override
	public int sellPrice() {
		return 2462;
	}

}
