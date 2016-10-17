package assignment4;

public class Squid extends Critter {

	@Override
	public void doTimeStep() {
		// floats in any direction each time
		walk(getRandomInt(7));
		reproduce(new Squid(),getRandomInt(7));
	}

	@Override
	public boolean fight(String oponent) {
		
		return true;
	}

}
