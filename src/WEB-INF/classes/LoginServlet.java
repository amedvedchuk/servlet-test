import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginServlet implements Servlet {

    public void init(ServletConfig config) throws ServletException {
    }

    public void service(ServletRequest request, ServletResponse response)
        throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.println("You are logged in.");
    }

    public void destroy() {
    }

    public String getServletInfo() {
        return null;
    }

    public ServletConfig getServletConfig() {
        return null;
    }

}
