package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helper.Constants;
import server.Database;

/**
 * Servlet implementation class FollowServlet
 */
@WebServlet("/FollowServlet")
public class FollowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		String followingStatus = request.getParameter("status");
		String foreignUsername = request.getParameter("user");
		
		HttpSession session = request.getSession();
		String currentUsername = "";
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
				
		if (followingStatus.equals("Follow")) {
			db.insertFollowRelationship(currentUsername, foreignUsername);
		} else if (followingStatus.equals("Unfollow")) {
			db.removeFollowRelationhip(currentUsername, foreignUsername);
		}
		  
		ArrayList<String> foreignFollowers = db.getFollowersOfUsername(foreignUsername);
		pw.print("<h3>FOLLOWERS</h3>");
		pw.print("<ul>");
		for (String follower : foreignFollowers) {
			pw.println("<a class=\"specificList\" href=\"ProfileServlet?name="
					+ follower
					+ "\"><p>" 
					+ follower
					+ "</p></li>");
		}
		pw.print("</ul>");
		
		pw.close();

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 
		doGet(request, response);
	}

}
