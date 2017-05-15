package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.User;
import server.Database;

/**
 * Servlet implementation class SignInServlet
 */
@WebServlet("/SignInServlet")
public class SignInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignInServlet() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		PrintWriter pw = response.getWriter();

		// establish connection to database
		Database db = null;

		try {
			db = (Database) session.getAttribute("database");			
			if (db == null) {
				throw new Exception("session expired");
			}
		} catch (Exception e) {
			pw.print("ERROR: Session expired");
			pw.close();
			return;
		}

		// get username/password input
		String usernameInput = request.getParameter("username");
		String passwordInput = request.getParameter("password");

		// check if username/password is empty
		if ((usernameInput == null || usernameInput.isEmpty()) 
				|| (passwordInput == null || passwordInput.isEmpty())) {
			pw.print("ERROR: Please enter a username and a password");
			pw.close();
			return;
		} 
		else { 
			boolean validUsername = false;
			boolean validPassword = false;

			// check if username exists
			User u = db.getUserWithUsername(usernameInput, true);
			
			if (u != null) {
				validUsername = true;
				if (passwordInput.equals(u.getPassword())) {
					validPassword = true;
				} else {
					// invalid password
					pw.println("ERROR: Invalid username and password combination");
					pw.close();
					return;
				}
			} else {
				//username doesn't exist
				pw.print("ERROR: No account associated with that username");
				pw.close();
				return;
			}

			if (validUsername && validPassword) {
				User currentUser = db.getUserWithUsername(usernameInput, true);
				session.setAttribute("currentUser", currentUser);
				session.setAttribute("currentUsername", usernameInput);
			}
		}
		pw.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
