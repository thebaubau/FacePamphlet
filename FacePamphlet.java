/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends ConsoleProgram implements FacePamphletConstants {

	// North components
	private JLabel nameLabel;
	private JTextField nameTextField;
	private JButton addProfile;
	private JButton deleteProfile;
	private JButton lookupProfile;

	// West components
	private JTextField statusTextField;
	private JButton changeStatus;
	private JTextField pictureTextField;
	private JButton changePicture;
	private JTextField friendTextField;
	private JButton addFriend;

	/**
	 * This method has the responsibility for initializing the 
	 * interactors in the application, and taking care of any other 
	 * initialization that needs to be performed.
	 */

	public void init() {
		setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		addComponents();
    }

	public void addComponents(){
		nameLabel     = new JLabel("Name");
		nameTextField = new JTextField(TEXT_FIELD_SIZE);
		addProfile    = new JButton("Add");
		deleteProfile = new JButton("Delete");
		lookupProfile = new JButton("Lookup");

		statusTextField  = new JTextField(TEXT_FIELD_SIZE);
		changeStatus     = new JButton("Change Status");
		pictureTextField = new JTextField(TEXT_FIELD_SIZE);
		changePicture    = new JButton("Change Picture");
		friendTextField  = new JTextField(TEXT_FIELD_SIZE);
		addFriend        = new JButton("Add Friend");

		add(nameLabel, NORTH);
		add(nameTextField, NORTH);
		add(addProfile, NORTH);
		add(deleteProfile, NORTH);
		add(lookupProfile, NORTH);

		add(statusTextField, WEST);
		add(changeStatus, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		add(pictureTextField, WEST);
		add(changePicture, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		add(friendTextField, WEST);
		add(addFriend, WEST);

//		FacePamphletCanvas profileCanvas = new FacePamphletCanvas();
//		add(profileCanvas);
	}

    /**
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
    public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == addProfile){
			println("Add: " + nameTextField.getText());
		}
		else if (source == deleteProfile) {
			println("Delete: " + nameTextField.getText());
		}
		else if (source == lookupProfile) {
			println("Lookup: " + nameTextField.getText());
		}
		else if (source == changeStatus) {
			println("Change Status: " + statusTextField.getText());
		}
		else if (source == changePicture) {
			println("Change Picture: " + pictureTextField.getText());
		}
		else if (source == addFriend) {
			println("Change Picture: " + friendTextField.getText());
		}

	}


}
