package com.xda.utils;

import java.io.Serializable;
import java.util.List;

public class ResultFilter<T> implements Serializable
{
	private static final long serialVersionUID = 5472321653620726832L;  
	private final static int DEFAULT_NAVIGATOR_SIZE = 5;  
	  
    //褰撳墠椤� 
    private int currentPage = 1;  
    //姣忛〉鏄剧ず鏁伴噺  
    private int pageSize = 5;  
      
    //鎬绘潯鏁� 
    private int totalCount;  
  
    private boolean haveNextPage = false;  
  
    private boolean havePrePage = false;  
  
    private int navigatorSize;  
      
    //瀛樻斁鏌ヨ缁撴灉鐢ㄧ殑list  
    private List<T> items;  
    
    public ResultFilter(){
    	
    }
    
    public ResultFilter(int totalCount, int pageSize, int currentPage) {  
        this(totalCount, pageSize, currentPage, DEFAULT_NAVIGATOR_SIZE);  
    }  
  
    public ResultFilter(int totalCount, int pageSize, int currentPage,  
                        int navigatorSize) {  
        this.totalCount = totalCount;  
        this.pageSize = pageSize;  
        this.currentPage = currentPage;  
        this.navigatorSize = navigatorSize;  
    }  
    /*
     * 鑾峰彇鎬婚〉鐮
     */
    public int getPageCount()
    {
    	int pageCount = 0;
    	if(pageSize > 0)
    	{
    		pageCount = totalCount / pageSize;
    		if(totalCount % pageSize != 0)
    			pageCount++;
    	}
    	return pageCount;
    }
    
    public int getCurrentPage()
    {
    	int pageCount = getPageCount();
    	
    	currentPage = currentPage < pageCount ? currentPage : pageCount;  
		currentPage = currentPage < 1 ? 1 : currentPage;  
		
		return currentPage; 
    }
    
    public int getPageSize(){
    	
    	return pageSize;
    }
    
    public int getTotalCount()
    {
    	return totalCount;
    }
    
    public boolean isHaveNextPage()
    {
    	haveNextPage = false;
    	int pageCount = getPageCount();
        if ((pageCount > 1) && (pageCount > getCurrentPage()))  
              haveNextPage = true;  
        
        return haveNextPage;  
    }
    
    public boolean isHavePrePage()
    {
    	 havePrePage = false;  
         if ((getPageCount() > 1) && (currentPage > 1))  
             havePrePage = true;  
         return havePrePage;  
    }
    
    private int getNavigatorIndex(boolean isBegin)
    {
    	int pageCount = this.getPageCount();
    	int beginNavigatorIndex = getCurrentPage() - navigatorSize / 2;  
    	int endNavigatorIndex = getCurrentPage() + navigatorSize / 2;
    	beginNavigatorIndex = beginNavigatorIndex < 1 ? 1 : beginNavigatorIndex;  
        endNavigatorIndex = endNavigatorIndex < pageCount ? endNavigatorIndex : pageCount;
        
        while ((endNavigatorIndex - beginNavigatorIndex) < navigatorSize &&  
                (beginNavigatorIndex != 1 || endNavigatorIndex != pageCount)) {  
             if (beginNavigatorIndex > 1)  
                 beginNavigatorIndex--;  
             else if (endNavigatorIndex < pageCount)  
                 endNavigatorIndex++;  
         }  
        
        if(isBegin)  
            return beginNavigatorIndex;  
        else  
            return endNavigatorIndex;                      
    }
    
    public int getBeginNavigatorIndex() {  
        return getNavigatorIndex(true);  
    }  
  
    public int getEndNavigatorIndex() {  
        return getNavigatorIndex(false);  
    }  
    
    public List<T> getItems() {  
        return items;  
    }  
  
    public void setItems(List<T> items) {  
        this.items = items;  
    }  
      
    public void setCurrentPage(int currentPage) {  
        this.currentPage = currentPage;  
    }  
  
    public void setPageSize(int pageSize) {  
        this.pageSize = pageSize;  
    }  
  
    public void setTotalCount(int totalCount) {  
        this.totalCount = totalCount;  
    }   
  
}
