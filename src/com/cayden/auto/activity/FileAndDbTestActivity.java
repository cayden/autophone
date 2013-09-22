/**
 * FileTestActivity.java
 * 版权所有(C) 2013 
 * 创建:cuiran 2013-7-5 下午4:36:02
 */
package com.cayden.auto.activity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.http.util.EncodingUtils;

import com.cayden.auto.R;
import com.cayden.auto.db.SQLiteBase;
import com.cayden.auto.util.FileUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * TODO 测试读写文件和读写数据库的速度
 * @author cuiran
 * @version TODO
 */
public class FileAndDbTestActivity extends Activity implements OnClickListener {
	private Button btn_write,btn_read;
	private TextView test_result;
	private static String FILE_PATH="/sdcard/testwrite/testwrite.txt";
	
	/**
	 * 0 测试文件 1 测试数据库
	 */
    private String testFlag="0";
	  @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.layout_test);
	        
	        initView();
	        
	        Intent intent=this.getIntent();
	        testFlag=intent.getExtras().getString("flag");
	        
	  }

	/**
	 *<b>function:</b>
	 *@author cuiran
	 *@createDate 2013-7-5 下午4:50:07
	 */
	private void initView() {
		// TODO Auto-generated method stub
		btn_write=(Button)findViewById(R.id.button_write);
		btn_read=(Button)findViewById(R.id.button_read);
		test_result=(TextView)findViewById(R.id.test_result);
		
		
		btn_write.setOnClickListener(this);
		btn_read.setOnClickListener(this);
	}
	/**
	 * 
	 *<b>function:</b>测试写入
	 *@author cuiran
	 *@createDate 2013-7-5 下午4:54:28
	 */
	public void testWrite(){
		if("0".equals(testFlag)){
			testWriteFile();
		}else{
			testWriteDb();
		}
	}
	
	private String initData(){
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<100000;i++){
			sb.append("1234567890abcdefghijlmnopqrst\n");
		}
		return sb.toString();
	}
	
	public void testWriteFile(){
		try {
			new FileUtils().createDir("testwrite");
			long startTime=System.currentTimeMillis();
			writeFileSdcardFile(FILE_PATH, initData());
			long endTime=System.currentTimeMillis();
			long ms=endTime-startTime;
			test_result.setText("写入时间为:"+ms+" ms");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void testWriteDb(){
		long startTime=System.currentTimeMillis();
		
		
		SQLiteBase.getInstance(this).addDb("1234567890abcdefghijlmnopqrst");
		
		
		long endTime=System.currentTimeMillis();
		long ms=endTime-startTime;
		test_result.setText("写入时间为:"+ms+" ms");
	}
	
	//写数据到SD中的文件
	public void writeFileSdcardFile(String fileName,String write_str) throws IOException{ 
	 try{ 

	       FileOutputStream fout = new FileOutputStream(fileName); 
	       byte [] bytes = write_str.getBytes(); 

	       fout.write(bytes); 
	       fout.close(); 
	     }

	      catch(Exception e){ 
	        e.printStackTrace(); 
	       } 
	   } 

	//读SD中的文件
	public String readFileSdcardFile(String fileName) throws IOException{ 
	  String res=""; 
	  try{ 
	         FileInputStream fin = new FileInputStream(fileName); 

	         int length = fin.available(); 

	         byte [] buffer = new byte[length]; 
	         fin.read(buffer);     
	         res = EncodingUtils.getString(buffer, "UTF-8"); 

	         fin.close();     
	        } 

	        catch(Exception e){ 
	         e.printStackTrace(); 
	        } 
	        return res; 
	} 
	/**
	 * 
	 *<b>function:</b>测试读出
	 *@author cuiran
	 *@createDate 2013-7-5 下午4:54:28
	 */
	public void testRead(){
		if("0".equals(testFlag)){
			testReadFile();
		}else{
			testReadDb();
		}
		
	}
	
	

	/**
	 *<b>function:</b>
	 *@author cuiran
	 *@createDate 2013-7-5 下午5:20:25
	 */
	private void testReadDb() {
		// TODO Auto-generated method stub
		long startTime=System.currentTimeMillis();
		List<String> list= SQLiteBase.getInstance(this).queryAll();
		
		long endTime=System.currentTimeMillis();
		long ms=endTime-startTime;
		test_result.setText("读出时间为:"+ms+" ms size="+list.size());
	}

	/**
	 *<b>function:</b>
	 *@author cuiran
	 *@createDate 2013-7-5 下午5:20:22
	 */
	private void testReadFile() {
		// TODO Auto-generated method stub
		try {
			long startTime=System.currentTimeMillis();
			String str=readFileSdcardFile(FILE_PATH);
			long endTime=System.currentTimeMillis();
			long ms=endTime-startTime;
			test_result.setText("读出时间为:"+ms+" ms");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.button_write:
			testWrite();
			break;
		case R.id.button_read:
			testRead();
			break;
		
		}
	}
}
