package com.pine.lib.storage.sqlite;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.pine.lib.base.activity.A;
import com.pine.lib.func.debug.G;
import com.pine.lib.func.debug.M;

/**
 * 数据库操作类
 * 
 * <pre>
 * 请用SqliteManager.i("dbName.db")构造
 * </pre>
 */
public class SqliteManager extends SQLiteOpenHelper
{
	private static final int VERSION = 1;
	private static SqliteManager sqliteManager = null;
	private static Object locker = new Object();
	private static G g = new G(SqliteManager.class);

	private SQLiteDatabase db;



	/**
	 * 创建表操作 不存在则创建 存在什么都不做
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @param className
	 * @return
	 */
	public SqliteManager createTable(Class<?> className)
	{
		new TableOpr().create(className);
		return this;
	}


	public SqliteManager insert(SqliteBeanInterface obj, Class<?> className)
	{
		new TableOpr().insert(obj);
		List<Object> l = new TableOpr().select(
				"select * from `" + className.getSimpleName()
						+ "` order by `id` desc", className);
		if (l.size() > 0)
		{
			obj.setId(((SqliteBeanInterface)l.get(0)).getId());
		}
		return this;
	}


	public SqliteManager del(SqliteBeanInterface obj)
	{
		new TableOpr().del(obj);
		return this;
	}


	/**
	 * 执行查询语句
	 * 
	 * @return
	 */
	public List<Object> select(String id, String colName, Class cls,
			Boolean isIncrease)
	{
		return new TableOpr().select(id, colName, cls, isIncrease);

	}


	/**
	 * 执行查询语句
	 * 
	 * @return
	 */
	public List<Object> select(String id, String colName, Class cls)
	{
		return select(id, colName, cls, true);

	}


	/**
	 * 执行查询语句
	 * 
	 * @return
	 */
	public List<Object> select(String sql, Class cls)
	{
		return new TableOpr().select(sql, cls);

	}


	/**
	 * 执行更新语句
	 * 
	 * @param sql
	 * @return
	 */
	public void update(SqliteBeanInterface obj)
	{
		new TableOpr().update(obj);

	}


	@Override
	public void onCreate(SQLiteDatabase arg0)
	{
		// TODO Auto-generated method stub

	}


	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2)
	{
		// TODO Auto-generated method stub

	}


	private SqliteManager(Context context, String dbName,
			CursorFactory factory, int version)
	{
		super(context, dbName, factory, version);
		M.i().addClass(this);
		db = context.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
	}


	private SqliteManager(Context context, String dbName)
	{
		this(context, dbName, null, VERSION);
		
	}


	/***
	 * 用暴力反射来把数据存入数据库
	 * 
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	private void updateArray(List list) throws IllegalArgumentException,
			IllegalAccessException
	{
		if (list == null || list.size() == 0)
		{
			return;
		}

		Object obj = list.get(0);
		Log.i("updateArray(List)", list.size() + "--"
				+ obj.getClass().getCanonicalName());
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

		SQLiteDatabase db = getWritableDatabase();
		db.delete(table, null, null);

		for (int i = 0; i < list.size(); i++)
		{
			Object o = list.get(i);
			ContentValues val = new ContentValues();
			for (int j = 0; j < columns.length; j++)
			{
				Class<?> type = fields[j].getType();
				if (type == double.class)
				{
					double value = fields[j].getDouble(o);
					val.put(columns[j], value);
				}
				else if (type == int.class)
				{
					int value = fields[j].getInt(o);
					val.put(columns[j], value);
				}
				else if (type == long.class)
				{
					long value = fields[j].getLong(o);
					val.put(columns[j], value);
				}
				else
				{
					String value = String.valueOf(fields[j].get(o));
					val.put(columns[j], value);
				}
			}

			db.insert(table, null, val);
		}
	}


	/***
	 * 获取指定类型的所有的数据
	 * 
	 * @param cls
	 * @return
	 */
	private Object getArrays(Class cls)
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
		List entities = new ArrayList();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db
				.query(tableName, names, null, null, null, null, null);

		try
		{

			while (cursor.moveToNext())
			{
				Object entity = cls.newInstance();
				for (Field f : fields)
				{
					f.setAccessible(true);
					// /用反射来调用相应的方法
					// /先构造出方法的名字
					String typeName = f.getType().getSimpleName();
					// /int --> Int,doble--->Double
					typeName = typeName.substring(0, 1).toUpperCase()
							+ typeName.substring(1);
					// /cuosor 的方法的名字
					String methodName = "get" + typeName;
					// /得到方法
					Method method = cursor.getClass().getMethod(methodName,
							int.class);
					Object retValue = method.invoke(cursor,
							cursor.getColumnIndex(f.getName()));
					f.set(entity, retValue);
					// f.set(entity, cursor.)
				}
				entities.add(entity);
			}

		}
		catch (NullPointerException ex)
		{

		}
		catch (SecurityException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (NoSuchMethodException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InstantiationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			if (cursor != null)
			{
				cursor.close();
			}
			if (db != null)
			{
				db.close();
			}
		}

		return entities;
	}


	/**
	 * 执行SQL语句
	 */
	public void execSQL(String sql)
	{
		db.execSQL(sql);
	}


	/**
	 * 执行查询语句
	 * 
	 * @param sql
	 * @return
	 */
	public Cursor select(String sql)
	{
		return db.rawQuery(sql, null);

	}


	public static SqliteManager i()
	{
		return i(null);
	}


	public static SqliteManager i(String dbName)
	{
		return i(A.app(), dbName);
	}


	public static SqliteManager i(Application context, String dbName)
	{
		if (sqliteManager == null)
		{
			synchronized (locker)
			{
				if (sqliteManager == null)
				{
					if (dbName == null)
					{
						dbName = "newDb.db";
						g.w("警告 - 没有设置默认数据库名称");
					}
					if (context == null)
					{
						g.e("严重错误 - 上下文为空");
						return null;
					}
					sqliteManager = new SqliteManager(context, dbName);
					g.i("创建 - 数据库管理类");
				}
			}
		}
		return sqliteManager;
	}


	public SQLiteDatabase getDb()
	{
		return db;
	}

}
