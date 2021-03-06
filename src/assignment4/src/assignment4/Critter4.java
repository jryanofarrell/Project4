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
 * Critter 4 "evolves" twice in one reproduction and only runs when it gets in a fight, it always runs from everything but algae
 **/
public class Critter4 extends Critter {
	
	@Override
	public String toString() { return "4"; }
	
	private static final int GENE_TOTAL = 24;
	private int[] genes = new int[8];
	private int dir;

	public Critter4() {
		for (int k = 0; k < 8; k += 1) {
			genes[k] = GENE_TOTAL/8;
		}
		dir = Critter.getRandomInt(8);
	}
	
	public boolean fight(String opponent) { if(opponent.equals("@"))return true; 
	run(dir);
	return false;
			}

	@Override
	public void doTimeStep() {
		/* take one step forward */
		if (getEnergy() > 100) {
			Critter4 child = new Critter4();
			int num_variation = 0; 
			for (int k = 0; k < 8; k += 1) {
				child.genes[k] = this.genes[k];
				if (this.genes[k] >0){
					num_variation ++;
				}
			}
			int g = Critter.getRandomInt(8);
			int h = Critter.getRandomInt(8);
			while (child.genes[g] == 0 && child.genes[g] == 0 && child.genes[h] == 0&& !(g==h)) {
				g = Critter.getRandomInt(8);
				h = Critter.getRandomInt(8);
				if (num_variation <2 && !(child.genes[g] ==0)){
					break;
				}
			}
			
			child.genes[g] -= 1;
			g = Critter.getRandomInt(8);
			child.genes[g] += 1;
			if(num_variation>1){
				child.genes[h]--;
				//g = Critter.getRandomInt(8);
				child.genes[g] += 1;
			}
			reproduce(child, Critter.getRandomInt(8));
		}
		
		/* pick a new direction based on our genes */
		int roll = Critter.getRandomInt(GENE_TOTAL);
		int turn = 0;
		while (genes[turn] <= roll) {
			roll = roll - genes[turn];
			turn = turn + 1;
		}
		assert(turn < 8);
		
		dir = (dir + turn) % 8;
	}

	public static void runStats(java.util.List<Critter> my4s) {
		int total_straight = 0;
		int total_left = 0;
		int total_right = 0;
		int total_back = 0;
		for (Object obj : my4s) {
			Critter4 c = (Critter4) obj;
			total_straight += c.genes[0];
			total_right += c.genes[1] + c.genes[2] + c.genes[3];
			total_back += c.genes[4];
			total_left += c.genes[5] + c.genes[6] + c.genes[7];
		}
		System.out.print("" + my4s.size() + " total Critter4s    ");
		System.out.print("" + total_straight / (GENE_TOTAL * 0.01 * my4s.size()) + "% straight   ");
		System.out.print("" + total_back / (GENE_TOTAL * 0.01 * my4s.size()) + "% back   ");
		System.out.print("" + total_right / (GENE_TOTAL * 0.01 * my4s.size()) + "% right   ");
		System.out.print("" + total_left / (GENE_TOTAL * 0.01 * my4s.size()) + "% left   ");
		System.out.println();
	}
}
