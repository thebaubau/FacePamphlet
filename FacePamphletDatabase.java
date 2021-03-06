/*
 * File: FacePamphletDatabase.java
 * -------------------------------
 * This class keeps track of the profiles of all users in the
 * FacePamphlet application.  Note that profile names are case
 * sensitive, so that "ALICE" and "alice" are NOT the same name.
 */

import acm.graphics.GImage;
import acm.util.ErrorException;

import java.io.*;
import java.util.*;

public class FacePamphletDatabase implements FacePamphletConstants {

	private HashMap<String, FacePamphletProfile> profiles = new HashMap<>();

	/**
	 * Constructor
	 * This method takes care of any initialization needed for
	 * the database.
	 */
	public FacePamphletDatabase() {}

	/**
	 * This method adds the given profile to the database.  If the
	 * name associated with the profile is the same as an existing
	 * name in the database, the existing profile is replaced by
	 * the new profile passed in.
	 */
	public void addProfile(FacePamphletProfile profile) {
		String name = profile.getName();

		if (profiles.containsKey(name)) {
			profiles.replace(name, profile);
		} else if (name != null) {
			profiles.put(name, profile);
		}
	}


	/**
	 * This method returns the profile associated with the given name
	 * in the database.  If there is no profile in the database with
	 * the given name, the method returns null.
	 */
	public FacePamphletProfile getProfile(String name) {
		if (profiles.containsKey(name)) {
			return profiles.get(name);
		}
		return null;
	}

	/**
	 * This method removes the profile associated with the given name
	 * from the database.  It also updates the list of friends of all
	 * other profiles in the database to make sure that this name is
	 * removed from the list of friends of any other profile.
	 *
	 * If there is no profile in the database with the given name, then
	 * the database is unchanged after calling this method.
	 */
	public void deleteProfile(String name) {
		if (profiles.containsKey(name)) {
			FacePamphletProfile profileToRemove = profiles.get(name);
			Iterator<String> iterator = profileToRemove.getFriends();
			if (iterator != null) {
				while(iterator.hasNext()) {
					String friendName = iterator.next();
					FacePamphletProfile friendsProfile = profiles.get(friendName);
					friendsProfile.removeFriend(name);
				}
			}
			profiles.remove(name);
		}
	}

	/**
	 * This method returns true if there is a profile in the database
	 * that has the given name.  It returns false otherwise.
	 */
	public boolean containsProfile(String name) {
		return profiles.containsKey(name);
	}

	public void loadFile(String file) {
		BufferedReader rd;
		try {
			rd = new BufferedReader(new FileReader(file));
			int nProfile = Integer.parseInt(rd.readLine());

			for (int i = 0; i < nProfile; i++) {
				// Create new profile;
				String name = rd.readLine();
				FacePamphletProfile profile = new FacePamphletProfile(name);

				// Set image for profile if it exists
				String image = rd.readLine();
				profile.setImageName(image);
				if (image.length() != 0) {
					profile.setImage(new GImage(image));
				}

				// Set status for profile
				String status = rd.readLine();
				profile.setStatus(status);

				// Add friends for this profile
				while (true) {
					String friend = rd.readLine();
					if (friend.length() == 0) break;
					profile.addFriend(friend);
				}

				// Adding the profile
				profiles.put(name, profile);
			}
			rd.close();
		} catch (IOException ex) {
			throw new ErrorException(ex);
		}
	}
}