package assignment4;

import java.util.List;

/*
 *This critter is a magician but not really powerful, he increases his magic by eating algae, he has to use magic to fight. 
 *it doesn't reproduce, instead if he has enough magic he can use it create one of his kind. If wins against another magician, he triples his magic
 */
public class MyCritter2 extends MyCritter1 {
	
	private int magic=5;
	
	//
	@Override
	public void doTimeStep () {
		if (magic>=10){
			try {
				Critter.makeCritter("MyCritter2");
			} catch (InvalidCritterException e) {
				e.printStackTrace();
			}
			magic-=10;
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
					return false;
			}
	}
	
	public String toString() {
		return "2";
	}

}
