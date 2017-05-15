package server;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import data.FeedEvent;
import data.Movie;
import data.User;
import helper.Constants;

public final class Database {
	/**
	 * PRIVATE VARIABLE DECLARATIONS
	 */
	Connection conn = null;
	String ipaddress = "";
	String dbName = "";
	String username = "";
	String password = "";
	
	/**
	 *  CONSTRUCTOR
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Database() throws SQLException, ClassNotFoundException {
		parseConfigurations();
		
		System.out.println("Attempting to connect to database...\n"
				+ "- username: '" + username + "'\n"
				+ "- password: '" + password + "'");
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(
				"jdbc:mysql://"
				+ ipaddress + "/"
				+ dbName
				+ "?user=" + username
				+ "&password=" + password
				+ "&useSSL=false");
		System.out.println("Connection successful!");
	}
	private void parseConfigurations() {
        String fileName = "config.txt"; 
        
        InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
        
        Scanner reader = new Scanner(is);
		while (reader.hasNextLine()) {
			String line = reader.nextLine();
			String category = line.substring(0, line.indexOf(":"));
            String content = line.substring(line.indexOf(":") + 1, line.length()).trim();

            switch (category) {
			case "ipaddress":
				ipaddress = content;
				break;
			case "db":
				dbName = content;
				break;
			case "user":
				username = content;
				break;
			case "password":
				password = content;
				break;
			}
		}
		
		if (reader != null) {
			reader.close();
		}
	}
	
	/**
	 *  MOVIE-RELATED
	 */
	public Movie getMovieResultsWithMovieID(String movieID) {
		Movie m = null;
		String statementText = "SELECT * FROM movie WHERE movie_id=?";
		ResultSet rs = executePSQuery(statementText, movieID);
		m = getMovieFromResultSet(rs);
		return m;
	}
	public String getMovieTitleWithMovieID(String movieID) {
		String movieTitle = "";
		Movie m = getMovieResultsWithMovieID(movieID);
		if (m != null) {
			movieTitle = m.getTitle();
		}
		return movieTitle;
	}
	public void insertMovieWithMovieID(String movieID, String movieTitle, int totalRatings, int ratingTotal) throws SQLException {
		String statementText = "INSERT INTO " + Constants.dbName + ".movie "
				+ "(movie_id, movie_title, movie_total_ratings, movie_rating_total) "
				+ "VALUES ('" + movieID + "', '" + movieTitle + "', '" + totalRatings + "', '" + ratingTotal + "')";
		executeSUpdate(statementText);
	}
	public void updateMovieRatingWithMovieID(String movieID, int totalRatings, int ratingTotal) throws SQLException {
		String statementText = "UPDATE " + Constants.dbName + ".movie "
				+ "SET movie_total_ratings='" + totalRatings
				+ "', movie_rating_total='" + ratingTotal 
				+ "' WHERE movie_id='" + movieID + "';";
		
		executeSUpdate(statementText);
	}

	/**
	 * USER RELATED
	 */
	public User getUserWithUsername(String username, boolean caseSensitive) {
		ArrayList<User> users = new ArrayList<User>();
		User user = null;
		
		String statementText = "";
		if (caseSensitive) {
			statementText = "SELECT * FROM user WHERE BINARY user_username=?";
		} else {
			statementText = "SELECT * FROM user WHERE user_username=?";
		}
		ResultSet rs = executePSQuery(statementText, username);
		users = getUsersFromResultSet(rs);
		if (users.size() > 0) {
			user = users.get(0);
		}
		return user;
	}
	public ArrayList<User> getUsersWithFirstName(String fname, boolean caseSensitive) {
		ArrayList<User> users = new ArrayList<User>();
		String statementText = "";
		if (caseSensitive) {
			statementText = "SELECT * FROM user WHERE BINARY user_fname=?";
		} else {
			statementText = "SELECT * FROM user WHERE user_fname=?";
		}
		ResultSet rs = executePSQuery(statementText, fname);
		users = getUsersFromResultSet(rs);
		return users;
	}
	public ArrayList<User> getUsersWithLastName(String lname, boolean caseSensitive) {
		ArrayList<User> users = new ArrayList<User>();
		String statementText = "";
		if (caseSensitive) {
			statementText = "SELECT * FROM user WHERE BINARY user_lname=?";
		} else {
			statementText = "SELECT * FROM user WHERE user_lname=?";
		}
		ResultSet rs = executePSQuery(statementText, lname);
		users = getUsersFromResultSet(rs);
		return users;
	}
	public String getUserImageWithUsername(String username) {
		User u = getUserWithUsername(username, true);
		if (u != null) {
			return u.getImage();
		} else {
			return "";
		}
	}
	public ArrayList<String> getFollowingOfUsername(String username) {
		ArrayList<String> followingUsernames = new ArrayList<String>();
		String statementText = "SELECT * FROM follow WHERE follow_username_following=?";
		ResultSet rs = executePSQuery(statementText, username);
		try {
			while (rs.next()) {
				String followingUsername = rs.getString("follow_username_being_followed");
				followingUsernames.add(followingUsername);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return followingUsernames;
	}
	public ArrayList<String> getFollowersOfUsername(String username) {
		ArrayList<String> followerUsernames = new ArrayList<String>();
		String statementText = "SELECT * FROM follow WHERE follow_username_being_followed=?";
		ResultSet rs = executePSQuery(statementText, username);
		try {
			while (rs.next()) {
				String followerUsername = rs.getString("follow_username_following");
				followerUsernames.add(followerUsername);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return followerUsernames;
	}
	public void insertUser(
			String username,
			String password,
			String fname,
			String lname,
			String image) throws SQLException {
		String statementText = "INSERT INTO " + Constants.dbName + ".user "
				+ "(user_username, user_password, user_fname, user_lname, user_image) "
				+ "VALUES ('" + username + "', '" + password + "', '" + fname + "', '" + lname + "', '" + image + "')";
		executeSUpdate(statementText);
	}

	/**
	 *  FOLLOWING RELATED
	 */
	public void insertFollowRelationship(String usernameFollowing, String usernameBeingFollowed) {
		String statementText = "INSERT INTO " + Constants.dbName + ".follow "
			+ "(follow_username_following, follow_username_being_followed) "
			+ "VALUES ('" + usernameFollowing + "', '" + usernameBeingFollowed + "');";
		executeSQuery(statementText);
	}
	public void removeFollowRelationhip(String usernameFollowing, String usernameBeingFollowed) {
		String statementText = "DELETE FROM " + Constants.dbName + ".follow "
				+ "WHERE follow_username_following='" + usernameFollowing + "' and "
				+ "follow_username_being_followed='" + usernameBeingFollowed + "';";
		executeSQuery(statementText);
	}
	
	/**
	 *  FEED/EVENT-RELATED
	 */
	public void insertEvent(String username, String action, String movieID, int rating) {
		String statementText = "INSERT INTO " + Constants.dbName + ".event "
				+ "(event_username, event_action, event_rating, event_movie_id) "
				+ "VALUES ('" + username + "', '" + action + "', '" + rating + "', '" + movieID + "');";
		executeSQuery(statementText);
	}
	public void insertEvent(String username, String action, String movieID) {
		String statementText = "INSERT INTO " + Constants.dbName + ".event "
				+ "(event_username, event_action, event_movie_id) "
				+ "VALUES ('" + username + "', '" + action + "', '" + movieID + "');";
		executeSQuery(statementText);
	}
	public ArrayList<FeedEvent> getEventsOfUsername(String username) {
		ArrayList<FeedEvent> events = new ArrayList<FeedEvent>();
		String statementText = "SELECT * FROM event WHERE event_username=?";

		ResultSet rs = executePSQuery(statementText, username);
	
		try {
			while (rs.next()) {
				String action = rs.getString("event_action");
				String movieID = rs.getString("event_movie_id");
				String movieTitle = getMovieTitleWithMovieID(movieID);
				java.sql.Timestamp tsSQL = rs.getTimestamp("event_timestamp");
				Date ts = new Date(tsSQL.getTime());
				String userImage = getUserImageWithUsername(username);
				FeedEvent event = null;
				if (action.equals("Rated")) {
					int rating = rs.getInt("event_rating");
					event = new FeedEvent(username, userImage, action, movieTitle, movieID, rating, ts);
				} else {
					event = new FeedEvent(username, userImage, action, movieTitle, movieID, ts);
				}
				
				events.add(event); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		return events;
	}
	
	/**
	 * EXECUTES STATEMENTS
	 */
	//for Prepared Statement with String parameter
	private ResultSet executePSQuery(String statementText, String parameter) {
		ResultSet rs = null;
		try {
			PreparedStatement ps = conn.prepareStatement(statementText);
			ps.setString(1, parameter);
			rs = ps.executeQuery();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return rs;
	}
	//for Statement
	//for Statement
	private ResultSet executeSQuery(String statementText) {
		ResultSet rs = null;
		try {
			Statement statement = conn.createStatement();
			rs = statement.executeQuery(statementText);
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		return rs;
	}
	private void executeSUpdate(String statementText) throws SQLException {
		Statement statement = conn.createStatement();
		statement.executeUpdate(statementText);

	}

	/**
	 * CONVERT TO DATA OBJECTS 
	 */
	private Movie getMovieFromResultSet(ResultSet rs) {
		Movie m = new Movie();
		try {
			while (rs.next()) {
				m.setTitle(rs.getString("movie_title"));
				m.setRatingTotal(rs.getDouble("movie_rating_total"));
				m.setRatingCount(rs.getDouble("movie_total_ratings"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return m;
	}
	private ArrayList<User> getUsersFromResultSet(ResultSet rs) {
		ArrayList<User> users = new ArrayList<User>();
		try {
			while (rs.next()) {
				User user = new User(
						rs.getString("user_fname"),
						rs.getString("user_lname"),
						rs.getString("user_username"),
						rs.getString("user_password"),
						rs.getString("user_image"));
				users.add(user);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return users;
	}
}
