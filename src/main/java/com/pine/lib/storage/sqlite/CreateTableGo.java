package com.pine.lib.storage.sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.support.v4.util.ArrayMap;

import com.pine.lib.func.debug.G;

public class CreateTableGo
{
	private static G g = new G(CreateTableGo.class);
	public String tableName = "";
	public SQLiteDatabase db;
	public Boolean isDrop = false;
	public ArrayMap<String, String> sql = new ArrayMap<String, String>();



	/**
	 * 构造函数
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @param tableName
	 *            要被创建的数据表的名字
	 * @param isDrop
	 *            如果存在 是否删除重新创建
	 */
	public CreateTableGo(String tableName, Boolean isDrop)
	{
		this(SqliteManager.i().getDb(), tableName, isDrop);
	}


	/**
	 * 构造函数
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @param db
	 *            请传入一个已经打开可写的数据库
	 * @param tableName
	 *            要被创建的数据表的名字
	 * @param isDrop
	 *            如果存在 是否删除重新创建
	 */
	public CreateTableGo(SQLiteDatabase db, String tableName, Boolean isDrop)
	{
		this.db = db;
		this.tableName = tableName;
		this.isDrop = isDrop;
	}


	public void addVarChar(String key, int stringLength)
	{
		sql.put(key, "varchar(" + stringLength + ")");
	}


	public void addText(String key)
	{
		sql.put(key, "text");
	}


	public void addInt(String key)
	{
		sql.put(key, "integer");
	}


	public void addBigInt(String key)
	{
		sql.put(key, "bigint");
	}


	public void addTimestamp(String key)
	{
		sql.put(key, "timestamp");
	}


	public void addIntPrimaryKey(String key, Boolean IsAutoIncrement)
	{
		if (IsAutoIncrement)
		{
			sql.put(key, "INTEGER PRIMARY KEY AUTOINCREMENT");
		}
		else
		{
			sql.put(key, "INTEGER PRIMARY KEY ");
		}
	}


	public void doNow()
	{
		if (isDrop)
		{
			String sqlString = "drop table if exists '" + tableName + "';";
			db.execSQL(sqlString);
			g.d(sqlString);
		}

		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("create table if not exists '" + tableName + "'( \n");

		for (int i = 0; i < sql.size(); i++)
		{
			String key = sql.keyAt(i);
			String value = sql.valueAt(i);
			sBuilder.append(key + " " + value);
			if (i < sql.size() - 1)
			{
				sBuilder.append(", \n");
			}
		}

		sBuilder.append(");");
		g.i("创建表Exec：" + sBuilder.toString());
		db.execSQL(sBuilder.toString());

	}

}
