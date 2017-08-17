package com.xda.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.xda.entity.Memo;

@Service("articleService")
public class ArticleService 
{
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 *获取文章的简要信息
	 * @return
	 */
	public List<Memo> getMemo(int pageNo, int pageSize){
		
		String sql = "select  f.ID as foodId, i.ID as picId, f.RELEASE_DATE as date, f.NAME as title, i.THUMB_PATH as picPath, f.MEMO as summary "
				+ " from image i, food f where i.FOOD_ID = f.ID and i.SHOW_FIRST = 1 limit " + (pageNo - 1)*pageSize + " , " + pageSize;;
		
		return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Memo.class));
	}
	
	public int getArticleCount()
	{
		int c = 0;
		String sql = "select count(*) from food";
		c = jdbcTemplate.queryForObject(sql, Integer.class);
		
		return c;
	}
	
	public Long genUniqueNum()
	{
		Long uId = System.currentTimeMillis();
		String sql = "insert into food(NAME) values(?)";
		int c = jdbcTemplate.update(sql, uId);
		if(c > 0)
		{
			sql = "select ID from food where NAME = ?";
			return jdbcTemplate.queryForObject(sql, Long.class, new Object[]{uId});
		}
		return -1L;
	}
	
	public boolean savePic(Integer id, String strPicPath)
	{
		int c = 0;
		String sql = "insert into image(THUMB_PATH, IMAGE_PATH, FOOD_ID, SHOW_FIRST) values (?, ?, ?, 0)";
		try{
			c = jdbcTemplate.update(sql, strPicPath, strPicPath, id);
		}catch(Exception e)
		{
			c = -1;
		}
		return c > 0;
	}
}
