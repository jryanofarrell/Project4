/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Clement PARDON
 * cp34735
 * 16460
 * John OFarrell
 * jro769
 * 16455
 * Slip days used: <0>
 * Fall 2016
 */
package assignment4;

/**
 * this critter walks diagonally then not diagonally
 * @author Clément
 *
 */

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
		System.out.print("" + my3s.size() + " total Critter1s");
		System.out.println();
	}
}
