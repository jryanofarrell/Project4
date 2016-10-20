package assignment4;

public class MyCritter1 extends Critter {
	private boolean diag=true;
	@Override
	public void doTimeStep() {
		if (diag)
			walk(Critter.getRandomInt(3)*2+1); 	//the critter walks on diagonals
		else
			walk(Critter.getRandomInt(3)*2);	//the critter can't walk diagonally
		diag= !diag;
		if (Critter.getRandomInt(5)==0)
			reproduce(new MyCritter1(),Critter.getRandomInt(8));
	}

	@Override
	public boolean fight(String opponent) {
		if (getEnergy() > 60)
			return true;
		else if (getEnergy()>40)
			run(Critter.getRandomInt(8));
		return false;
	}
	
	public String toString() {
		return "1";
	}
}
