/*Umar Khan
 * ICS4U
 * January 24th 2021
 * MainFrame Class
 * This class is meant to control all of the screens, buttons, and overall outputs of my game. It will react to certain events and actions done by the user
 */

package BadIceCream;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class MainFrame extends JFrame implements ItemListener,ActionListener{
	private static final long serialVersionUID = 687431382580017855L;//used to remove error from java file
	Image menu;//Image of the main menu
	MainPanel panel;//a MainPanel that is called panel
	IPanel background = new IPanel();//new IPanel for the Main Menu screen image when the user runs the game
	public static int i=1;//variable used for another IPanel (different from the first one)
	IPanel instruc = new IPanel(i);//new IPanel that references a different constructor for the Instructions screen image
	
	Icon Play = new ImageIcon("images/PlayButton.png");//image icon for the Play Button
	JButton play = new JButton(Play);//new button for which will be the Play button
	
	Icon Instructions = new ImageIcon("images/InstructionsButton.png");//image icon for the Instructions Button
	JButton instructions = new JButton(Instructions);//new button for which will be the Instructions button
	
	Icon Back = new ImageIcon("images/BackButton.png");//image icon for the Back Button (on Instructions menu)
	JButton back = new JButton(Back);//new button for which will be the Back button
	
	//MainFrame constructor
	MainFrame(){
		//Set size, background, layout 
		super("Bad Ice Cream");//call this game "Bad Ice Cream"
		setSize(850,700);//set its size to be 850px by 700px
		setVisible(true);//set the screen to be visible
		setResizable(false);//do not let the user resize the game screen
		setLocation(0,0);//top left most corner of the screen
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
			background.setLayout(null);//background should have no layout
			background.setBounds(0,0,850,700);//background is the size of the game
			add(background);
			
			instruc.setLayout(null);//instructions should have no layout
			instruc.setBounds(0,0,850,700);//instructions screen is the size of the game

			play.setBounds(311,390,227,76);//location and size of the play button
			play.addActionListener(this);//add in an action listener so that it can tell if it is clicked
			play.requestFocus();//focused state
			background.add(play);//add the play button to the main menu
			
			instructions.setBounds(311,495,227,76);//location and size of the instruction button
			instructions.addActionListener(this);//add in an action listener so that it can tell if it clicked
			instructions.requestFocus();//focused state
			background.add(instructions);//add the instructions button to the main menu
	}
	/*actionPerformed
	 * Purpose: To output the correct screens according to the user's button inputs
	 * Pre: ActionEvent event (which button was clicked)
	 * Post: void
	 */
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		 if (event.getSource() == play) {//if play button is clicked
			 Container container = getContentPane();////create a container to hold the panel and all the components
			 container.setLayout(null);
			 background.setVisible(false);//stop showing main menu
			 container.setBackground(new Color(164,187,255));//make the background colour a blue
			 panel=new MainPanel();//the panel is a new MainPanel
			 add(panel);//start the game screen
			 validate();
		 }
		 if(event.getSource()==instructions) {//if instructions button is clicked
			 background.setVisible(false);//stop showing main menu
			 instruc.setVisible(true);//make the instructions screen visible
			 back.setVisible(true);//make the back button visible
			 add(instruc);
			 back.setBounds(524,532,227,76);//location of the back button
			 back.addActionListener(this);//add in an action listener so that it can tell if it is clicked
			 back.requestFocus();//focused state
			 instruc.add(back);//add the back button to the instructions screen
		 }
		 if(event.getSource()==back) {//if the back button is clicked
			 instruc.setVisible(false);//stop showing the instructions screen
			 back.setVisible(false);//stop showing the back button
			 background.setVisible(true);//make the main menu visible again
		 }
		
	}
	public void itemStateChanged(ItemEvent e) {}//required method for itemListener, it is not used
}
