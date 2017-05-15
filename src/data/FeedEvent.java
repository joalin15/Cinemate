package data;

import java.util.Date;

public class FeedEvent {
	/*
	 * PRIVATE VARIABLE DECLARATIONS
	 */
	private String action;
	private String movie;
	private String movieID;
	private double rating = 0.0;
	private Date timestamp;
	private String username;
	private String userImage;
	
	/*
	 * CONSTRUCTORS
	 */
	public FeedEvent() {
		
	}
	public FeedEvent(String u, String ui, String a, String m, String mID, Date d) {
		username = u;
		userImage = ui;
		action = a;
		movie = m;
		movieID = mID;
		timestamp = d;
	}
	public FeedEvent(String u, String ui, String a, String m, String mID, double r, Date d) {
		username = u;
		userImage = ui;
		action = a;
		movie = m;
		movieID = mID;
		rating = r;
		timestamp = d;
	}
	
	/*
	 * SETTERS
	 */
	public void setAction(String s) {
		action = s;
	}
	public void setMovie(String s) {
		movie = s;
	}
	public void setMovieID(String s) {
		movieID = s;
	}
	public void setRating(double d) {
		rating = d;
	}
	
	/*
	 * GETTERS
	 */
	public String getUsername() {
		return username;
	}
	public String getUserImage() {
		return userImage;
	}
	public String getAction() {
		return action;
	}
	public String getMovie() {
		return movie;
	}
	public String getMovieID() {
		return movieID;
	}
	public double getRating() {
		return rating;
	}
	public Date getTimestamp() { 
		return timestamp;
	}
}
