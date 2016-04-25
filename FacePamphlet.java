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
import java.util.Iterator;
import javax.swing.*;
import javax.swing.text.html.HTMLDocument;

public class FacePamphlet extends Program implements FacePamphletConstants {

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

	private FacePamphletDatabase database;
	private FacePamphletProfile profileEntry;
	private String currentProfile;


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

		statusTextField.addActionListener(this);
		pictureTextField.addActionListener(this);
		friendTextField.addActionListener(this);
		addActionListeners();

		loadDatabase();

		FacePamphletCanvas profileCanvas = new FacePamphletCanvas();
		add(profileCanvas);
	}

    /**
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
    public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		String nameField = nameTextField.getText();
		if (nameField.isEmpty()) return;

		if (source == addProfile){
			profileEntry = new FacePamphletProfile(nameField);
			if (database.containsProfile(nameField)){
				println("Add: profile for: " + profileEntry.toString() + " already exists");
			} else {
				database.addProfile(profileEntry);
				currentProfile = nameField;
				println("Add: new profile: " + profileEntry.toString());
			}
		}

		else if (source == deleteProfile) {
			if (database.containsProfile(nameField)){
				println("Delete: profile of: " + profileEntry.getName() + " deleted");
				database.deleteProfile(nameField);
				currentProfile = null;
			}
			else {
				println("Delete: profile with name: " + nameField + " does not exist");
			}
		}

		else if (source == lookupProfile) {
			if (database.containsProfile(nameField)){
				println("Lookup: " + database.getProfile(nameField));
				currentProfile = nameField;
			} else {
				println("Lookup: profile with name: " + nameField + " does not exist");
			}
		}

		else if (source == changeStatus) {
			if (currentProfile != null){
				profileEntry = database.getProfile(currentProfile);
				profileEntry.setStatus(statusTextField.getText());
				println("Change Status: for profile: " + profileEntry.getName() + " to " + profileEntry.getStatus());
			} else {
				println("Lookup a profile to change the status");
			}
		}

		else if (source == changePicture) {
			if (currentProfile != null){
				GImage image = null;
				try {
					image = new GImage("MehranS.jpg");
				} catch (ErrorException ex){
					println("File not found");
				}
				profileEntry = database.getProfile(currentProfile);
				profileEntry.setImage(image);
				println("Image added");
				println("Change Picture: " + pictureTextField.getText());
			} else {
				println("Lookup a profile to change the image");
			}
		}

		else if (source == addFriend || source == friendTextField) {
			if (currentProfile != null){
				profileEntry = database.getProfile(currentProfile);
				if (isFriendAlreadyInList(profileEntry)){
					println("Friend already exists");
				}
				else {
					String friend = friendTextField.getText();
					reciprocalFriend(friend);
				}
			} else {
				println("Lookup a profile to change the image");
			}
		}
	}

	private boolean isFriendAlreadyInList(FacePamphletProfile profile){
		Iterator<String> it = profile.getFriends();
		if (it == null) return false;
		while (it.hasNext()){
			if (it.next().equals(friendTextField.getText())){
				return true;
			}
		}
		return false;
	}

	private void reciprocalFriend(String friend){
		if (database.getProfile(friend) == null){
			println("No profile with that name exists!");
		} else {
			profileEntry.addFriend(friend);
			database.getProfile(friend).addFriend(profileEntry.getName());
			println("Added Friend: " + friendTextField.getText());
		}
	}

	private void loadDatabase(){
		database = new FacePamphletDatabase();
	}
}
