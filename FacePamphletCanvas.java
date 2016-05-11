/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */


import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas
		implements FacePamphletConstants {

	private GLabel message;

	/**
	 * Constructor
	 * This method takes care of any initialization needed for
	 * the display
	 */
	public FacePamphletCanvas() {

	}

	/**
	 * This method displays a message string near the bottom of the
	 * canvas.  Every time this method is called, the previously
	 * displayed message (if any) is replaced by the new message text
	 * passed in.
	 */
	public void showMessage(String msg) {
		if (message != null){
			remove(message);
		}
		message = new GLabel(msg);
		message.setFont(MESSAGE_FONT);
		message.setLocation((getWidth() - message.getWidth()) / 2, getHeight() - BOTTOM_MESSAGE_MARGIN);
		add(message);
	}


	/**
	 * This method displays the given profile on the canvas.  The
	 * canvas is first cleared of all existing items (including
	 * messages displayed near the bottom of the screen) and then the
	 * given profile is displayed.  The profile display includes the
	 * name of the user from the profile, the corresponding image
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		removeAll();

		GLabel profileName;
		GLabel profileStatus;
		GLine lineDelimitation;
		GImage profileImage;
		Iterator<String> friendIterator;

		// Profile name to display
		profileName = new GLabel(profile.getName());
		profileName.setFont(PROFILE_NAME_FONT);
		profileName.setLocation(LEFT_MARGIN, profileName.getAscent() / 2 + TOP_MARGIN);
		profileName.setColor(Color.BLUE);

		lineDelimitation = new GLine(LEFT_MARGIN, profileName.getAscent() / 2 + TOP_MARGIN + 5, getWidth() -
				LEFT_MARGIN, profileName.getAscent() / 2 + TOP_MARGIN + 5);
		lineDelimitation.setColor(Color.BLACK);

		// Profile picture to be displayed
		if (profile.getImage() == null){
			showNullImage();
		} else {
			profileImage = profile.getImage();
			profileImage.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
//       profileImage.scale(0.4);
			profileImage.setLocation(IMAGE_MARGIN, IMAGE_MARGIN * 3);
			add(profileImage);
		}

		// Profile status to be displayed
		if (profile.getStatus() == null){
			profileStatus = new GLabel("No current status");
		} else {
			profileStatus = new GLabel(profile.getStatus());
			profileStatus.setFont(PROFILE_STATUS_FONT);
			profileStatus.setLocation(LEFT_MARGIN, IMAGE_MARGIN * 3 + IMAGE_HEIGHT + STATUS_MARGIN);
		}

		// Friend header label
		GLabel friendsHeader = new GLabel("Friends:");
		friendsHeader.setFont(PROFILE_FRIEND_LABEL_FONT);
		friendsHeader.setLocation(getWidth() / 2, IMAGE_MARGIN * 3 + friendsHeader.getAscent());

		// Friend iterator
		int friendCount = 1;
		friendIterator = profile.getFriends();

		if (friendIterator != null){
			while (friendIterator.hasNext()){
				GLabel friendName = new GLabel(friendIterator.next());
				friendName.setFont(PROFILE_FRIEND_FONT);
				friendName.setLocation(getWidth() / 2, IMAGE_MARGIN * 3 + friendsHeader.getAscent() +
						IMAGE_MARGIN * friendCount);
				add(friendName);
				friendCount++;
			}
		}

		add(profileName);
		add(profileStatus);
		add(lineDelimitation);
		add(friendsHeader);
	}

	private void showNullImage(){
		GCompound nullImage = new GCompound();
		nullImage.setLocation(IMAGE_MARGIN, IMAGE_MARGIN * 3);

		GRect nullImageRect = new GRect(IMAGE_WIDTH, IMAGE_HEIGHT);

		GLabel noImageLabel = new GLabel("NO IMAGE");
		noImageLabel.setFont(PROFILE_IMAGE_FONT);

		nullImage.add(nullImageRect);
		nullImage.add(noImageLabel, (IMAGE_WIDTH - noImageLabel.getWidth()) / 2, (IMAGE_HEIGHT + noImageLabel.getAscent()) / 2);

		add(nullImage);
	}

	private void getProfileFriends(FacePamphletProfile profiles){


	}


}