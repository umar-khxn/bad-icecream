/*Umar Khan
 * ICS4U
 * January 24th 2021
 * Map Class
 * This class is meant to read the specific Map file and retrieve the necessary information from it such as the coordinates of the characters
 */

package BadIceCream;
import java.io.*;

public class Map{
	private int mx;//The x-coordinate for Main Ice Cream
	private int my;//The y-coordinate for Main Ice Cream
	private Enemy[] enemies;//Enemy array for the enemies in the game
	private int level;//current level of the game
	String line="";  //Put the integers read form the file into a line 
	BufferedReader br;//Buffered reader to read the file
	private int [][] mymap; //Store the original map read from the file
	int c=0;//used to initialize the map
	
	Map (int v) {
		mymap = new int [12][15];//the map is 15 x 12 
		level=v;//level is v
		System.out.println(level);//output the current level
		
		if(level==1) {
			Main.amount=600;//if it is level 1, set the total amount that needs to be collected to be 600
			enemies = new Enemy[3];//level 1 will have 3 enemies
		}
		else if(level==2) {
			Main.amount=700;;//if it is level 2, set the total amount that needs to be collected to be 700
			enemies = new Enemy[4];//level 2 will have 4 enemies
		}
		else if(level==3) {
			Main.amount=900;//if it is level 3, set the total amount that needs to be collected to be 900
			enemies = new Enemy[5];//level 3 will have 5 enemies
		}
		int enemyNumber = 0;
		try{	
			br = new BufferedReader(new FileReader(new File("maps/"+level+".map")));//One way of reading the file saved in the folder of the same class
			String contentLine = br.readLine(); //String variable to store every int element in the file
			System.out.println("---------------------");
			System.out.println(contentLine);//output the map line
			while (contentLine != null) {
				line=line+contentLine;//Add the int elements in the file to the line one at a time
				contentLine = br.readLine();
				System.out.println(contentLine);
			}
			System.out.println("---------------------");
			br.close();
		}

		//Being used to check errors
		catch (IOException ioe) {
			ioe.printStackTrace();
		} 
		//Get byte value for each int element in the line
		byte [] by = line.getBytes();
		//Get the length of the line
		int length=line.length();
		System.out.println(length);

		//Create a parallel array to store the int value for each int element converted from its byte value
		int [] x = new int [length];
		for (int i=0; i<line.length();i++) {
			x[i]=by[i]-48;
		}
		//Create a 2D int array to store the converted int value form the parallel array 
		for (int i=0; i<12; i++) {
			for (int j=0; j<15; j++) {
				mymap [i][j]=x[c];
				System.out.print(mymap[i][j]);
				//Find the original x,y-coordinates for Main Sundae Ice Cream
				if (mymap[i][j]==4) {//if 4 in the map is found
					mx=j;
					my=i;
				}
				//Find the original x,y-coordinates for Evil Ice Cream and create a new Enemy
				if(mymap[i][j]==3) {//if a 3 in the map is found
					enemies[enemyNumber] = new Enemy(j, i);//create a new enemy
					enemyNumber++;//increase the number of enemies based on how many 3's are found
				}
				c++;
			}
		}
	}
	
	/*getEnemies
	 * Purpose: return the enemies to the MainPanel to be used for the game
	 * Pre: N/A
	 * Post: enemies
	 */
	public Enemy[] getEnemies(){
		return enemies;//return the enemies
	}
	
	/*getMap
	 * Purpose: Return the 2D int array map for the specific level
	 * Pre: N/A
	 * Post: void
	 */
	public int[][]getMap(){
		return mymap;//return the map
	}
	
	/*getIceX
	 * Purpose: Return the x-coordinate of the Main Ice Cream
	 * Pre: N/A
	 * Post: void
	 */
	public int getIceX(){
		return mx;//return x-coordinate of the player
	}
	
	/*getIceY
	 * Purpose: Return the x-coordinate of the Main Ice Cream
	 * Pre: N/A
	 * Post: void
	 */
	public int getIceY(){
		return my;//return the y coordinate of the player
	}
}
