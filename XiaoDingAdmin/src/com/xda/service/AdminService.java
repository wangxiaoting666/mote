package com.xda.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.xda.entity.Admin;

@Service("adminService")
public class AdminService {
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public AdminService()
	{
		
	}
	
	public boolean addAdmin(Admin admin)
	{
		int c = 0;
		//判断用户是否存在
		String sql = " select count(*) from admin where USER_NAME = ? ";
		int count = jdbcTemplate.queryForObject(sql, new Object[]{admin.getUsername()}, Integer.class);
		if(count > 0)
		{
			return false;
		}
		
		sql = "insert into admin(USER_NAME, PASSWORD) values (?,?) ";
		try{
			c = jdbcTemplate.update(sql, admin.getUsername(), admin.getPassword());
		}catch(Exception e)
		{
			c = -1;
			System.out.println(e.getMessage());
		}
		
		return c > 0;
	}
	/**
	 * 获取用户数量
	 * @return
	 */
	public int getAdminCount()
	{
		int c = 0;
		String sql = " select count(*) from admin";
		c = jdbcTemplate.queryForObject(sql, Integer.class);
		
		return c;
	}
	
	public List<Admin> getAdmins(int pageNo, int pageSize)
	{
		 String sql = " select a.USER_NAME as username, a.PASSWORD as password from admin a  limit " + (pageNo - 1)*pageSize + " , " + pageSize;
		
		return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Admin.class));
	}
	/**
	 * 修改用户的密码
	 * @param userName 用户名 ：唯一字段
	 * @param newPass 新密码
	 * @return
	 */
	public boolean updateAdmin(String userName, String newPass)
	{
		int c = 0;
		String sql = "update admin set PASSWORD = ? where USER_NAME=? ";
		try{
			c = jdbcTemplate.update(sql, newPass, userName);
		}catch(Exception e)
		{
			c = -1;
			System.out.println(e.getMessage());
		}
		
		return c > 0;
	}
	
	public boolean deleteAdmin(String userName)
	{
		int c = 0;
		String sql = "delete from admin where USER_NAME=? ";
		try{
			c = jdbcTemplate.update(sql, userName);
		}catch(Exception e)
		{
			c = -1;
			System.out.println(e.getMessage());
		}
		
		return c > 0;
	}
}
