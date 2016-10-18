package assignment4;

public class MyCritter1 extends Critter.TestCritter {
	private boolean diag=true;
	@Override
	public void doTimeStep() {
		resetWalk();
		if (diag)
			walk(Critter.getRandomInt(3)*2+1); 	//the critter walks on diagonals
		else
			walk(Critter.getRandomInt(3)*2);	//the critter can't walk diagonally
		diag= !diag;
		if (Critter.getRandomInt(5)==5)
			reproduce(new MyCritter1(),Critter.getRandomInt(7));
	}

	@Override
	public boolean fight(String opponent) {
		if (getEnergy() > 60)
			return true;
		return false;
	}
	
	public String toString() {
		return "1";
	}
}
