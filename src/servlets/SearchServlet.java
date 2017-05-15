package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import data.User;
import helper.Constants;
import server.API;
import server.Database;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchServlet() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Attempts to get query for movies and for users
		String movieQuery = request.getParameter("movies");
		String userQuery = request.getParameter("users");

		boolean movieSearch = true;

		/*
		 *  Identifies whether it was a movie or user search
		 *  Proceeds to search accordingly
		 */
		if (movieQuery != null) {
			searchMovies(request, response, movieQuery);
		} else if (userQuery != null) {
			searchUsers(request, response, userQuery);
			movieSearch = false;
		}

		request.setAttribute("movieSearch", movieSearch);

		request.getRequestDispatcher("search.jsp").forward(request, response);
	}

	private void searchMovies(HttpServletRequest request, HttpServletResponse response, String movieQuery) 
			throws ServletException, IOException {
		ArrayList<String> matchingMovies = new ArrayList<String>();
		if (movieQuery!= null && !movieQuery.isEmpty()) {
			// Parses movieQuery for searchType and query
			int index = movieQuery.indexOf(':');
			String searchType = movieQuery.substring(0, index);
			String query = movieQuery.substring(index+1, movieQuery.length()).trim();

			/*
			 * Find movies that match the query
			 */
			boolean searchTitle = true;
			
			JSONObject apiResponse = null;

			switch(searchType) {
			case "actor": 
				searchTitle = false;
				apiResponse = API.searchTMDBActor(query);
				break;
			case "title":
				apiResponse = API.searchTMDBMovie(query);
				break;
			}
		
			JSONArray results = new JSONArray();
			try {
				if (searchTitle) {
					results = (JSONArray) apiResponse.get("results");
				} else {
					JSONArray actorResults = (JSONArray) apiResponse.get("results");
					JSONObject actor = (JSONObject) actorResults.get(0);
					results = (JSONArray) actor.get("known_for");
				}
				
				for (int i = 0; i < results.length(); i++) {
					JSONObject movie = (JSONObject) results.get(i);
					matchingMovies.add((String) movie.get("title"));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		request.setAttribute("searchResults", matchingMovies);
	}
	private void searchUsers(HttpServletRequest request, HttpServletResponse response, String userQuery) 
			throws ServletException, IOException {
		ArrayList<String> matchingUsers = new ArrayList<String>();

		String name = userQuery;
		if (name != null && !name.isEmpty()) {
			HttpSession session = request.getSession(true);
			Database db = null;
			
			try {
				db = (Database) session.getAttribute("database");
				if (db == null) {
					throw new Exception("session expired");
				}
			} catch (Exception e) {
				request.getRequestDispatcher(Constants.entryPage).forward(request, response);
				return; 
			}

			name = name.toLowerCase();
			
			ArrayList<User> users = new ArrayList<User>();
			User userUsername = db.getUserWithUsername(name, false);
			if (userUsername != null) {
				users.add(userUsername);
			}
			ArrayList<User> usersFname = db.getUsersWithFirstName(name, false);
			ArrayList<User> usersLname = db.getUsersWithLastName(name, false);
			users.addAll(usersFname);
			users.addAll(usersLname);
					
			for (User u : users) {
				String username = u.getUsername();
				if (!matchingUsers.contains(username)) {
					matchingUsers.add(username);
				}
			}
		}
		request.setAttribute("searchResults", matchingUsers);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 
		doGet(request, response);
	}
}
