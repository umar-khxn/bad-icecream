/*Umar Khan
 * ICS4U
 * January 24th 2021
 * MainPanel Class
 * This class is meant to contain all of the logic used to run my game the way it is meant to be. The entire game output is based on this class
 */

package BadIceCream;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainPanel extends JPanel implements Runnable, KeyListener{
	private static final long serialVersionUID = -8691637399230146889L;//used to remove error in Java file
	int[][]map;//2D array of the map of the game (numbers)
	int biX,biY;//X and Y position of the main ice cream (Bad Ice Cream)
	Image[]myImages;//array of the images used in my game to produce the map
	Map Levelmap;//map of the game from Map class
	int len=50;//size of each tile in the game
	int score=0;//score of the player (number of cherries collected)
	boolean canMove;//if user can move or not
	boolean canIce;//if user can break or make ice or not
	Thread runner;//Thread for the movement of evil ice creams
	Enemy[] enemies;//array of enemies from the Enemy class

	MainPanel(){//MainPanel constructor
		setBounds(40,30,750,600);//location where to start the game screen
		setBackground(Color.white);//set the background to be white (snow)
		addKeyListener(this);//add a key listener for the movements
		myImages=new Image[7];//there will be 7 images that need to be shown on the map
		for(int i=0; i<myImages.length; i++) {
			myImages[i] = new ImageIcon ("images/"+i+".png").getImage();
			//Store the images for different map components in an Image array using ImageIcon
		}
		setVisible(true);
		getMap(Main.level);//get the map of the current level
		if(Main.level==1) {//if it is level 1 then start the movement for the evil characters
			runner = new Thread(this);
			runner.start();
		}
	}
	
	/*getMap
	 * Purpose: Obtain the details of the map and the positions of the characters
	 * Pre: one int variable, i
	 * Post: void
	 */
	public void getMap(int i) {
		Levelmap=new Map(i);//Levelmap is a new Map from the Map class
		map=Levelmap.getMap();//the map that will be used is the Levelmap
		biX=Levelmap.getIceX();//determine the x position of the main character
		biY=Levelmap.getIceY();//determine the y psotion of the main character
		enemies = Levelmap.getEnemies();//get the positions of the enemies
	}
	
	/*paint
	 * Purpose: draw the map and screen of the game, as well as update the score and check if the user has won
	 * Pre: Graphics g
	 * Post: void
	 */
	public void paint(Graphics g) {
		for(int i=0; i<12; i++) {//lopp for the 12 columns of numbers for the map
			for(int j=0; j<15; j++){//loop for the 15 rows of numbers for the map
				g.drawImage(myImages[map[i][j]],j*len,i*len,this);//draw the map of the game
			}
		}
		drawScore(g);//output the curren score of the player
		win();//check if the user has won or not
	}
	
	/*keyPressed
	 * Purpose: key inputs of user that will affect the game such as movements with arrow keys, and WASD keys for ice. Also output the necessary information and options if the user has won
	 * Pre: KeyEvent e
	 * Post: void
	 */
	public void keyPressed(KeyEvent e) {
		if(canMove) {//if user movement is valid (in bounds)
			if(e.getKeyCode()==KeyEvent.VK_UP){//if up arrow key is pressed
				moveUp();//move the user up
			}
			if(e.getKeyCode()==KeyEvent.VK_DOWN){//if down arrow key is pressed
				moveDown();//move the user down
			}
			if(e.getKeyCode()==KeyEvent.VK_LEFT){//if left arrow key is pressed
				moveLeft();//move the user to the left
			}
			if(e.getKeyCode()==KeyEvent.VK_RIGHT){//if right arrow key is pressed
				moveRight();//move the user to the right
			}
		}
		if(canIce) {//if user ice is valid (in bounds)
			if(e.getKeyCode()==KeyEvent.VK_W) {//if W key is pressed
				iceUp();//create an ice block above the user
			}
			if(e.getKeyCode()==KeyEvent.VK_S) {//if S key is pressed
				iceDown();//create an ice block below the user
			}
			if(e.getKeyCode()==KeyEvent.VK_A) {//if A key is pressed
				iceLeft();//create an ice block to the left of the user
			}
			if(e.getKeyCode()==KeyEvent.VK_D) {//if D key is pressed
				iceRight();//create an ice block to the right of the user
			}
		}
		if(win()) {//if the user has won the game and conditions are met
			int choice = 0;//exit or continue choice
			if(Main.level==3) {//if the current game level is 3
				JOptionPane.showMessageDialog(null, "Congratulations on beating Bad Ice Cream!\nExit the Game?","Pass",JOptionPane.PLAIN_MESSAGE);
				System.exit(0);//if the user has reached the last level and won
			}
			else if(Main.level!=3) {//when the game level is not the last one (3)
				choice=JOptionPane.showConfirmDialog(null,"Congratulations on passing level "+Main.level+".\nMove On to Next Level?","Pass",JOptionPane.YES_NO_OPTION);
				if(choice==1) {//Close the game window if the player chooses to exit in the dialog box
					System.exit(0); 
				}
				else if(choice==0) {//continue to the next level if they would like to continue
					Main.level++;//go to the next level
					System.out.println(Main.level);
					getMap(Main.level);//get the map of the next level
				}
				score=0;//reset the score to 0 for the next level
			}
		}
	}
	
	/*moveUp
	 * Purpose: move the main character up if it is possible and make the necessary changes to the map
	 * Pre: none
	 * Post: void
	 */
	public void moveUp() {
		if(biY == 0) {//if the user cannot move up
			return;
		}
		if(map[biY-1][biX]==0) {//if the square above the user is a snow block, allow them to move up
			map[biY][biX]=0;//change their previous position to a snow block (they are off the square)
			map[biY-1][biX]=4;//make their next position (which is now the current one) to a sundae block (player)
			biY=biY-1;//update the y-coordinate
		}
		else if(map[biY-1][biX]==1) {//if the square above the user is an ice block, do not allow them to move up
			map[biY][biX]=4;//keep them in the same place
		}
		else if(map[biY-1][biX]==2) {//if the square above the user is a cherry, allow them to move up
			map[biY][biX]=0;//change their previous position to a snow block (they are off the square)
			map[biY-1][biX]=4;//make their next position (which is now the current one) to a sundae block (player)
			score=score+100;//add 100 to their current score
			biY=biY-1;//update the y-coordinate
		}
		canMove = false;//used to only move one block at a time
	}
	
	/*moveDown
	 * Purpose: move the main character down if it is possible and make the necessary changes to the map
	 * Pre: none
	 * Post: void
	 */
	public void moveDown() {
		if(biY == map.length - 1) {//if the user cannot move down
			return;
		}
		if(map[biY+1][biX]==0) {//if the square below the user is a snow block, allow them to move down
			map[biY][biX]=0;//change their previous position to a snow block (they are off the square)
			map[biY+1][biX]=4;//make their next position (which is now the current one) to a sundae block (player)
			biY=biY+1;//update the y-coordinate
		}
		else if(map[biY+1][biX]==1) {//if the square below the user is an ice block, do not allow them to move down
			map[biY][biX]=4;//keep them in the same place
		}
		else if(map[biY+1][biX]==2) {//if the square below the user is a cherry, allow them to move down
			map[biY][biX]=0;//change their previous position to a snow block (they are off the square)
			map[biY+1][biX]=4;//make their next position (which is now the current one) to a sundae block (player)
			score=score+100;//add 100 to their current score
			biY=biY+1;//update the y-coordinate
		}
		canMove = false;//used to only move one block at a time
	}
	
	/*moveUp
	 * Purpose: move the main character to the left if it is possible and make the necessary changes to the map
	 * Pre: none
	 * Post: void
	 */
	public void moveLeft() {
		if(biX == 0) {//if the user cannot move to the left
			return;
		}
		if(map[biY][biX-1]==0) {//if the square to the left of the user is a snow block, allow them to move left
			map[biY][biX]=0;//change their previous position to a snow block (they are off the square)
			map[biY][biX-1]=4;//make their next position (which is now the current one) to a sundae block (player)
			biX=biX-1;//update the x-coordinate
		}
		else if(map[biY][biX-1]==1) {//if the square to the left of the user is an ice block, do not allow them to move left
			map[biY][biX]=4;//keep them in the same place
		}
		else if(map[biY][biX-1]==2) {//if the square to the left the user is a cherry, allow them to move the left
			map[biY][biX]=0;//change their previous position to a snow block (they are off the square)
			map[biY][biX-1]=4;//make their next position (which is now the current one) to a sundae block (player)
			score=score+100;//add 100 to the player's current score
			biX=biX-1;//update the x-coordinate
		}
		canMove = false;//used to only move one block at a time
	}
	
	/*moveUp
	 * Purpose: move the main character to the right if it is possible and make the necessary changes to the map
	 * Pre: N/A
	 * Post: void
	 */
	public void moveRight() {
		if(biX == map[0].length - 1) {//if the user cannot move to the left
			return;
		}
		if(map[biY][biX+1]==0) {//if the square to the right of the user is a snow block, allow them to move right
			map[biY][biX]=0;//change their previous position to a snow block (they are off the square)
			map[biY][biX+1]=4;//make their next position (which is now the current one) to a sundae block (player)
			biX=biX+1;//update the x-coordinate
		}
		else if(map[biY][biX+1]==1) {//if the square to the right of the user is an ice block, do not allow them to move right
			map[biY][biX]=4;//keep them in the same place
		}
		else if(map[biY][biX+1]==2) {//if the square to the right the user is a cherry, allow them to move the right
			map[biY][biX]=0;//change their previous position to a snow block (they are off the square)
			map[biY][biX+1]=4;//make their next position (which is now the current one) to a sundae block (player)
			score=score+100;//add 100 to the score of the player
			biX=biX+1;//update the x-coordinate
		}
		canMove = false;//used to only move one block at a time
	}
	
	/*iceUp
	 * Purpose: to create of break an ice block above the player if it is possible
	 * Pre: N/A
	 * Post: void
	 */
	public void iceUp(){
		if(biY == 0) {//if the player cannot create an ice block above them
			return;
		}
		if(map[biY-1][biX]==1) {//if the block above the player is currently an ice block
			map[biY-1][biX]=0;//make it into a snow block now
		}
		else if(map[biY-1][biX]==0) {//if the block above the player is current a snow block
			map[biY-1][biX]=1;//make it into an ice block now
		}
		canIce=false;//used to only create one ice block at a time
	}
	
	/*iceDown
	 * Purpose: to create of break an ice block below the player if it is possible
	 * Pre: N/A
	 * Post: void
	 */
	public void iceDown(){
		if(biY == map.length - 1) {//if the player cannot create an ice block below them
			return;
		}
		if(map[biY+1][biX]==1) {//if the block below the player is currently an ice block
			map[biY+1][biX]=0;//make it into a snow block now
		}
		else if(map[biY+1][biX]==0) {//if the block below the player is currently a snow block
			map[biY+1][biX]=1;//make it into an ice block now
		}
		canIce=false;//used to only create one ice block at a time
	}
	
	/*iceLeft
	 * Purpose: to create of break an ice block to the left of the player if it is possible
	 * Pre: N/A
	 * Post: void
	 */
	public void iceLeft(){
		if(biX == 0) {//if the player cannot create an ice block to the left them
			return;
		}
		if(map[biY][biX-1]==1) {//if the block to the left of the player is currently an ice block
			map[biY][biX-1]=0;//make it into a snow block now 
		}
		else if(map[biY][biX-1]==0) {//if the block to the left of the player is currently a snow block
			map[biY][biX-1]=1;//make it into an ice block now 
		}
		canIce=false;//used to only create one ice block at a time
	}
	
	/*iceRight
	 * Purpose: to create of break an ice block to the right of the player if it is possible
	 * Pre: N/A
	 * Post: void
	 */
	public void iceRight(){
		if(biX == map[0].length - 1) {//if the player cannot create an ice block to the right of them
			return;
		}
		if(map[biY][biX+1]==1) {//if the block to the right of the player is currently an ice block
			map[biY][biX+1]=0;//make it into a snow block
		}
		else if(map[biY][biX+1]==0) {//if the block to the right of the player is currently a snow block
			map[biY][biX+1]=1;//make it into a snow block now
		}
		canIce=false;//used to only create one ice block at a time
	}
	
	/*win
	 * Purpose: to check if the user has reached the assigned score for the level
	 * Pre: N/A
	 * Post: one boolean flag, weather the user has reached the score or not
	 */
	boolean win() {
		boolean flag=false;//set the player winning to false
		if(score==Main.amount) {//check if player has collected all the cherries for the level
 			flag=true;//if they have, they have completed the level
		}
		return flag;//return the status of the player's win
	}
	
	/*drawScore
	 * Purpose: draw the score of the player and update it if they collect more cherries
	 * Pre: Graphics g
	 * Post: void
	 */
	private void drawScore(Graphics g) {
		String text = String.valueOf(score);//make the int value of the score into a String
		Graphics2D g2d = (Graphics2D) g;//Graphics2D to output the score
		//all done to output the score the way it is meant to be done
		g2d.setRenderingHint(
				RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(
				RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(
				RenderingHints.KEY_FRACTIONALMETRICS,
				RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g2d.setColor(new Color(61, 94, 227));
		g2d.setFont(new Font("Lato", Font.BOLD, 25));//Set the font to Lato, bold and 25
		g2d.drawString(text, 357,590);//location to print the score
	}
	
	/*run
	 * Purpose: controls all of the movement for each evil ice cream character for each level
	 * Pre: N/A
	 * Post: void
	 */
	public void run() {
		Thread thread = Thread.currentThread();//thread that will be in charge of the movements of the evil ice creams
		while(runner == thread) {//when the runner has started
			if(Main.level==1) {//if the level is currently 1
				enemies[0].moveString = "rrrrrrrrrrllllllllll";//path of the first evil ice cream
				enemies[1].moveString = "llllllllllrrrrrrrrrr";//path of the second evil ice cream
				enemies[2].moveString = "rrrrrrrrrrllllllllll";//path of the third evil ice cream
				int enemyMovePeriod = 2;//enemyMovePeriod is 2
				int enemyReady = enemyMovePeriod;//enemyReady is the same as enemyMovePeriod
				while (runner == thread && Main.level==1) {//if the thread is currently being used and the level is 1
					if(score == Main.amount) {//if the player has collected all of the cherries
						Main.level = 1;//keep it the same level as it will be increased elsewhere
					} 
					else {
						if(enemyReady == 0) {//if enemyReady is equal to 0
							for(int i = 0; i < enemies.length; i++) {//do all of the movements of all of the enemies in the map
								enemies[i].moveEnemy(map,biX,biY);//move the enemy according to the predetermined path
							}
							enemyReady = enemyMovePeriod;//set the enemyReady equal to the enemyMovePeriod again
						}
						else {
							enemyReady--;//decrease the enemyReady by 1
						}
					}
					repaint();//repaint the screen
					requestFocusInWindow();//focus the window
					try {
						Thread.sleep(80);//speed of the evil ice creams
					} 
					catch (InterruptedException e) {//catch the potential error
						e.printStackTrace();
					}
					if(Main.fail==true) {//if the player has failed the round, thus failing the game
						JOptionPane.showMessageDialog(null, "Oh No! The Evil Ice Cream caught you!\nYou must exit the game.","Fail",JOptionPane.PLAIN_MESSAGE);//inform the user they have lost
						System.exit(0);//if the user has lost then they must exit the game
					}
				}
			}

			if(Main.level==2) {
				while (runner == thread && Main.level==2) {//if the level is currently 2
					//TODO level 2
					enemies[0].moveString = "rlrlrlrlrlududududud";//path of the first evil ice creams
					enemies[1].moveString = "rruulldd";//path of the second ice cream
					enemies[2].moveString = "ddrrrruuulllld";//path of the third ice cream
					enemies[3].moveString = "lllllllrrrrrrr";//path of the fourth ice cream
					int enemyMovePeriod = 2;//enemyMovePeriod is 2
					int enemyReady = enemyMovePeriod;//enemyReady is the same as enemyMovePeriod
					while (runner == thread && Main.level==2) {//if the thread is currently being used and the level is 2
						if(score == Main.amount) {//if the player has collected all of the cherries
							Main.level = 2;//keep it the same level as it will be increased elsewhere
						} 
						else {
							if(enemyReady == 0) {//if enemyReady is equal to 0
								for(int i = 0; i < enemies.length; i++) {//do all of the movements of all of the enemies in the map
									enemies[i].moveEnemy(map,biX,biY);//move the enemy according to the predetermined path
								}
								enemyReady = enemyMovePeriod;//set the enemyReady equal to the enemyMovePeriod again
							}
							else {
								enemyReady--;//decrease the enemyReady by 1
							}
						}
						repaint();//repaint the screen
						requestFocusInWindow();//focus the window
						try {
							Thread.sleep(80);//speed of the evil ice creams
						} catch (InterruptedException e) {//catch the potential error
							e.printStackTrace();
						}
						if(Main.fail==true) {//if the player has failed the round, thus failing the game
							JOptionPane.showMessageDialog(null, "Oh No! The Evil Ice Cream caught you!\nYou must exit the game.","Fail",JOptionPane.PLAIN_MESSAGE);//inform the user they have lost
							System.exit(0);//if the user has lost then they must exit the game
						}
					}
				}
			}
			if(Main.level==3) {
				while (runner == thread && Main.level==3) {//if the level is currently 3
					//TODO level 3
					enemies[0].moveString = "dlru";//path of the first ice cream
					enemies[1].moveString = "rrrrrdduulllll";//path of the second evil ice cream
					enemies[2].moveString = "dddrrruuulll";//path of the third evil ice cream
					enemies[3].moveString = "luururdddl";//path of the fourth evil ice cream
					enemies[4].moveString = "rrrrrrrrrrllllllllll";//path of the fifth evil ice cream
					int enemyMovePeriod = 2;//enemyMovePeriod is 2
					int enemyReady = enemyMovePeriod;//enemyReady is the same as enemyMovePeriod
					while (runner == thread && Main.level==3) {//if the thread is currently being used and the level is 3
						if(score == Main.amount) {//if the player has collected all of the cherries
						} 
						else {
							if(enemyReady == 0) {//if enemyReady is equal to 0
								for(int i = 0; i < enemies.length; i++) {//do all of the movements of all of the enemies in the map
									enemies[i].moveEnemy(map,biX,biY);//move the enemy according to the predetermined path
								}
								enemyReady = enemyMovePeriod;//set the enemyReady equal to the enemyMovePeriod again
							}
							else {
								enemyReady--;//decrease the enemyReady by 1
							}
						}
						repaint();//repaint the screen
						requestFocusInWindow();//focus the window
						try {
							Thread.sleep(80);//speed of the evil ice creams
						} catch (InterruptedException e) {//catch the potential error
							e.printStackTrace();
						}
						if(Main.fail==true) {//if the player has failed the round, thus failing the game
							JOptionPane.showMessageDialog(null, "Oh No! The Evil Ice Cream caught you!\nYou must exit the game.","Fail",JOptionPane.PLAIN_MESSAGE);//inform the user they have lost
							System.exit(0);//if the user has lost then they must exit the game
						}
					}
				}
			}
		}
	}
	
	/*keyReleased
	 * Purpose: only allows the user to move one square at a time
	 * Pre: KeyEvent e
	 * Post: void
	 */
	public void keyReleased(KeyEvent e){
		canMove=true;//move only one square at a time
		canIce=true;//move only one square at a time
	}
	public void keyTyped(KeyEvent e){}//keyTyped is required method for KeyListener. It is not used

}
