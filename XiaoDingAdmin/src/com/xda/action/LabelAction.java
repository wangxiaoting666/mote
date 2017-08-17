package com.xda.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xda.entity.Label;
import com.xda.service.LabelService;

@Controller
public class LabelAction {
	
	@Resource
	private LabelService labelService;

	public LabelService getLabelService() {
		return labelService;
	}

	public void setLabelService(LabelService labelService) {
		this.labelService = labelService;
	}
	
    public LabelAction(){
    	
    }
    
    @RequestMapping(value="labelAdd", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> addOneLabel(String labelName)
	{
		System.out.println("addOneLabel::LabelName is : " + labelName);
		Label l = new Label();
		l.setName(labelName);
		Map<String, String> retMap = new HashMap<String, String>();
		if(labelService.addLabel(l))
		{
			retMap.put("msg", "娣诲姞鏍囩<" + labelName + ">鎴愬姛锛�");
		}else{
			retMap.put("msg", "娣诲姞鐢ㄦ埛澶辫触锛侊紒锛�");
		}
		
		return retMap;
	}
    /**
     * 鍒嗛〉鑾峰彇鏍囩鏁�
     * @param pageNo 椤电爜
     * @return
     */
    @RequestMapping(value="listLabels", method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> getLabels(int pageNo, int pageSize)
    {
    	//System.out.println("PageNo is :" + pageNo);
		Map<String, Object> rets = new HashMap<String, Object>(4);
		int totalCount = labelService.getLabelCount();
		
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
    		List<Label> labelList = labelService.getLabels(pageNo, pageSize);
    		rets.put("labelLists", labelList);
    	}
    	
    	return rets;
    }
    
    @RequestMapping(value="labelUpdate", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> updateOneLabel(String labelName, int id)
	{
		System.out.println("update label, name is " + labelName + ", id is " + id);
		Map<String, String> retMap = new HashMap<String, String>();
	   if(labelService.updateLabel(labelName, id))
	   {
		   retMap.put("msg", "淇敼鎴愬姛锛�");
	   }else
	   {
		   retMap.put("msg", "淇敼澶辫触锛侊紒锛�");
	   }
	   return retMap;
	}
    
    @RequestMapping(value="labelDel", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> delOneAdmin(int id)
	{
		System.out.println("Del one label, id is " + id);
		Map<String, String> retMap = new HashMap<String, String>();
	   if(labelService.deleteLabel(id))
	   {
		   retMap.put("msg", "鍒犻櫎鎴愬姛锛�");
	   }else
	   {
		   retMap.put("msg", "鍒犻櫎澶辫触锛侊紒锛�");
	   }
	   return retMap;
	}
    /**
     * 鎼滅储鏍囩
     * @param labelName 鏍囩鍚�
     * @return
     */
    @RequestMapping(value="searchLabel", method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> searchLabels(String labelName)
    {
    	System.out.println("searchLabels::label name is " + labelName);
    	Map<String, Object> rets = new HashMap<String, Object>();
        if(labelName == null || labelName.length() == 0){
    		return rets;
    	}
    	
    	List<Label> labelList = labelService.searchLabel(labelName);
		rets.put("labelLists", labelList);
    	return rets;
    }
}
