package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.Movie;
import helper.Constants;
import server.Database;

/**
 * Servlet implementation class ActionServlet
 */
@WebServlet("/ActionServlet")
public class ActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActionServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// initialize variables
		PrintWriter pw = response.getWriter();
		HttpSession session = request.getSession();
		String currentUsername = null;
		Database db = null;
				
		// get information from GET request
		String action = request.getParameter("action");
		String movie = request.getParameter("movie");
		String movieID = request.getParameter("movieID");
		
		// attempt to retrieve session objects
		try {
			db = (Database) session.getAttribute("database");
			currentUsername = (String) session.getAttribute("currentUsername");
			if (db == null || currentUsername == null) {
				throw new Exception("session expired");
			}
		} catch (Exception e) {
			// returns to sign-in page if session expired
			request.getRequestDispatcher(Constants.entryPage).forward(request, response);
			return;
		}
		
		// handles rating action
		if (action.equals("Rated")) {
			String ratingString = request.getParameter("rating");
			String currentRatingString = request.getParameter("crating");
			
			int rating = Integer.parseInt(ratingString);
			int currentRating = Integer.parseInt(currentRatingString);
						
			int newRating = updateMovieRating(request, response, rating, movie, movieID, db);
			if (newRating != currentRating) {
				pw.print(newRating);
			}
			db.insertEvent(currentUsername, action, movieID, rating);
		// handles like, dislike, watched actions
		} else {
			boolean movieExists = db.getMovieResultsWithMovieID(movieID) != null;
			if (movieExists) {
				try {
					db.insertMovieWithMovieID(movieID, movie, 0, 0);
				} catch (SQLException e) {
				}
			}
			db.insertEvent(currentUsername, action, movieID);
		}
				
		pw.close();
	}

	private int updateMovieRating(
			HttpServletRequest request, 
			HttpServletResponse response, 
			int rating, 
			String movie,
			String movieID, 
			Database db) throws ServletException, IOException {
		
		Movie m = db.getMovieResultsWithMovieID(movieID);
		
		if (m != null) {
			int totalRatings = (int) m.getRatingCount() + 1;
			int ratingTotal = (int) m.getRatingTotal() + rating;
			
			try {
				db.updateMovieRatingWithMovieID(movieID, totalRatings, ratingTotal);
			} catch (SQLException e) {
			}
			
			float newRatingDouble = (float) ratingTotal / (float) totalRatings;
			int newRating = Math.round(newRatingDouble);
			
			return newRating;
		}
		else {
			try {
				db.insertMovieWithMovieID(movieID, movie, 1, rating);
			} catch (SQLException e) {
			}
			return rating;
		}
		
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
