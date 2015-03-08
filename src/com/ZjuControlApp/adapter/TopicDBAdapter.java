package com.ZjuControlApp.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TopicDBAdapter {
	//	关于这一部分，必须注意sqlite的主键命名，由于simpleCursorAdapter的方法只识别_id，
	//	所以，当你用到sqlite的simpleCursorAdapter时，必须把数据表的主键命名为_id。
	//	否则就会出现java.lang.IllegalArgumentException: column ‘_id’ does not exist错误。
	private static final String tag="TopicDBAdapter";
	
	//定义数据库常量
	private String databaseName;
	private String tableName;
	//定义字段
	public static final String key_id="_id";
	public static final String key_title="title";
	public static final String key_setting="setting";
	public static final String key_info="info";
	
	//定义数据库版本
	private static final int database_version=1;
	
	private DatabaseHelper mdbhelper;
	private SQLiteDatabase mdb;
	
	private Context mcxt;
	
	public TopicDBAdapter(Context cxt, String tableN)
	{
		this.mcxt=cxt;
		tableName = tableN;
		databaseName = tableN;
	}
	
	public class DatabaseHelper extends SQLiteOpenHelper{

		public DatabaseHelper(Context context) {
			super(context, databaseName, null, database_version);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			//创建数据表
			String sql="create table "
				   +tableName+"(_id integer primary key autoincrement,"
				   +key_title+" text not null,"
				   +key_setting+" text not null,"
				   +key_info+" text not null);";
			Log.i(tag,"sql="+sql);
			db.execSQL(sql);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("drop table if exists "+tableName);
			onCreate(db);
		}
	}
	
	public TopicDBAdapter open()
	{
		mdbhelper=new DatabaseHelper(mcxt);
		mdb=mdbhelper.getWritableDatabase();
		return this;
	}
	
	public long insert(String title,String info)
	{
		mdb=mdbhelper.getWritableDatabase();
		// Calendar calendar=Calendar.getInstance();
		// String info=calendar.get(Calendar.YEAR)+"年"+calendar.get(Calendar.MONTH+1)+"月"+calendar.get(Calendar.DAY_OF_MONTH)+"日"+calendar.get(Calendar.HOUR)+"时"+calendar.get(Calendar.MINUTE)+"分";
        ContentValues values=new ContentValues();
        values.put(key_title,title);
        values.put(key_setting,"Setting");
        values.put(key_info,info);
		long rowId=mdb.insert(tableName,null,values);
		return rowId;
	}
	
	public void update(long rowid,String title,String info)
	{
		mdb=mdbhelper.getWritableDatabase();
		//Calendar calendar=Calendar.getInstance();
		//String info=calendar.get(Calendar.YEAR)+"年"+calendar.get(Calendar.MONTH+1)+"月"+calendar.get(Calendar.DAY_OF_MONTH)+"日"+calendar.get(Calendar.HOUR)+"时"+calendar.get(Calendar.MINUTE)+"分";
		ContentValues values=new ContentValues();
		values.put(key_title,title);
	    values.put(key_setting,"Setting");
	    values.put(key_info,info);
	    mdb.update(tableName,values,key_id+"="+rowid,null);
	}
	
	public Cursor queryALl()
	{
        mdb=mdbhelper.getReadableDatabase();
		Cursor cursor=mdb.query(tableName,new String[]{key_id,key_title,key_setting,key_info},
				null,
				null,null,null,null);
	    return cursor;
	}
	
	public Cursor queryById(long rowId)
	{
	   mdb=mdbhelper.getReadableDatabase();
	   Cursor cursor=mdb.query(tableName,new String[]{key_id,key_title,key_setting,key_info},
			   key_id+"="+rowId,
			   null,null,null,null);
	   return cursor;
	}
	
	public Cursor queryByTitle(String title)
	{
		mdb=mdbhelper.getReadableDatabase();
		Cursor cursor = mdb.rawQuery("SELECT * FROM "+ tableName +" WHERE title=?", 
				new String[]{ title});

		return cursor;
	}
	
	public void delById(long rowId)
	{
		mdb=mdbhelper.getWritableDatabase();
		mdb.delete(tableName,key_id+"="+rowId,null);
	}

}
