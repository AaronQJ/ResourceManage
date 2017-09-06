package com.bussniess.service;

import java.util.ArrayList;
import java.util.List;

import com.bussniess.dao.IProBakDao;
import com.bussniess.dao.util.Conditions;
import com.bussniess.dao.util.Conditions.Operator;
import com.bussniess.domain.ProBak;
import com.bussniess.util.DataTablesPage;


public class ProBakService {
	
	private IProBakDao bakDao;

	public IProBakDao getBakDao() {
		return bakDao;
	}

	public void setBakDao(IProBakDao bakDao) {
		this.bakDao = bakDao;
	}

	public void add(ProBak proBak) {
		bakDao.addOrUpdate(proBak);
	}

	public void page(DataTablesPage<ProBak> page, Conditions conditions) {

		bakDao.page(page, conditions);
	}

	

	public DataTablesPage<ProBak> findAll(Conditions conditions,
			int iDisplayLength1, int nowPage1) {
		
		return bakDao.findAll(conditions, iDisplayLength1, nowPage1);
	}

	public boolean checkSeqNum(Conditions conditions) {
	
		return bakDao.isRepeat(conditions);
	}

	public ProBak findById(String bakId) {
		
		return bakDao.findById(bakId) ;
	}

	public void update(ProBak proBak) {
		bakDao.addOrUpdate(proBak);
		
	}

	public void deleteById(String bakid) {
		bakDao.deleteById(bakid);
		
	}
	
	public int findByCondition(Conditions conditions){
		return bakDao.findByConditions(conditions).size();
	}

	public List<ProBak>  findAll(){
		return bakDao.findAll();
	}
	
	
	/**
	 * 判断待增加的数据中和数据表中是否重复（主要看lineNum是否相同），若重复就不进行增加
	 * 返回值为0 代表没有重复的可以插入， 否则为重复的行号 
	 * @param lineNum
	 * @return
	 */
	public String isBakDupli(String bakSeqNum ) {		
		Conditions conditions = new Conditions();
		conditions.addCondition("bakSeqNum", bakSeqNum, Operator.EQUAL);		//添加查询条件专线号是否与当前待插入数据的专线号值相等
		List<ProBak> list = new ArrayList<ProBak>();
		list = bakDao.findByConditions(conditions);
		if(list.size() == 0) {					//0表示不相等，可以往数据表中添加设备
			return null;
		} else {
			return list.get(0).getBakId();		//因为重复的只可能有一个，返回当前的id号
		}	
	}

	public List<ProBak> findByConditions(Conditions conditions) {
		
		return bakDao.findAll();
	}

	
}
