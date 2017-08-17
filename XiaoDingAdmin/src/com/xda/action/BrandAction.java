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

import com.xda.entity.Brand;
import com.xda.service.BrandService;
import com.xda.utils.FileOperUtil;
import com.xda.utils.LogUtil;

@Controller
public class BrandAction implements ServletContextAware{
	
	private  String imgPath;
	private  String chSeq;
	
	@Resource
	private BrandService brandService;
	
	private ServletContext servletContext;

	 @Override
	 public void setServletContext(ServletContext context) {
		// TODO Auto-generated method stub
		servletContext = context;
	 }
	 
	 
	 public BrandService getBrandService() {
		return brandService;
	}


	public void setBrandService(BrandService brandService) {
		this.brandService = brandService;
	}
	
	
	public String getImgPath() {
		return imgPath;
	}

    @Value("#{propertiesReader[brand_imgPath]}")
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
	  * 澧炲姞鍝佺墝  鍥剧墖淇濆瓨鍦�./image/logo涓�
	  * @param name 鍝佺墝鍚嶇О
	  * @param file
	  * @return
	 * @throws InterruptedException 
	  */
	 @RequestMapping(value="addBrand", method=RequestMethod.POST)  
	 public @ResponseBody Map<String, String> addBrand(@RequestParam("name")String name, @RequestParam("file") MultipartFile file) 
	 {  
		 Map<String, String> retMap = new HashMap<String, String>();
		  if (!file.isEmpty()) {
	        	 // 鏂囦欢淇濆瓨璺緞  
	            String path = servletContext.getRealPath("brand.jsp");
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
					
					retMap.put("msg", "娣诲姞鍝佺墝[" + name + "]澶辫触锛侊紒锛�");
					return retMap;
				}
	            
	            String fileName = file.getOriginalFilename();
	            String fileType = fileName.substring(fileName.lastIndexOf("."));
	           // System.out.println(fileType);
	            String newFileName = new Date().getTime()+fileType;
	            System.out.println(newFileName);
	            File newFile = new File(path, newFileName);
	            try {  
	                //淇濆瓨鍒版暟鎹簱涓�
	                Brand b = new Brand();
	                b.setName(name);
	                b.setPicPath("../" + imgPath + "/" + newFileName);
	               if(brandService.addBrand(b)){
	            	   // 杞瓨鏂囦欢  
		               file.transferTo(newFile); 
	            	   retMap.put("msg", "娣诲姞鍝佺墝[" + name + "]鎴愬姛锛");
	               }else {
	            	   retMap.put("msg", "娣诲姞鍝佺墝澶辫触锛侊紒锛�鍝佺墝[" + name + "]宸插瓨鍦紒"); 
	               }
	            } catch (Exception e) {  
	                e.printStackTrace(); 
	                retMap.put("msg", "娣诲姞鍝佺墝[" + name + "]澶辫触锛侊紒锛�");
	            }
	           
	        }   
	        return retMap; 
	 }
	 
	 /**
	  * 鏍规嵁椤电爜鑾峰彇鍝佺墝淇℃伅
	  * @param pageNo
	  * @return
	  */
	@RequestMapping(value="listBrands", method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> getBrands(int pageNo, int pageSize)
    {
    	//System.out.println("PageNo is :" + pageNo);
		Map<String, Object> rets = new HashMap<String, Object>(4);
		int totalCount = brandService.getBrandCount();
		
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
    		List<Brand> labelList = brandService.getBrands(pageNo, pageSize);
    		rets.put("brandLists", labelList);
    	}
    	
    	return rets;
    }
	
	/**
	 * 鏍囩鍚�
	 * @param brandName
	 * @return
	 */
	@RequestMapping(value="searchBrand", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> searchBrands(String brandName)
    {
    	LogUtil.info("searchBrands::label name is " + brandName);
    	Map<String, Object> rets = new HashMap<String, Object>();
    	
    	List<Brand> bList = brandService.searchBrand(brandName);
		rets.put("brandLists", bList);
    	return rets;
    }
	
	@RequestMapping(value="updateBrand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateBrand(int id, String name, String picPath, @RequestParam("file") MultipartFile file)
	{
		LogUtil.info("updateBrand id: " + id + ", name: " + name + ", picPath: " + picPath);
		Map<String, String> retMap = new HashMap<String, String>();
	
		//鑾峰彇淇濆瓨鐨勮矾寰�
	    String path = servletContext.getRealPath("brand.jsp");
        int l = path.lastIndexOf(chSeq);
        path = path.substring(0, l);
        path = path.substring(0, path.lastIndexOf(chSeq));
       // LogUtil.info(path);
        try {
        	path = path + chSeq + imgPath;
			FileOperUtil.buildPath(path);//鍒ゆ柇璺緞鏄惁瀛樺湪锛屼笉瀛樺湪鍒欐柊寤�
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
			retMap.put("msg", "0");
			return retMap;
		}
        //鑾峰彇鏂囦欢鍚嶇О
        String picType = picPath.substring(picPath.lastIndexOf('.'));
        String newFileName = new Date().getTime() + picType;
        String saveName = "../" + imgPath + "/" + newFileName;
	    try{
	    	if(brandService.updateBrand(id, name, saveName))
	    	{
	    		file.transferTo(new File(path, newFileName));
	    		
	    		//鍒犻櫎涔嬪墠鐨勬枃浠�
	    		String oldFileName = picPath.substring(picPath.lastIndexOf('/') + 1);
	    		File oldFile = new File(path, oldFileName);
	    		if (oldFile.isFile() && oldFile.exists()) {  
	    			oldFile.delete();  
	    		}
	    		
	    		retMap.put("newPicPath", saveName);
	    		retMap.put("msg", "1");
	    	}
	    }catch(Exception e){
	    	LogUtil.info(e.getMessage());
	    	retMap.put("msg", "0");
	    }
	    
		return retMap;
	}
	
	@RequestMapping(value="updateBrandName", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateBrand(int id, String name, String picPath)
	{
		LogUtil.info("updateBrandName id: " + id + ", name: " + name + ", picPath: " + picPath);
		Map<String, String> retMap = new HashMap<String, String>();
		if(brandService.updateBrand(id, name, picPath))
    	{
    		retMap.put("msg", "淇敼鍝佺墝[" + name + "]鎴愬姛锛�");
    	}
		else{
			retMap.put("msg", "淇敼鍝佺墝[" + name + "]澶辫触锛侊紒锛�");
		}
		
		return retMap;
	}
	
	@RequestMapping(value="delBrand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> delBrand(int id, String name, String picPath)
	{
		LogUtil.info("delBrand id: " + id + ", picPath: " + picPath);
		Map<String, String> retMap = new HashMap<String, String>();
		if(brandService.deleteBrand(id))
    	{
			//鑾峰彇鍥剧墖璺緞
		    String path = servletContext.getRealPath("brand.jsp");
	        int l = path.lastIndexOf(chSeq);
	        path = path.substring(0, l);
	        path = path.substring(0, path.lastIndexOf(chSeq));
	        path = path + chSeq + imgPath;
			
	       //鍒犻櫎涔嬪墠鐨勬枃浠�
    		String oldFileName = picPath.substring(picPath.lastIndexOf('/') + 1);
    		File oldFile = new File(path, oldFileName);
    		if (oldFile.isFile() && oldFile.exists()) {  
    			oldFile.delete();  
    		}
			
    		retMap.put("msg", "1");
    	}
		else{
			retMap.put("msg", "0");
		}
		
		return retMap;
	}
}
