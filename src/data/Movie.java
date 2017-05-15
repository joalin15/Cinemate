package data;

public class Movie {
	
	/*
	 * PRIVATE VARIABLE DECLARATIONS
	 */
	private String title;
	private double ratingTotal = 0.0;
	private double ratingCount = 0.0;
	
	/*
	 * SETTERS
	 */
	public void setRatingTotal(double d) {
		ratingTotal = d;
	}
	public void setRatingCount(double d) {
		ratingCount = d;
	}
	public void setTitle(String s) {
		title = s;
	}

	/*
	 * GETTERS
	 */
	public String getTitle() {
		return title;
	}
	public String getTitleLowerCase() {
		return title.toLowerCase();
	}
	public double getRating() {
		if (ratingTotal == 0.0 || ratingCount == 0.0) {
			return -5.0;
		}
		return (ratingTotal/ratingCount);
	}
	public double getRatingTotal() {
		return ratingTotal;
	}
	public double getRatingCount() {
		return ratingCount;
	}
}
