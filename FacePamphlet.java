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
	private JLabel fileLabel;
	private JTextField fileTextField;
	private JButton saveFile;
	private JButton loadFile;

	// West components
	private JTextField statusTextField;
	private JButton changeStatus;
	private JTextField pictureTextField;
	private JButton changePicture;
	private JTextField friendTextField;
	private JButton addFriend;

	private FacePamphletDatabase database;
	private FacePamphletProfile profileEntry;
	private FacePamphletCanvas profileCanvas = new FacePamphletCanvas();
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

	private void addComponents(){
		nameLabel     = new JLabel("Name");
		nameTextField = new JTextField(TEXT_FIELD_SIZE);
		addProfile    = new JButton("Add");
		deleteProfile = new JButton("Delete");
		lookupProfile = new JButton("Lookup");
		fileLabel     = new JLabel("File");
		fileTextField = new JTextField(TEXT_FIELD_SIZE);
		saveFile      = new JButton("Save");
		loadFile      = new JButton("Load");

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
		add(fileLabel, NORTH);
		add(fileTextField, NORTH);
		add(saveFile, NORTH);
		add(loadFile, NORTH);

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
		String fileField = fileTextField.getText();

		if (nameField.isEmpty() && fileField.isEmpty()) return;

		if (source == addProfile){
			if (database.containsProfile(nameField)){
				profileCanvas.showMessage("Profile " + nameField + " already exists");
			} else {
				profileEntry = new FacePamphletProfile(nameField);
				database.addProfile(profileEntry);
				currentProfile = nameField;
				lookupProfile(currentProfile);
			}
		}

		else if (source == deleteProfile) {
			if (database.containsProfile(nameField)){
				database.deleteProfile(nameField);
				profileCanvas.removeAll();
				profileCanvas.showMessage("Profile of " + nameField + " deleted");
				currentProfile = null;
			}
			else {
				profileCanvas.showMessage("A profile with the name " + nameField + " does not exist");
			}
		}

		else if (source == lookupProfile) {
			if (database.containsProfile(nameField)){
				profileEntry = database.getProfile(nameField);
				profileCanvas.displayProfile(profileEntry);
				profileCanvas.showMessage("Displaying " + database.getProfile(nameField));
				currentProfile = nameField;
			} else {
				profileCanvas.showMessage("A profile with the name " + nameField + " does not exist");
			}
		}

		else if (source == changeStatus) {
			if (currentProfile != null){
				profileEntry = database.getProfile(currentProfile);
				profileEntry.setStatus(statusTextField.getText());
				lookupProfile(currentProfile);
				profileCanvas.showMessage("Status updated to: " + profileEntry.getStatus());
			} else {
				profileCanvas.showMessage("Lookup a profile to change the status.");
			}
		}

		else if (source == changePicture) {
			if (currentProfile != null){
				GImage image = null;
				try {
					image = new GImage("C:\\Automation\\Workspace\\FacePamphlet\\images\\" + pictureTextField.getText());
					profileCanvas.showMessage("Picture updated");
				} catch (ErrorException ex){
					profileCanvas.showMessage("File not found");
				}
				profileEntry = database.getProfile(currentProfile);
				profileEntry.setImage(image);
				lookupProfile(currentProfile);
			} else {
				profileCanvas.showMessage("Lookup a profile to change the image.");
			}
		}

		else if (source == addFriend || source == friendTextField) {
			if (currentProfile != null){
				profileEntry = database.getProfile(currentProfile);
				if (isFriendAlreadyInList(profileEntry)){
					profileCanvas.showMessage(profileEntry.getName() + " already has " + friendTextField.getText() +
							"added as a friend");
				}
				else {
					String friend = friendTextField.getText();
					reciprocalFriend(friend);
					lookupProfile(currentProfile);
				}
			} else {
				profileCanvas.showMessage("Lookup a profile to add friends.");
			}
		}

		else if (source == loadFile){
			try {
				database.loadFile("C:\\Automation\\Workspace\\FacePamphlet\\" + fileField);
				profileCanvas.showMessage("File loaded successfully!");
			} catch (ErrorException ex) {
				profileCanvas.showMessage("Cannot open file " + fileField);
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
			profileCanvas.showMessage("No profile with that name exists!");
		} else {
			profileEntry.addFriend(friend);
			database.getProfile(friend).addFriend(profileEntry.getName());
			profileCanvas.showMessage(friendTextField.getText() + " added as a friend");
		}
	}

	private void loadDatabase(){
		database = new FacePamphletDatabase();
	}

	private void lookupProfile(String text){
		if (database.containsProfile(text)){
			profileCanvas.displayProfile(database.getProfile(text));
			profileCanvas.showMessage("Displaying " + database.getProfile(text));
		} else {
			profileCanvas.showMessage("A profile with the name " + text + " does not exist");
		}

	}
}