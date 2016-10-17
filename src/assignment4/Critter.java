/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */
package assignment4;

import java.util.ArrayList;
import java.util.List;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	private static String myPackage;
	private	static List<Critter> population = new ArrayList<Critter>();
	private static List<Critter> babies = new ArrayList<Critter>();

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	private void change_coord(int direction,int distance){
		switch(direction){
		case 0 :
			x_coord += distance;
			break;
		case 1:
			x_coord += distance;
			y_coord -= distance;
			break;
		case 2:
			y_coord -= distance;
			break;
		case 3:
			x_coord -= distance;
			y_coord -= distance;
			break;
		case 4 :
			x_coord -= distance;
			break;
		case 5 :
			x_coord -= distance;
			y_coord += distance;
			break;
		case 6:
			y_coord += distance;
			break;
		case 7 :
			x_coord += distance;
			y_coord += distance;
			break;
		}
//		if (x_coord <1){
//			x_coord = 1;
//		}
//		else if(x_coord > Params.world_width){
//			x_coord = Params.world_width -1;
//		}
//		if (y_coord <1){
//			y_coord = 1;
//		}
//		else if(y_coord > Params.world_height){
//			y_coord = Params.world_height -1;
//		}
		x_coord=x_coord%Params.world_width;
		y_coord=y_coord%Params.world_height;
	}
	protected final void walk(int direction) {

		change_coord(direction,1);
		energy -= Params.walk_energy_cost;
	}
	
	protected final void run(int direction) {
		change_coord(direction,2);
		energy = Params.run_energy_cost; 
	}
	
	protected final void reproduce(Critter offspring, int direction) {
		if(energy < Params.min_reproduce_energy){
			return;
		}
		offspring.energy = energy/2;
		energy = energy/2 + energy%2;
		offspring.x_coord = x_coord;
		offspring.y_coord = y_coord;
		change_coord(direction,1);
		babies.add(offspring);
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		//Object newCritterObject;
		Class<?> newCritterObject;
		try{
			newCritterObject = Class.forName(critter_class_name);
		}catch(ClassNotFoundException e){
			throw new InvalidCritterException(critter_class_name);
		}
		Critter newCritter = null;
		try {
			newCritter = (Critter) newCritterObject.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		newCritter.x_coord = Critter.getRandomInt(Params.world_width);
		newCritter.y_coord = Critter.getRandomInt(Params.world_height);
		newCritter.energy = Params.start_energy;
		population.add(newCritter);
		
	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new ArrayList<Critter>();
		Class<?> newClass;
		try{
			newClass=Class.forName(critter_class_name);
			for (Critter c : population){
				if (c.getClass().equals(newClass))
					result.add(c);
			}
		}catch(ClassNotFoundException e){
			throw new InvalidCritterException(critter_class_name);
		}
			
		return result;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}

		public static List<Critter> getPop() {
			// TODO Auto-generated method stub
			return null;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	private void transferEnergy(Critter B){
		energy += B.getEnergy()/2;
		B.energy=0;
	}

	private boolean samePosition(Critter B){
		return x_coord == B.x_coord && y_coord == B.y_coord;
	}
	private boolean isAlive(){
		return this.getEnergy() > 0;
	}
	public static void clearWorld() {
	}
	
	public static void worldTimeStep() {
		for(Critter c : babies){
			population.add(c);
		}
		for(Critter c : population){
			c.doTimeStep();
		}
		for(int i = 0; i<population.size(); i++){
			Critter A = population.get(i);
			for(int j = i+1; j<population.size(); j++){
				Critter B = population.get(j);
				if(A.samePosition(B)){
					boolean Afight,Bfight;
					Afight = A.fight(B.toString());
					Bfight = B.fight(A.toString());
					if (A.isAlive() && B.isAlive() && A.samePosition(B)){
						int Adice=0,Bdice = 0;
						if(Afight){
							Adice = Critter.getRandomInt(A.getEnergy());
						}
						if(Bfight){
							Bdice = Critter.getRandomInt(B.getEnergy());
						}
						if(Bdice > Adice){
							B.transferEnergy(A);
						}
						else{
							A.transferEnergy(B);
						}
					}
				}
			}
			
		}
		for(int i = 0; i<population.size(); i++){
			Critter c = population.get(i);
			c.energy -= Params.rest_energy_cost;
			if(!c.isAlive()){
				Critter x = population.get(population.size() - 1);
				population.set(i, x);
				population.remove(population.size()-1); 
			}
		}
	}
	public static void displayWorld() {
		String[][] world = new String[Params.world_width+2][Params.world_height+2];
		for(int i = 1; i<Params.world_width+1; i++){
			world[i][0] = "-";
			world[i][Params.world_height +1] = "-";
		}
		for(int i = 1; i<Params.world_height+1; i++){
			world[0][i] = "|";
			world[Params.world_width +1][i] = "|";
		}
		world[0][0] ="+";
		world[0][Params.world_height +1] ="+";
		world[Params.world_width +1][0] ="+";
		world[Params.world_width +1][Params.world_height +1] ="+";
		for(Critter c:population){
			world[c.x_coord][c.y_coord] = c.toString();
		}
		for(int i = 0;i<Params.world_width+2;i++){
			for(int j = 0;j<Params.world_height+2;j++){
				System.out.println(world[i][j]);
			}
		}

	}
}
