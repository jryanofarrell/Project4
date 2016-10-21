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

import java.util.List;

/*
 *This critter is a magician but not really powerful, he increases his magic by eating algae, he has to use magic to do his actions
 *If wins against another magician, he triples his magic
 */
public class Critter2 extends Critter{
	private int magic=5;
	
	//
	@Override
	public void doTimeStep () {
		if (magic>=10){
			reproduce(new Critter2(), magic%8);
			magic-=10;
		}if (magic>5){
			walk(magic%8);
			magic--;
		}
	}
	
	
	
	public boolean fight(String opp) {
		if (opp.toString().equals("2")){
			magic= (magic>3)?(magic*3):(magic+3);
			return true;
			}else if (opp.toString().equals("@")){
				magic +=2;
				return true;
			}else{
				if(magic>0){
					magic--;
					return true;
				}else
					run(magic%8);
					return false;
			}
	}
	
	public String toString() {
		return "2";
	}
	public static void runStats(java.util.List<Critter> my3s) {
		System.out.print("" + my3s.size() + " total Critter3s    ");
		System.out.println();
	}
}
