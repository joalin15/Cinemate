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

import data.User;
import server.Database;

/**
 * Servlet implementation class SignUpServlet
 */
@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUpServlet() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
	    PrintWriter pw = response.getWriter();   
	    
	    response.setContentType("text/javascript");
	    response.setCharacterEncoding("UTF-8");
	    response.setHeader("Cache-Control", "no-cache");
	    
	    String name = request.getParameter("name"); 
	    String fname = parseFirstName(name);
	    String lname = parseLastName(name);
		String usernameInput = request.getParameter("username");
		String passwordInput = request.getParameter("password");
		String image = request.getParameter("image");
	    
		// check if form is incomplete
		if ((name == null || name.isEmpty())
				|| (usernameInput == null || usernameInput.isEmpty()
				|| (passwordInput == null || passwordInput.isEmpty())
				|| (image == null || image.isEmpty()))) {
			pw.print("ERROR: All required fields were not provided");
			pw.close();
			return;
		} 
		else {
			// establish connection to database
			Database db = null;

			try {
				db = (Database) session.getAttribute("database");			
				if (db == null) {
					throw new Exception("session expired");
				}
			} catch (Exception e) {
				pw.print("Session Expired! Please enter an input file again");
				pw.close();
				return;
			}
			
			boolean userExists = false;
			
			//insert user into database;
			if (db.getUserWithUsername(usernameInput, true) == null) {
				try {
					db.insertUser(usernameInput, passwordInput, fname, lname, image);
				} catch (SQLException e) {
					userExists = true;
				}
			}
			
			if (userExists) {
				// username already exists
				pw.println("ERROR: Username has already been taken");
				pw.close();
				return;
			}
			
			User currentUser = new User(name, usernameInput, passwordInput, image);
			
			session.setAttribute("currentUser", currentUser);
			session.setAttribute("currentUsername", usernameInput);
		}
		pw.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 
		doGet(request, response);
	}
	
	/**
	 * HELPER FUNCTIONS
	 */
	private String parseFirstName(String name) {
		int indexName = name.indexOf(' ');
		if (indexName != -1) {
			String first = name.substring(0, indexName).trim();
			return first;
		} else {
			return name;
		}
	}
	private String parseLastName(String name) {
		int indexName = name.indexOf(' ');
		if (indexName != -1) {
			String last = name.substring(indexName+1, name.length()).trim();
			return last;
		} else {
			return ""; // no last name
		}
	}

}
