package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import servlet.utils.UserInfoXML;

/**
 * Servlet implementation class SignUpServlet
 */
@WebServlet(urlPatterns={"/SignUpServlet","/servlet/RegisterServlet"})
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String username;
	private String password;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUpServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>"+this.getClass().getSimpleName()+"</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method<br>");
		out.println("Served at: "+request.getContextPath());
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// format input
		InputStreamReader inputReader =new InputStreamReader(request.getInputStream());
		BufferedReader bufferedReader = new BufferedReader(inputReader);
		StringBuffer stringBuffer = new StringBuffer();
		String lineString;
		while ((lineString= bufferedReader.readLine())!=null) {
			stringBuffer.append(lineString);
		}
		System.out.println("input string : "+stringBuffer);
		JSONObject inputObject = JSONObject.fromObject(stringBuffer.toString());
		System.out.println("input JSONObject : "+inputObject);

		username = inputObject.getString("username");
		password = inputObject.getString("password");
	
		boolean isRepeat = UserInfoXML.getInstance().checkUserRepeat(username);
		String result = "";
		
		if (!isRepeat) {
			if (UserInfoXML.getInstance().addUser(username, password) > 0) {
				result = "success";
			} else {
				result = "fail";
			}
		} else {
			result = "repeat";
		}
				
		// write response
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.println(result);
		out.flush();
		out.close();
	}

}
