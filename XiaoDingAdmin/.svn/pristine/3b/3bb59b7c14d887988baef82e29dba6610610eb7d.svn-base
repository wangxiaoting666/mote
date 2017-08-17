package com.xda.utils;

import java.io.File;

public class FileOperUtil {
	

	/**
	 * 判断目录是否存在，不存在新建目录
	 * @param path 文件的路径
	 * @return
	 * @throws InterruptedException
	 */
	public static  boolean buildPath(String path)throws InterruptedException
	{
		 String [] paths=path.split("/");
		 StringBuffer fullPath=new StringBuffer();
		 for (int i = 0; i < paths.length; i++) 
		 {
			fullPath.append(paths[i]).append("/");
			File file=new File(fullPath.toString());
			if(!file.exists())
			{
				file.mkdir();
				LogUtil.info("创建目录为：" + fullPath.toString());
				//Thread.sleep(1500);
		   }
	    }
		File file=new File(fullPath.toString());//目录全路径
		if (!file.exists()) 
		{
			return true;
		}
		else
		{
			return false;
		}
	}

}
