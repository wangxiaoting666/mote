package com.xda.action;


import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import com.xda.utils.LogUtil;

@Controller
public class UploadFileAction implements ServletContextAware {
	
	private ServletContext servletContext;

	 @Override
	public void setServletContext(ServletContext context) {
		// TODO Auto-generated method stub
		servletContext = context;
	}

	/*** 
     * 上传文件 用@RequestParam注解来指定表单上的file为MultipartFile 
     *  
     * @param file 
     * @return 
     */  
    @RequestMapping(value="fileUpload", method=RequestMethod.POST)  
    public @ResponseBody Map<String, Object> fileUpload(String name, @RequestParam("file") MultipartFile file) {  
       LogUtil.info("name is" + name);
       Map<String, Object> retMap = new HashMap<String, Object>();
    	// 判断文件是否为空  
        if (!file.isEmpty()) { 
        	 // 文件保存路径  
            String path = servletContext.getRealPath("/tmp/");
            LogUtil.info(path);
            String fileName = file.getOriginalFilename();
            String fileType = fileName.substring(fileName.lastIndexOf("."));
           // System.out.println(fileType);
            String newFileName = new Date().getTime()+fileType;
            System.out.println(newFileName);
            File newFile = new File(path, newFileName);
            String str = String.format("%s\\%s", path, newFileName);
            retMap.put("picName", str);
            LogUtil.info("file name is " + str);
            try {  
            	 // 转存文件  
                file.transferTo(newFile); 
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
       
        // 重定向  
        return retMap;  
    }  
}
