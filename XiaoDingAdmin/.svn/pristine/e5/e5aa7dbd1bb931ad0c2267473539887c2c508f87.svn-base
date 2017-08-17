package com.xda.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.xda.entity.Label;

@Service("labelService")
public class LabelService {
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public boolean addLabel(Label l)
	{
		int c = 0;
		//判断用户是否存在
		String sql = " select count(*) from label where NAME = ? ";
		int count = jdbcTemplate.queryForObject(sql, new Object[]{l.getName()}, Integer.class);
		if(count > 0)
		{
			return false;
		}
		
		sql = "insert into label(NAME) values (?) ";
		try{
			c = jdbcTemplate.update(sql, l.getName());
		}catch(Exception e)
		{
			c = -1;
			System.out.println(e.getMessage());
		}
		
		return c > 0;
	}
	
	/**
	 * 获取标签数量
	 * @return
	 */
	public int getLabelCount()
	{
		int c = 0;
		String sql = " select count(*) from label";
		c = jdbcTemplate.queryForObject(sql, Integer.class);
		
		return c;
	}
	
	public List<Label> getLabels(int pageNo, int pageSize)
	{
		 String sql = " select ID as id, NAME as name from label order by id asc limit " + (pageNo - 1)*pageSize + " , " + pageSize;
		
		return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Label.class));
	}
	
	/**
	 * 修改标签名
	 * @param labelName
	 * @param id 标签ID
	 * @return
	 */
	public boolean updateLabel(String labelName, int id)
	{
		int c = 0;
		String sql = "update label set NAME = ? where ID=? ";
		try{
			c = jdbcTemplate.update(sql, labelName, id);
		}catch(Exception e)
		{
			c = -1;
			System.out.println(e.getMessage());
		}
		
		return c > 0;
	}
	
	public boolean deleteLabel(int id)
	{
		int c = 0;
		String sql = "delete from label where id=? ";
		try{
			c = jdbcTemplate.update(sql, id);
		}catch(Exception e)
		{
			c = -1;
			System.out.println(e.getMessage());
		}
		
		return c > 0;
	}
	/**
	 * 根据标签名搜索标签，支持模糊搜索
	 * @param labelName
	 * @return
	 */
	public List<Label> searchLabel(String labelName)
	{
		 String sql = " select ID as id, NAME as name from label where NAME like '%" + labelName + "%'";
			
		return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Label.class));
	}
}
