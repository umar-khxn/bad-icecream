/*Umar Khan
 * ICS4U
 * January 24th 2021
 * Main Class
 * This class is meant to contain the global variables that will be used throughout the program and run my game
 */

package BadIceCream;

public class Main {
	public static int level=1;//set the starting level to 1
	public static int amount;//amount of the total score for the level
	public static boolean fail=false;//if user has been caught by the evil ice cream or not (failed the game or not)
	
	public static void main(String[] args) {
        new MainFrame();//start the game
        
    }

}
