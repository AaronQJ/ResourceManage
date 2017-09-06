package com.bussniess.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.bussniess.dao.util.Conditions;
import com.bussniess.util.DataTablesPage;



public interface IDao<T> {

	void addOrUpdate(T bean);

	void addOrUpdateAll(Collection<T> beans);

	void delete(T bean);

	void deleteAll(Collection<T> beans);

	void deleteAllByIds(Serializable... ids);
	
	List<T>  findAllByIds(Serializable... ids);
	
	
	void deleteById(Serializable id);

	List<T> findAll();

	List<T> findByConditions(Conditions conditions);

	T findById(Serializable id);

	List<T> queryPageData(Conditions conditions,int index,int length);
	int getTotalRecord(Conditions conditions);
	void page(DataTablesPage<T> page, Conditions conditions);
	DataTablesPage<T>  findAll(Conditions conditions,int pageSize,int currentPage);
	Boolean isRepeat(Conditions conditions);
}
