package assignment4;

public class Critter1 extends Critter {
	private boolean diag=true;
	@Override
	public void doTimeStep() {
		if (diag)
			walk(Critter.getRandomInt(3)*2+1); 	//the critter walks on diagonals
		else
			walk(Critter.getRandomInt(3)*2);	//the critter can't walk diagonally
		diag= !diag;
		if (Critter.getRandomInt(5)==0)
			reproduce(new Critter1(),Critter.getRandomInt(8));
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
	public static void runStats(java.util.List<Critter> my3s) {
		System.out.print("" + my3s.size() + " total Critter1s    ");
		System.out.println();
	}
}
