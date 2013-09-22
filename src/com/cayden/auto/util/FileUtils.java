/**
 * FileUtils.java
 * 版权所有(C) 2013 
 * 创建:cuiran 2013-5-10 下午3:47:54
 */
package com.cayden.auto.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import android.os.Environment;
import android.util.Log;

/**
 * TODO
 * @author cuiran
 * @version TODO
 */
public class FileUtils {
	
	private String SDPATH=null;      
	public String getSDPATH()    
	{          
		return SDPATH;     
   }  
	public FileUtils(){
		SDPATH=Environment.getExternalStorageDirectory()+"/";
		
	}
	
	public static void copyFile(File fromFile,File toFile,boolean rewrite){
		if (!fromFile.exists()) {

			return;

		}
		if (!fromFile.isFile()) {

			return;
		}
		
		if (!fromFile.canRead()) {

			return ;

		}

		if (!toFile.getParentFile().exists()) {

			toFile.getParentFile().mkdirs();
			
		}

		if (toFile.exists() && rewrite) {

			toFile.delete();

		}
			try {

				java.io.FileInputStream fosfrom = new java.io.FileInputStream(fromFile);

				java.io.FileOutputStream fosto = new FileOutputStream(toFile);

				byte bt[] = new byte[1024];

					int c;

					while ((c = fosfrom.read(bt)) > 0) {
						fosto.write(bt, 0, c); //将内容写到新文件当中
					}

					fosfrom.close();

					fosto.close();

			} catch (Exception ex) {

				Log.e("readfile", ex.getMessage());

			}



	}
	
	//创建目录      
	public File createDir(String fileName) throws IOException      {  
		File dir=new File(SDPATH+fileName);         
		
		dir.mkdir();       
		
		return dir;     
		
	}  
	
	//创建子目录      
	public File createDir(String parentDir,String fileName) throws IOException      {  
		File dir=new File(parentDir+fileName);         
		
		dir.mkdir();       
		
		return dir;     
		
	}  
	public File createFile(String fileName)throws IOException{
		
		File file=new File(SDPATH+fileName);
		file.createNewFile();
		
		return file;
	}
	
	public File createChatFile(String fileName)throws IOException{
		
		File file=new File(fileName);
		file.createNewFile();
		
		return file;
	}
	
	 //判断文件是否存在  
	public boolean isExist(String fileName)   
	{       
		File file=new File(SDPATH+fileName);      
		return file.exists();  
	
	}  
	
	public File writeTo(String path,String filename,InputStream inputStream){
		File file=null;
		
		OutputStream outputStream=null;
		
		try{
			createFile(path);
			
			file=createFile(path+filename);
			
			outputStream=new FileOutputStream(file);  
			byte buffer[]=new byte[1024];  
			 //将输入流中的内容先输入到buffer中缓存，然后用输出流写到文件中
			 while((inputStream.read(buffer))!=-1)  {
				 outputStream.write(buffer);  
			 }
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				outputStream.close();
			}catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return file;
	}
	

	
	public File writeToSdcard(String filePath,InputStream inputStream){
		File file=null;
		
		OutputStream outputStream=null;
		try{
			file=createFile(filePath);
			
			outputStream=new FileOutputStream(file);  
			
			byte buffer[] = new byte[128];
			// 将输入流中的内容先输入到buffer中缓存，然后用输出流写到文件中
			int currentNumber = 0;
			do {
			int length = (inputStream.read(buffer));
		
				if (length != -1) {
					currentNumber+=length;
					outputStream.write(buffer, 0, length);
					
				} 
				else {
					break;
				}
			} while (true);

			
//			byte buffer[]=new byte[1024];  
//			 //将输入流中的内容先输入到buffer中缓存，然后用输出流写到文件中
//			 while((inputStream.read(buffer))!=-1)  {
//				 outputStream.write(buffer);  
//			 }
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				outputStream.close();
			}catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return file;
	}
	
	public File writeToSdcardChat(String filePath,InputStream inputStream){
		File file=null;
		
		OutputStream outputStream=null;
		try{
			file=createChatFile(filePath);
			
			outputStream=new FileOutputStream(file);  
			
			byte buffer[] = new byte[128];
			// 将输入流中的内容先输入到buffer中缓存，然后用输出流写到文件中
			int currentNumber = 0;
			do {
			int length = (inputStream.read(buffer));
		
				if (length != -1) {
					currentNumber+=length;
					outputStream.write(buffer, 0, length);
					
				} 
				else {
					break;
				}
			} while (true);

			
//			byte buffer[]=new byte[1024];  
//			 //将输入流中的内容先输入到buffer中缓存，然后用输出流写到文件中
//			 while((inputStream.read(buffer))!=-1)  {
//				 outputStream.write(buffer);  
//			 }
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				outputStream.close();
			}catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return file;
	}
	
	
}
