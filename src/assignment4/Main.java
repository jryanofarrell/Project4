/* CRITTERS Main.java
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
package assignment4; // cannot be in default package
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Scanner;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name, 
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     */
    public static void main(String[] args) { 
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));			
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }

        /* Do not alter the code above for your submission. */
//        /* Write your code below. */
//        try {
//			Critter.makeCritter("Craig");
//		} catch (InvalidCritterException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
        //myPackage = Critter.class.getPackage().toString().split(" ")[1];
        
        boolean invalid;
        boolean flag = true;
        Critter.clearWorld();
        while(flag){
        	invalid =false;
        	//System.out.print("Command: ");
	        String input = kb.nextLine();
	        String[] splitted = input.split("\\s+");
	        String command,second_arg="",third_arg="";
	        command = splitted[0];
	        if(splitted.length >=2){
	        	second_arg = splitted[1];
	        }
	        if(splitted.length >=3){
	        	third_arg = splitted[2];
	        }
	        if(command.equals("quit")){
	        	flag = false;
	        }
	        else if(command.equals("show")){
	        	Critter.displayWorld();
	        }
	        else if(command.equals("step")){
	        	int steps = 1;
	        	if(!second_arg.equals("")){
	        		try{
	        			steps = Integer.parseInt(second_arg);
	        		}catch(NumberFormatException e){
	        			System.out.println("Invalid command : " + input);
	        			continue;
	        		}
	        	}
	        	for(int i = 0; i<steps; i++){
	        		Critter.worldTimeStep();
	        	}
	        }
	        else if(command.equals("seed")){
	        	int seed;
        		try{
        			seed = Integer.parseInt(second_arg);
        			Critter.setSeed(seed);
        		}catch(NumberFormatException e){
        			System.out.println("Invalid command : " + input);
        			continue;
        			//System.out.println();
        		}
        	
	        	
	        }
	        else if(command.equals("make")){
	        	int num_critters = 1;
	        	String critter = second_arg;
	        	if(!third_arg.equals("")){
	        		try{
	        			num_critters = Integer.parseInt(third_arg);
	        		}catch(NumberFormatException e){
	        			System.out.println("Invalid command : " + input);
	        			continue;
	        		}
	        	}
	        	for (int i = 0; i<num_critters; i++){
	        		if((i+1)%1000 == 0){
	        			i++;
	        			i--;
	        		}
	        		try {
						Critter.makeCritter(critter);
					} catch (InvalidCritterException e) {
						//e.printStackTrace();
						System.out.println("Invalid command : " + input);
						continue;
					}
	        	}
	        }
	       // System.out.println("test");
	        else if(command.equals("stats")){
	        	String class_name = second_arg;
	        	List<Critter> critter_list = null; 
	        	try {
					critter_list = Critter.getInstances(myPackage + "."+ class_name);
				} catch (InvalidCritterException e) {
					//e.printStackTrace();
					System.out.println("Invalid command : " + input);
					continue;
				}
	        	//Class<?>[] types = {critter_list.class};
	        	Class<?> newCritterClass = null;
	        	Method method = null;
	        	
	    		try{
	    			newCritterClass = Class.forName(myPackage +"."+ class_name);
	    			
	    		}catch(ClassNotFoundException e){
	    			//throw new InvalidCritterException(class_name);
	    		} 
	    		Class<?>[] types = {List.class};
	    		try{
	    			method = newCritterClass.getMethod("runStats",types);
	    		}catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		try {
					method.invoke(newCritterClass, critter_list);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		
	    		
	    		
	        	//Critter.runStats(critter_list);
	        }else
	        	System.out.println("Invalid command : " + input);
	        //System.out.println();
        }
        /* Write your code above */
        //System.out.flush();

    }
}
