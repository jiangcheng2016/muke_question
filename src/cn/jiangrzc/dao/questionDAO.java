package cn.jiangrzc.dao;

import java.sql.ResultSet;

import cn.jiangrzc.model.DbUtils;

public class questionDAO {
	
	DbUtils dbUtils = new DbUtils();
	
	//通过问题查询
	public ResultSet getAnswer(String question) {
		String sql = "SELECT a.*,b.answer FROM questions a LEFT JOIN answer b ON a.questionid = b.questionid WHERE a.question like \"%\" ? \"%\" ;";
		/*String sql = "select answer from answer where questionid in (select questionid from questions where question like \"%\" ? \"%\");";*/
		Object [] param = {question};
		ResultSet rs = null;
		try {
			 rs = dbUtils.executeQuery(sql, param);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rs;
		
	}
}
