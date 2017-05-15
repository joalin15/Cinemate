package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.FeedEvent;
import helper.Constants;
import server.Database;

/**
 * Servlet implementation class FeedServlet
 */
@WebServlet("/FeedServlet")
public class FeedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
        
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FeedServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		String currentUsername = null;
		Database db = null;

		try {
			currentUsername = (String) session.getAttribute("currentUsername");
			db = (Database) session.getAttribute("database");
			if (db == null || currentUsername == null) {
				throw new Exception("session expired");
			}
		} catch (Exception e) {
			request.getRequestDispatcher(Constants.entryPage).forward(request, response);
			return; 
		}  
		
		ArrayList<FeedEvent> feed = new ArrayList<FeedEvent>();
		ArrayList<String> usernamesFollowing = db.getFollowingOfUsername(currentUsername);
		
		for (String username : usernamesFollowing) {
			ArrayList<FeedEvent> uEvents = db.getEventsOfUsername(username);
			for (FeedEvent event : uEvents) {
				feed.add(event);
			}
		}
		
		if (feed.size() > 0) {
			quicksortFeedByDate(0, feed.size()-1, feed);
		}
		
		request.setAttribute("feed", feed);
		   
		request.getRequestDispatcher("feed.jsp").forward(request, response);
	}
	
	private void quicksortFeedByDate(int low, int high, ArrayList<FeedEvent> feed) {
	        int i = low, j = high;
	        
	        Date pivot = feed.get(low + (high-low)/2).getTimestamp();
	
	        while (i <= j) {
	                while (feed.get(i).getTimestamp().getTime() < pivot.getTime()) {
	                        i++;
	                }
	                while (feed.get(j).getTimestamp().getTime() > pivot.getTime()) {
	                        j--;
	                }
	                if (i <= j) {
	                        exchange(i, j, feed);
	                        i++;
	                        j--;
	                }
	        }
	        if (low < j)
	        	quicksortFeedByDate(low, j, feed);
	        if (i < high)
	        	quicksortFeedByDate(i, high, feed);
	}
	
	private void exchange(int i, int j, ArrayList<FeedEvent> feed) {
	        FeedEvent temp = feed.get(i);
	        feed.set(i, feed.get(j));
	        feed.set(j, temp);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
