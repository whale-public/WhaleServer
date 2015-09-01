package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import servlet.utils.NotificationGetPostUtil;

/**
 * Servlet implementation class PushNotificationServlet
 */
@WebServlet("/PushNotificationServlet")
public class PushNotificationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ArrayList<String> userList = new ArrayList<String>();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PushNotificationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// format input
		InputStreamReader inputReader =new InputStreamReader(request.getInputStream());
		BufferedReader bufferedReader = new BufferedReader(inputReader);
		StringBuffer stringBuffer = new StringBuffer();
		String lineString;
		while ((lineString= bufferedReader.readLine())!=null) {
			stringBuffer.append(lineString);
		}
		stringBuffer.append("[\"8d367c28537a40e49d090d8ab883d898\",\"io22222\"]");
		System.out.println("input string : "+stringBuffer);
		JSONArray inputArray = JSONArray.fromObject(stringBuffer.toString());
		System.out.println("input JSONObject : "+inputArray);
		
		userList = (ArrayList<String>) JSONArray.toCollection(inputArray);
		System.out.println("userList.size = "+userList.size());
		if (userList.size() > 0) {
			System.out.println(", get(0) = "+userList.get(0));
		}
		
		sendToUsers(userList);
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
		JSONArray inputArray = JSONArray.fromObject(stringBuffer.toString());
		System.out.println("input JSONObject : "+inputArray);
		
		userList = (ArrayList<String>) JSONArray.toCollection(inputArray);
		System.out.println("userList.size = "+userList.size()+", get(0) = "+userList.get(0));
		
		sendToUsers(userList);
		
		// write response
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.println("OK");
		out.flush();
		out.close();
	}

	private void sendToUsers(ArrayList<String> users) {
		String u1 = users.get(0);
		System.out.println("sendToUsers : user1 = "+u1);
		if (!u1.equals("")) {
			// 3.用POST方式发送
			/*--拼接POST字符串--*/
			final StringBuilder parameter = new StringBuilder();
			parameter.append("action=send&broadcast=N&username="); // 单条发送这里要设成N,若设成Y则广播,全部收到,后面参数无效
			parameter.append(u1);
			parameter.append("&title=androidPN test&message=");
			parameter.append("POST I love the world!");
			parameter.append("&uri=");
			parameter.append(""); // 和推送给iPhone格式保持一致
			/*--End--*/
			 String resp = NotificationGetPostUtil.send("POST", NotificationGetPostUtil.NOTIFICATION_SERVER + "notification_api.do", parameter);
			 System.out.println("response:" + resp);
		}
	}
}
