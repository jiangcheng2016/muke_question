package cn.jiangrzc.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.jiangrzc.dao.questionDAO;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class question
 */
@WebServlet("/question")
public class question extends HttpServlet {
	private static final long serialVersionUID = 1L;
	questionDAO questionDAO = new questionDAO();
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public question() {
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//1.设置编码及类型
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		//2.获取相关参数
		String question = request.getParameter("infodata");
		//3.根据问题查询questionid anwser
		ResultSet rs = questionDAO.getAnswer(question);
		//System.out.println(rs);
		String answer = null;
		String question_select = null;
		try {
			while(rs.next()) {
				question_select = rs.getString("question");
				answer = rs.getString("answer");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (question_select == null) {
			question_select = "未查到此题目！";
		}
		
		if (answer == null) {

			answer = "1.题目只需复制题干即可,不需要选项和问号等多余符号\r\n" +"<br />"
					+ "2.数据不完整，查询结果为空!";
		}
		//System.out.println(answer);
		
		//4.将question,answer转化为jsonp 数据格式
		Map<String, String> map = new HashMap<String,String>();
		map.put("question", question_select);
		map.put("answer", answer);
		JSONObject jsonObject = new JSONObject().fromObject(map);
		String jsonpCallback = request.getParameter("jsonpCallback");
		//response 传值给ajax 
		PrintWriter data = response.getWriter();
		System.out.println(data);
		data.println(jsonpCallback+"("+jsonObject.toString(1,1)+")");//返回jsonp格式数据 
		System.out.println(jsonpCallback+"("+jsonObject.toString(1,1)+")");
		//data.write(jsonObject.toString());
		data.flush();
		data.close();
	}

}
