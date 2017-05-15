package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.Movie;
import helper.Constants;
import server.API;
import server.Database;

/**
 * Servlet implementation class MovieServlet
 */
@WebServlet("/MovieServlet")
public class MovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MovieServlet() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		
		String movieTitle = request.getParameter("name");
		
		// establish connection to database
		Database db = null;

		try {
			db = (Database) session.getAttribute("database");			
			if (db == null) {
				throw new Exception("session expired");
			}
		} catch (Exception e) {
			request.setAttribute("result", "Session Expired! Please enter an input file again");
			request.getRequestDispatcher(Constants.entryPage).forward(request, response);
			return;
		}
		
		String movieID = API.getMovieIDOfMovieTitle(movieTitle);
		Movie m = db.getMovieResultsWithMovieID(movieID);
				

		if (m!= null) {
			int totalRatings = (int) m.getRatingCount();
			int ratingTotal = (int) m.getRatingTotal();
			double rating = (double) ratingTotal / (double) totalRatings;
			int roundedRating = (int) Math.round(rating);
			request.setAttribute("rating", roundedRating);
		} else {
			request.setAttribute("rating", 0);
		}
			
		request.setAttribute("title", movieTitle);
		request.getRequestDispatcher("movie.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 
		doGet(request, response);
	}

}
