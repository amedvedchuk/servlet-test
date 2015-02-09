import java.io.FileReader;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FormLogin extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 371257235501122091L;

	public static String USER_KEY = "ServletLogin.user";

	public static String FIELD_USER = "username";

	public static String FIELD_PASSWORD = "password";

	public static String LOGGED_BY_EXT_KEY = "is_log_ext";

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, java.io.IOException {
		resp.setContentType("text/html");
		java.io.PrintWriter out = resp.getWriter();
		resp.setHeader("Expires", "Tues, 01 Jan 1980 00:00:00 GMT");
		String uri = req.getRequestURI();

		HttpSession session = req.getSession();
		String user = getUserFromSessionOrExt(req);

		System.out.println("doGet: IP = " + req.getRemoteAddr()
				+ " user from session = " + user + " params = "
				+ req.getParameterMap());

		if (user == null) {
			login(out, uri);
			return;
		}

		if ("Y".equals(session.getAttribute(LOGGED_BY_EXT_KEY))) {
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Welcome, " + user + "</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<center><h2>Welcome to our site! You was authenticated by external system!!</h2>");
			out.println("</center><br><br>");
			out.println("<h1>your IP is: " + req.getRemoteAddr() + "</h1>");
			out.println("<h1>your user is: " + user + "</h1>");
			
			out.println("<br><form method=POST action=\"" + uri + "\">");
			out.println("<input type=submit value=\"Logout\" name=\"logout\">");
			out.println("</form>");

			
			out.println("</body>");
			out.println("</html>");
			out.flush();
		} else {
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Welcome</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<center><h2>Welcome to our site!</h2>");
			out.println("</center><br><br>");
			out.println("<h1>your IP is: " + req.getRemoteAddr() + "</h1>");
			out.println("<h1>your user is: " + user + "</h1>");

			// out.println("<button name=\"Logout\" />");

			out.println("<br><form method=POST action=\"" + uri + "\">");
			out.println("<input type=submit value=\"Logout\" name=\"logout\">");
			out.println("</form>");

			out.println("</body>");
			out.println("</html>");
			out.flush();
		}

	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, java.io.IOException {

		resp.setContentType("text/html");
		java.io.PrintWriter out = resp.getWriter();
		HttpSession session = req.getSession(true);
		String user = getUserFromSessionOrExt(req);

		System.out.println("doPost: IP = " + req.getRemoteAddr()
				+ " user from session = " + user + " params = "
				+ req.getParameterMap());

		if (req.getParameter("logout") != null) {
			// logout(out, req.getRequestURI());
			session.removeAttribute(USER_KEY);
		} else if (user == null) {
			String username = req.getParameter(FIELD_USER);
			String password = req.getParameter(FIELD_PASSWORD);

			System.out.println("doPost: user from form: " + username);

			if (!validUser(username, password)) {
				out.println("<html>");
				out.println("<title>Invalid User</title>");
				out.println("<body><center><h2>" + "Invalid User!</h2><br>");
				out.println("Press the 'Back' button to try again");
				out.println("</center></body></html>");
				out.flush();
				return;
			}
			session.setAttribute(USER_KEY, username);
		}
		resp.sendRedirect(req.getRequestURI());
	}

	protected void login(java.io.PrintWriter out, String uri)
			throws java.io.IOException {

		System.out.println("login: do login! uri = " + uri);
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Login</title>");
		out.println("<center><h2>Welcome! Please login</h2>");
		out.println("<br><form method=POST action=\"" + uri + "\">");
		out.println("<table>");
		out.println("<tr><td>User ID:</td>");
		out.println("<td><input type=text name=" + FIELD_USER
				+ " size=30></td></tr>");
		out.println("<tr><td>Password:</td>");
		out.println("<td><input type=password name=" + FIELD_PASSWORD
				+ " size=10></td></tr>");
		out.println("</table><br>");
		out.println("<input type=submit value=\"Login\">");
		out.println("</form></center></body></html>");
	}


	protected boolean validUser(String username, String password) {
		boolean valid = false;
		if ((username != null) && (username.length() > 0)) {
			valid = username.equals(password);
		}

		System.out.println("validuser: " + valid);

		return valid;
	}

	String getUserFromSessionOrExt(HttpServletRequest req) {

		HttpSession session = req.getSession(true);
		String user = (String) session.getAttribute(USER_KEY);

		if (user == null) {
			Properties properties = new Properties();
			try {
				properties
						.load(new FileReader(
								"C:\\Users\\amedvedchuk\\workspace\\SimplestLoginInServlet\\users.properties"));
				System.out.println("session cashe: " + properties);
				user = properties.getProperty(req.getRemoteAddr());
				if (user != null) {
					session.setAttribute(LOGGED_BY_EXT_KEY, "Y");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return user;
	}
}
