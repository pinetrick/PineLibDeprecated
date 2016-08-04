package com.pine.lib.storage.sqlite;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.pine.lib.func.debug.G;

public class TableOpr
{
	private static G g = new G(TableOpr.class);



	public void create(Class<?> className)
	{
		CreateTableGo createTableGo = new CreateTableGo(
				className.getSimpleName(), false);

		Field[] fields = className.getDeclaredFields();

		// String[] names = new String[fields.length];

		for (int i = 0; i < fields.length; i++)
		{
			Field f = fields[i];
			f.setAccessible(true);
			String key = f.getName();

			if (key.equals("id"))
			{
				g.d("主键 - " + key);
				createTableGo.addIntPrimaryKey(key, true);
			}
			else if (f.getType().equals(String.class))
			{
				g.d("String - " + key);
				createTableGo.addText(key);
			}
			else if (f.getType().equals(int.class))
			{

				g.d("Integer - " + key);
				createTableGo.addInt(key);
			}
			else if (f.getType().equals(long.class))
			{

				g.d("BigInt - " + key);
				createTableGo.addBigInt(key);
			}

		}
		createTableGo.doNow();
	}


	public void update(SqliteBeanInterface obj)
	{

		// /根据对象的类型来确定数据表
		String table = obj.getClass().getSimpleName();
		// /根据类的成员变量来确定要查询那些列
		Field[] fields = obj.getClass().getDeclaredFields();
		String[] columns = new String[fields.length];
		// /初始化列名
		for (int i = 0; i < columns.length; i++)
		{
			try
			{
				fields[i].setAccessible(true);
				columns[i] = fields[i].getName();

				if (columns[i].equals("id")) continue;
				if (obj.getId() <= -1) continue;

				String sqlString = "update `" + table + "` set `" + columns[i]
						+ "`='" + sqlEncode(String.valueOf(fields[i].get(obj)))
						+ "' where `id`=" + obj.getId();
				//g.d("sqlString = " + sqlString);

				SQLiteDatabase db = SqliteManager.i().getReadableDatabase();
				db.execSQL(sqlString);
			}
			catch (Exception e)
			{
				if (!e.toString().contains("no such column"))
				{
					g.e("更新异常" + e.toString());
				}
				
			}
		}

	}

	public String sqlEncode(String s)
	{
		s = s.replace("'", "[单引号]");
		s = s.replace("%", "[百分号]");
		return s;
	}
	
	public String sqlDecode(String s)
	{
		s = s.replace("[单引号]", "'");
		s = s.replace("[百分号]", "%");
		return s;
	}

	public void del(SqliteBeanInterface obj)
	{
		try
		{
			// /根据对象的类型来确定数据表
			String table = obj.getClass().getSimpleName();

			String sqlString = "delete from `" + table + "` where `id`="
					+ obj.getId();
			// g.d("sqlString = " + sqlString);

			SQLiteDatabase db = SqliteManager.i().getReadableDatabase();
			db.execSQL(sqlString);

		}
		catch (Exception e)
		{
			g.e("删除异常" + e.toString());
		}

	}


	public List<Object> select(String sql, Class<?> cls)
	{
		try
		{
			Field[] fields = cls.getDeclaredFields();
			String[] names = new String[fields.length];
			for (int i = 0; i < names.length; i++)
			{
				Field f = fields[i];
				f.setAccessible(true);
				fields[i].setAccessible(true);
				names[i] = f.getName();
			}

			String tableName = cls.getSimpleName();
			SQLiteDatabase db = SqliteManager.i().getReadableDatabase();
			// String[] selectionArgs = new String[] { id };
			String sqlString = sql;
			// g.d("sqlString = " + sqlString);

			Cursor cursor = db.rawQuery(sqlString, null);
			// tableName, names, colName + "=?",
			// selectionArgs, null, null, null);

			List<Object> rList = new ArrayList<Object>();
			while (cursor.moveToNext())
			{
				Object entity = cls.newInstance();
				for (Field f : fields)
				{
					f.setAccessible(true);
					// /用反射来调用相应的方法
					// /先构造出方法的名字

					String typeName = f.getType().getSimpleName();
					int columnId = cursor.getColumnIndex(f.getName());
					// Object retValue = null;
					if (typeName.equals("String"))
					{
						String retValue = sqlDecode(cursor.getString(columnId));
						f.set(entity, retValue);
					}
					else if (typeName.equals("int"))
					{
						int retValue = cursor.getInt(columnId);
						f.set(entity, retValue);
					}
					else if (typeName.equals("long"))
					{
						long retValue = cursor.getInt(columnId);
						f.set(entity, retValue);
					}

					// // /int --> Int,doble--->Double
					// typeName = typeName.substring(0, 1).toUpperCase()
					// + typeName.substring(1);
					// // /cuosor 的方法的名字
					// String methodName = "get" + typeName;
					// // /得到方法
					// Method method = cursor.getClass().getMethod(methodName,
					// int.class);
					// Object retValue = method.invoke(cursor,
					// cursor.getColumnIndex(f.getName()));

					// f.set(entity, cursor.)
				}
				rList.add(entity);
			}
			return rList;
		}
		catch (Exception e)
		{
			g.e("查询异常！" + e.toString());
		}

		return new ArrayList<Object>();
	}


	public List<Object> select(String id, String colName, Class<?> cls,
			Boolean isIncrease)
	{

		Field[] fields = cls.getDeclaredFields();
		String[] names = new String[fields.length];
		for (int i = 0; i < names.length; i++)
		{
			Field f = fields[i];
			f.setAccessible(true);
			fields[i].setAccessible(true);
			names[i] = f.getName();
		}

		String tableName = cls.getSimpleName();
		SQLiteDatabase db = SqliteManager.i().getReadableDatabase();
		// String[] selectionArgs = new String[] { id };
		String sqlString = "select * from `" + tableName + "` where `"
				+ colName + "`='" + id + "' order by id";
		if (!isIncrease)
		{
			sqlString += " desc";
		}
		return select(sqlString, cls);
	}


	public void insert(SqliteBeanInterface obj)
	{
		// /根据对象的类型来确定数据表
		String table = obj.getClass().getSimpleName();
		// /根据类的成员变量来确定要查询那些列
		Field[] fields = obj.getClass().getDeclaredFields();
		String[] columns = new String[fields.length];
		// /初始化列名
		for (int i = 0; i < columns.length; i++)
		{
			fields[i].setAccessible(true);
			columns[i] = fields[i].getName();
		}

		SQLiteDatabase db = SqliteManager.i().getWritableDatabase();

		try
		{
			ContentValues val = new ContentValues();
			for (int j = 0; j < columns.length; j++)
			{
				if (fields[j].getName().equals("id")) continue;
				Class<?> type = fields[j].getType();
				if (type == double.class)
				{
					double value = fields[j].getDouble(obj);
					val.put(columns[j], value);
				}
				else if (type == int.class)
				{
					int value = fields[j].getInt(obj);
					val.put(columns[j], value);
				}
				else if (type == String.class)
				{
					String value = sqlEncode(String.valueOf(fields[j].get(obj)));
					val.put(columns[j], value);
				}
				else
				{
					g.w("未知类型，将不会持久化：" + type);
				}
			}

			db.insert(table, null, val);
			g.d("插入了一条记录到表 - " + table);
		}
		catch (Exception e)
		{
			g.e("数据库插入异常");
		}

	}

}
