package org.test.struts.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.test.struts.dao.IUserDAO;

import freemarker.template.Template;

@SuppressWarnings("unused")
public class UserDAO implements IUserDAO {
	private JdbcTemplate jdbcTemplate;
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	@Override
	public boolean checkUser(String username, String password) {
		// TODO Auto-generated method stub
		System.out.println("dao starting access to Database");
		String sql = "select count(*) from user where username=? and password=? ";
		@SuppressWarnings("deprecation")
		int count = jdbcTemplate.queryForInt(sql, new Object[] { username,password });

		return count>0;
	}
	@Override
	public int signUp(String userName, String password) {
		System.out.println("dao for SignUp");
		String sql = "INSERT user (username,password) VALUES(?,?)";
		System.out.println("before intert sql = "+sql);
		int cout = jdbcTemplate.update(sql, new Object[] {userName,password});
		System.out.println("insert done : count = "+cout);
		return cout;
	}
}
