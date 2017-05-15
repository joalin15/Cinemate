package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.User;
import helper.Constants;
import server.Database;


/**
 * Servlet implementation class ProfileServlet
 */
@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /** 
     * @see HttpServlet#HttpServlet()
     */
    public ProfileServlet() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		
		String username = request.getParameter("name");
				
		User user = new User();
		User currentUser = new User();
		Database db = null;
		boolean foreignUser = false;
		 
		try {  
			currentUser = (User) session.getAttribute("currentUser");
			db = (Database) session.getAttribute("database");
			if (currentUser == null || db == null) {
				throw new Exception("session expired");
			}
		} catch (Exception e) {
			request.getRequestDispatcher(Constants.entryPage).forward(request, response);
			return; 
		} 
		
		
		if (currentUser.getUsername().equals(username)) {
			user = currentUser;
		} else {
			//page of foreign user
			user = db.getUserWithUsername(username, true);
			foreignUser = true;	
		}
		
		boolean followingStatus = false;
		
		if (foreignUser) {
			ArrayList<String> following = db.getFollowingOfUsername(currentUser.getUsername());
			for (String u : following) {
				if (u.equals(username)) {
					followingStatus = true;
				}
			}
		}
						
		request.setAttribute("name", user.getName());
		request.setAttribute("username", user.getUsername());
		request.setAttribute("profilePicture", user.getImage()); 
		request.setAttribute("following", db.getFollowingOfUsername(user.getUsername()));
		request.setAttribute("followers", db.getFollowersOfUsername(user.getUsername()));
		request.setAttribute("feed", db.getEventsOfUsername(user.getUsername()));
		request.setAttribute("foreignUser", foreignUser);
		request.setAttribute("followingStatus", followingStatus);
				
		request.getRequestDispatcher("profile.jsp").forward(request, response);
	}  

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 
		doGet(request, response);
	}

}
