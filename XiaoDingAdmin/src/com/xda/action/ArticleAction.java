package com.xda.action;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.xda.entity.Memo;
import com.xda.service.ArticleService;
import com.xda.utils.FileOperUtil;
import com.xda.utils.LogUtil;

@Controller
public class ArticleAction implements ServletContextAware{
	
	@Resource
	private ArticleService articleService;
	private ServletContext servletContext;
	
	private  String imgPath;
	private  String chSeq;
	
	public ArticleService getArticleService() {
		return articleService;
	}

	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}
	
	 @Override
	 public void setServletContext(ServletContext context) {
		// TODO Auto-generated method stub
		servletContext = context;
	 }


	@RequestMapping(value="listMemoes", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getMemoes(int pageNo)
	{
		Map<String, Object> rets = new HashMap<String, Object>(4);
		int pageSize = 9;
		int totalCount = articleService.getArticleCount();
		
		//System.out.println("totalCount is " + totalCount);
		int totalPages = totalCount / pageSize;
    	if(totalCount % pageSize != 0)
    	{
    		totalPages++;
    	}
    		
    	rets.put("totalPages", totalPages);
    	rets.put("currentPage", pageNo);
    	rets.put("sizeOfPage", pageSize);
    	
    	if(pageNo <= totalPages)
    	{
    		List<Memo> memoList =  articleService.getMemo(pageNo, pageSize);
    		rets.put("memoLists", memoList);
    	}
    	
    	return rets;
	}
	
	@RequestMapping(value="gotoArticle", method = RequestMethod.GET)
	public ModelAndView gotoArticle()
	{
		ModelAndView mv = new ModelAndView("addArticle");
		
		Long uId = articleService.genUniqueNum();
		mv.addObject("id", uId);
		
		return mv;
	}
	
	public String getImgPath() {
		return imgPath;
	}

    @Value("#{propertiesReader[food_imgPath]}")
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
    
    


	public String getChSeq() {
		return chSeq;
	}

	@Value("#{propertiesReader[separator]}")
	public void setChSeq(String chSeq) {
		this.chSeq = chSeq;
	}


	/**
	  * 澧炲姞鍝佺墝  鍥剧墖淇濆瓨鍦�./image/food涓�
	  * @param name 鍝佺墝鍚嶇О
	  * @param file
	  * @return
	 * @throws InterruptedException 
	  */
	 @RequestMapping(value="loadFoodImage", method=RequestMethod.POST)  
	 public @ResponseBody Map<String, String> loadFoodImage(@RequestParam("id")int id, @RequestParam("file") MultipartFile file) 
	 {  
		  LogUtil.info("loadFoodImage: id = " + id);
		 
		  Map<String, String> retMap = new HashMap<String, String>();
		  if (!file.isEmpty()) {
	        	 // 鏂囦欢淇濆瓨璺緞  
	            String path = servletContext.getRealPath("index.jsp");
	            int l = path.lastIndexOf(chSeq);
	            path = path.substring(0, l);
	            path = path.substring(0, path.lastIndexOf(chSeq));
	            LogUtil.info(path);
	            try {
	            	path = path + chSeq + imgPath;
					FileOperUtil.buildPath(path);//鍒ゆ柇璺緞鏄惁瀛樺湪锛屼笉瀛樺湪鍒欐柊寤�
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					
					retMap.put("msg", "涓婁紶鍥剧墖澶辫触锛侊紒锛�");
					return retMap;
				}
	            
	            String fileName = file.getOriginalFilename();
	            String fileType = fileName.substring(fileName.lastIndexOf("."));
	           // System.out.println(fileType);
	            String newFileName = new Date().getTime()+fileType;
	            System.out.println(newFileName);
	            File newFile = new File(path, newFileName);
	            try {  
	            	String strPath = "../" + imgPath + "/" + newFileName;
	            	if(articleService.savePic(id, strPath)){
	            		file.transferTo(newFile); 
	            		 retMap.put("msg", "涓婁紶鎴愬姛锛侊紒锛�");
	            	}
	            } catch (Exception e) {  
	                e.printStackTrace(); 
	                retMap.put("msg", "涓婁紶澶辫触锛侊紒锛�");
	            }
	           
	        }   
	        return retMap; 
	 }
	 
}
