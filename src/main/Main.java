package main;

import game.Game;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		Game game = new Game(0.5,1,0.05,100);
		game.run(4*7*24);
	}

}
