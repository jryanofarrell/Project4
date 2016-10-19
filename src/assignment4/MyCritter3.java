package assignment4;

/*
 * Critter that only goes in one direction
 */
public class MyCritter3 extends Critter {
	
	@Override
	public String toString() { return "3"; }
	
	private static final int GENE_TOTAL = 24;
	private int[] genes = new int[8];
	private int dir;
	
	public MyCritter3() {
		dir = Critter.getRandomInt(8);
		genes[dir] = GENE_TOTAL;
	}
	
	public boolean fight(String opponent) { return true; }

	@Override
	public void doTimeStep() {
		/* take one step forward */
		walk(dir);
		
		if (getEnergy() > 150) {
			MyCritter3 child = new MyCritter3();
			int g;
			for (int k = 0; k < 8; k += 1) {
				child.genes[k] = 0;
			}
			g = Critter.getRandomInt(8);
			child.genes[g] += GENE_TOTAL;
			child.dir = g;
			reproduce(child, Critter.getRandomInt(8));
		}
		
	}

	public static void runStats(java.util.List<Critter> craigs) {
		int total_straight = 0;
		int total_left = 0;
		int total_right = 0;
		int total_back = 0;
		for (Object obj : craigs) {
			MyCritter3 c = (MyCritter3) obj;
			total_straight += c.genes[0];
			total_right += c.genes[1] + c.genes[2] + c.genes[3];
			total_back += c.genes[4];
			total_left += c.genes[5] + c.genes[6] + c.genes[7];
		}
		System.out.print("" + craigs.size() + " total Craigs    ");
		System.out.print("" + total_straight / (GENE_TOTAL * 0.01 * craigs.size()) + "% straight   ");
		System.out.print("" + total_back / (GENE_TOTAL * 0.01 * craigs.size()) + "% back   ");
		System.out.print("" + total_right / (GENE_TOTAL * 0.01 * craigs.size()) + "% right   ");
		System.out.print("" + total_left / (GENE_TOTAL * 0.01 * craigs.size()) + "% left   ");
		System.out.println();
	}
}
