/*Umar Khan
 * ICS4U
 * January 24th 2021
 * Enemy Class
 * This class is meant to control the movements of the enemy ice creams and change the coordinates accordingly
 */

package BadIceCream;

public class Enemy {
	String moveString;//string that will contain the predetermined movement of each evil character
	int moveIndex = 0;//set the moveIndex to 0
	int eX, eY;//x-coordinate and y-coordinate of the evil ice cream
	
	public Enemy(int eX, int eY) {
		this.eX = eX;
		this.eY = eY;
	}
	/*moveEnemy
	 * Purpose: to move the enemy ice creams in their predetermined paths depending on the String moveString
	 * Pre: one 1D array, map (map of the game), one int pX (x-coordinate of the player), one int pY (y-coordinate of the player)
	 * Post: void
	 */
	public void moveEnemy(int[][] map, int pX, int pY) {
		char move = moveString.charAt(moveIndex);//find the current letter
		int nX = eX;//nX is the current x-coordinate of the evil ice cream
		int nY = eY;//nY is the current y-coordinate of the evil ice cream
		if(move == 'r') {//if it is'r'
			nX=nX+1;//move the evil ice cream one square to the right
		}
		else if (move == 'l') {//if it is 'l'
			nX=nX-1;//move the evil ice cream one square to the left
		}
		else if(move == 'u') {//if it is 'u'
			nY=nY-1;//move the evil ice cream one square up
		}
		else if(move == 'd') {//if it is 'd' 
			nY=nY+1;//move the evil ice cream one square down
		}
		if(map[eY][eX]==3 && map[nY][nX]==map[pY][pX]) {//if the evil ice cream coordinates equal that of the player (sundae ice cream)
			map[eY][eX]=0;//change the current position of the evil ice cream to a snow block (as it is on top of the player now)
			map[nY][nX]=6;//change the current position of the sudae ice cream to a block that has an overlap
			if(map[nY][nX]==6) {
				Main.fail=true;//the player has failed the game
			}
		}
		if(map[eY][eX]==5) {//if the evil ice cream's coordinate is them on top of a cherry
			map[eY][eX]=2;//make the coordinate back into the original cherry
			map[nY][nX]=3;//make the coordinate adjacent to the player back into the regular evil ice cream (no overlap)
		}
		if(map[nY][nX]==0 && map[eY][eX]!=5 || map[nY][nX]==1 && map[eY][eX]!=5) {//if the the block adjacent to the evil ice cream is a snow block or an ice block
			map[eY][eX]=0;//change their current position to a snow block
			map[nY][nX]=3;//go on top of the adjacent square and if it is an ice block, break it
		}
		else if(map[nY][nX]==2){//if the block adjacent to the evil ice cream is a cherry
			map[nY][nX]=5;//change the cherry block into a block that is both the ice cream overlapping the cherry
			map[eY][eX]=0;//change the current position of the evil ice cream to a snow block (as they have just left the block)
		}
		eX = nX;//update the x-coordinate of the evil ice cream
		eY = nY;//update the y-coordinate of the evil ice cream
		
		moveIndex++;//go to the next letter
		if(moveIndex == moveString.length()){//if the entire length of the String has been reached but the game is not over, then restart it
			moveIndex = 0;
		}
	}
}
