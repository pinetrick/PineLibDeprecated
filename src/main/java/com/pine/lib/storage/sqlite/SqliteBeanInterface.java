package com.pine.lib.storage.sqlite;

public interface SqliteBeanInterface
{
	public int getId();
	public void setId(int id);
	public void update();
	public void insert();
	public void del();
}
