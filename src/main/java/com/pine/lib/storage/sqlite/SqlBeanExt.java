package com.pine.lib.storage.sqlite;

import android.view.View;


public abstract class SqlBeanExt implements SqliteBeanInterface
{

	public void update()
	{
		SqliteManager.i().update(this);

	}


	public void insert()
	{
		SqliteManager.i().insert(this, this.getClass());

	}


	@Override
	public void del()
	{
		SqliteManager.i().del(this);

	}

}
