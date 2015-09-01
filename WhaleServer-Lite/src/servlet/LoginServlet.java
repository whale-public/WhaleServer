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
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	private String username;
	private String password;
	
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
		
		int isValid = checkAccountValid(username,password);
		
		// write response
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String result = null;
		switch (isValid) {
		case 1:
			result = "valid";
			break;
		case 0:
			result = "NoUser";
			break;
		case -1:
			result = "invalid";
			break;
		default:
			break;
		}
		out.println(result);
		out.flush();
		out.close();
	}

	/**
	 * @param uname
	 * @param psw
	 * @return 1 means valid
	 * 		   	0 means uername does not exist
	 * 			-1 means invalid, password does not match username
	 */
	private int checkAccountValid(String uname, String psw) {
		
		return UserInfoXML.getInstance().checkUser(uname, psw);
	}

}
