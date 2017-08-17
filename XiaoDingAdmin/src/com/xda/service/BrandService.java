package com.xda.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.xda.entity.Brand;
import com.xda.utils.LogUtil;

@Service("brandService")
public class BrandService {
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public boolean addBrand(Brand b)
	{
		int c = 0;
		//判断用户是否存在
		String sql = " select count(*) from brand where NAME = ? ";
		int count = jdbcTemplate.queryForObject(sql, new Object[]{b.getName()}, Integer.class);
		if(count > 0)
		{
			return false;
		}
		
		sql = "insert into brand(NAME, PIC_PATH) values (?,?) ";
		try{
			c = jdbcTemplate.update(sql, b.getName(), b.getPicPath());
		}catch(Exception e)
		{
			c = -1;
			System.out.println(e.getMessage());
		}
		
		return c > 0;
	}
	
	public List<Brand> getBrands(int pageNo, int pageSize)
	{
		 String sql = " select ID as id, NAME as name, PIC_PATH as picPath from brand order by id asc limit " + (pageNo - 1)*pageSize + " , " + pageSize;
		
		return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Brand.class));
	}
	
	/**
	 * 获取品牌数量
	 * @return
	 */
	public int getBrandCount()
	{
		int c = 0;
		String sql = " select count(*) from Brand";
		c = jdbcTemplate.queryForObject(sql, Integer.class);
		
		return c;
	}
	
	/**
	 * 根据品牌名称搜索品牌，支持模糊搜索
	 * @param brandName
	 * @return
	 */
	public List<Brand> searchBrand(String brandName)
	{
		 String sql = " select ID as id, NAME as name, PIC_PATH as picPath from brand where NAME like '%" + brandName + "%'";
			
		return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Brand.class));
	}
	
	/**
	 * 修改品牌
	 * @param id
	 * @param newName 品牌名称
	 * @return
	 */
	public boolean updateBrand(int id, String newName, String newPicPath)
	{
		int c = 0;
		String sql = "update brand set NAME = ?, PIC_PATH=? where ID=? ";
		try{
			c = jdbcTemplate.update(sql, newName, newPicPath, id);
		}catch(Exception e)
		{
			c = -1;
			LogUtil.error(e.getMessage());
		}
		
		return c > 0;
	}
	
	/**
	 * 删除品牌记录
	 * @param id
	 * @return
	 */
	public boolean deleteBrand(int id)
	{
		int c = 0;
		String sql = "delete from brand where id=? ";
		try{
			c = jdbcTemplate.update(sql, id);
		}catch(Exception e)
		{
			c = -1;
			System.out.println(e.getMessage());
		}
		
		return c > 0;
	}
}
