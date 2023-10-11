/*Umar Khan
 * ICS4U
 * January 24th 2021
 * IPanel Class
 * This class is meant to draw the necessary screens for the game, such as the Main Menu screen and Instructions screen
 */

package BadIceCream;
import java.awt.*;
import javax.swing.*;
public class IPanel extends JPanel {
	private static final long serialVersionUID = -133194026026558670L;//used to remove error in Java file
	Image  screen;//Image of the screen that will be displayed (either Main Menu or Instructions)
	
	//IPanel constructor that will be used to display only the Main Menu 
	public IPanel() {
		super();
		Toolkit kit = Toolkit.getDefaultToolkit();
		screen = kit.getImage("images/MainMenu.png");
	}
	//IPanel(int i) constructor that will be used to display only the Instructions menu
	public IPanel(int i) {
		super();
		Toolkit kit = Toolkit.getDefaultToolkit();
		screen = kit.getImage("images/Instructions.png");
	}
	/*paintComponent
	 * Purpose: To draw the specific and appropriate menu screen
	 * Pre: Graphics comp
	 * Post: void
	 */
	public void paintComponent(Graphics comp) {
		Graphics2D comp2D = (Graphics2D) comp;//Graphics2D variable
		comp2D.drawImage(screen, 0, 0, this);//draw the appropriate screen image that is needed
	}
}

