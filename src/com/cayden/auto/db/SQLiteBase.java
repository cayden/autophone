/**
 * SQLiteBase.java
 * 版权所有(C) 2013 
 * 创建:cuiran 2013-7-5 下午5:33:31
 */
package com.cayden.auto.db;


import java.util.ArrayList;
import java.util.List;




import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * TODO
 * @author cuiran
 * @version TODO
 */
public class SQLiteBase extends SQLiteOpenHelper {
	public static final String TAG="SQLiteBase";
	
	public static String DATABASENAME = "testdb.db";
	
	public static String TABLENAME = "testspeed";
	
	public static String COLUMN_STR="column1";
	
	protected SQLiteDatabase database;
	
	private static SQLiteBase _instance = null;
	private static Context context = null;
	
	public SQLiteBase(Context context){
		
		super(context, DATABASENAME, null, 1);
		
		database = this.getWritableDatabase();
	}
	
	public synchronized static SQLiteBase getInstance(Context _context) {
		if (null == _instance || null == context) {
			_instance = new SQLiteBase(_context);
		}
		return _instance;
	}
	
	public void addDb(String str){
		
		try{
	
			database.beginTransaction(); 
			String execSQL="insert into "+TABLENAME +" ("+COLUMN_STR+") values ('1234567890abcdefghijlmnopqrst')";
			for(int i=0;i<100000;i++){
				database.execSQL(execSQL);
			}
			database.setTransactionSuccessful();
			
			database.endTransaction();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	public List<String> queryAll(){
		List<String> list=new ArrayList<String>();
		
		String querySql="select * from "+TABLENAME+" ";
		Cursor cursor=null;
		cursor=database.rawQuery(querySql, null);
		
		if(cursor==null){
			
			return list;
		}
		
		if(cursor.getCount()<=0){
			
			return list;
		}
		
		cursor.moveToFirst();
		
		for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
			
			int ArticleIdIndex=cursor.getColumnIndex(COLUMN_STR);
			list.add(cursor.getString(ArticleIdIndex));
		
		}
		
		return list;
		
		
	}
	/**
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	public SQLiteBase(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String dbSQL="create table if not exists "+TABLENAME+ " (_id"
				+ " integer primary key autoincrement not null," 
				+ COLUMN_STR+" varchar )";
		db.execSQL(dbSQL);
	}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
