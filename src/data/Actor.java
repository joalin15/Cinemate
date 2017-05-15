package data;

import java.util.Vector;

public class Actor {
	/*
	 * PRIVATE VARIABLE DECLARATIONS
	 */
	private String fname;
	private String lname;
	private String name;
	private String image;
	private Vector<Movie> movies = new Vector<Movie>();

	/*
	 * CONSTRUCTORS
	 */
	public Actor() {
	}
	public Actor(String f, String l, String i, Movie m) {
		fname = f;
		lname = l;
		name = fname + " " + lname;
		image = i;
		movies.add(m);
	}
	
	/* 
	 * SETTERS 
	 */
	public void setName(String s) {
		name = s;
	}	
	public void addMovie(Movie m) {
		movies.add(m);
	}
	
	/* 
	 * GETTERS 
	 */
	public String getName() {
		return name;
	}
	public String getNameLowerCase() {
		String nameLowerCase = name.toLowerCase();
		return nameLowerCase;
	}
	public String getImage() {
		return image;
	}
	public Vector<Movie> getMovies() {
		return movies;
	}
	public Vector<String> getMovieNames() {
		Vector<String> v = new Vector<String>();
		for (int i = 0; i < movies.size(); i++) {
			v.add(movies.elementAt(i).getTitle());
		}
		return v;
	}
}

	
	

