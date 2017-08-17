package com.xda.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xda.entity.Admin;
import com.xda.service.AdminService;

@Controller
public class AdminAction {
  
	@Resource
	private AdminService adminService;

	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}
	
	@RequestMapping(value="userAdd", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> addOneAdmin(String userName, String password)
	{
		System.out.println("UserName is : " + userName + "  Password is: " + password);
		Admin admin = new Admin();
		admin.setUsername(userName);
		admin.setPassword(password);
		Map<String, String> retMap = new HashMap<String, String>();
		if(adminService.addAdmin(admin))
		{
			retMap.put("msg", "<" + userName + ">");
		}else{
			retMap.put("msg", "Failed!");
		}
		
		return retMap;
	}
	
	@RequestMapping(value="listUsers", method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> getAdmins(int pageNo)
    {
		//System.out.println("PageNo is :" + pageNo);
		Map<String, Object> rets = new HashMap<String, Object>(4);
		int pageSize = 5;
		int totalCount = adminService.getAdminCount();
		
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
    		List<Admin> adminList = adminService.getAdmins(pageNo, pageSize);
    		rets.put("adminLists", adminList);
    	}
    	
    	return rets;
    }
	
	@RequestMapping(value="userUpdate", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> updateOneAdmin(String userName, String password)
	{
		System.out.println("update admin, username is " + userName + ", password is " + password);
		Map<String, String> retMap = new HashMap<String, String>();
	   if(adminService.updateAdmin(userName, password))
	   {
		   retMap.put("msg", "淇敼鐢ㄦ埛瀵嗙爜<" + userName + ">");
	   }else
	   {
		   retMap.put("msg", "淇敼鐢ㄦ埛瀵嗙爜<" + userName + ">");
	   }
	   return retMap;
	}
	
	@RequestMapping(value="userDel", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> delOneAdmin(String userName)
	{
		System.out.println("Del one admin, username is " + userName);
		Map<String, String> retMap = new HashMap<String, String>();
	   if(adminService.deleteAdmin(userName))
	   {
		   retMap.put("msg", "鍒犻櫎鐢ㄦ埛<" + userName + ">鎴愬姛锛�");
	   }else
	   {
		   retMap.put("msg", "鍒犻櫎鐢ㄦ埛<" + userName + ">澶辫触锛侊紒锛�");
	   }
	   return retMap;
	}
	
}
