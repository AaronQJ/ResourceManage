package com.bussniess.service;

import java.util.ArrayList;
import java.util.List;

import com.bussniess.dao.ISpeProDao;
import com.bussniess.dao.util.Conditions;
import com.bussniess.dao.util.Conditions.Operator;
import com.bussniess.domain.SpePro;
import com.bussniess.util.DataTablesPage;


public class SpeProService {
	
	private ISpeProDao  speDao;

	public ISpeProDao getSpeDao() {
		return speDao;
	}

	public void setSpeDao(ISpeProDao speDao) {
		this.speDao = speDao;
	}

	public void add(SpePro spe) {
		speDao.addOrUpdate(spe);
		
	}

	public List<SpePro> findAll() {
		return speDao.findAll();
	}

	public void page(DataTablesPage<SpePro> page, Conditions conditions) {
		speDao.page(page, conditions);
		
	}

	public boolean chackSeqNum(Conditions conditions) {
		
		return speDao.isRepeat(conditions);
	}

	public DataTablesPage<SpePro> findAll(Conditions conditions,
			int iDisplayLength1, int nowPage1) {
		
		return speDao.findAll(conditions, iDisplayLength1, nowPage1);
	}

	public SpePro findById(String speid) {
		return speDao.findById(speid);
		
	}

	public void deleteById(String speId) {
		speDao.deleteById(speId);
		
	}

	public void update(SpePro spePro) {
		speDao.addOrUpdate(spePro);
		
	}

	public int findByCondition(Conditions conditions) {
		
		return speDao.findByConditions(conditions).size();
	}

	
	
	/**
	 * 判断待增加的数据中和数据表中是否重复（主要看speSeqNum是否相同），若重复就不进行增加
	 * 返回值为0 代表没有重复的可以插入， 否则为重复的行号 
	 * @param seqNum
	 * @return
	 */
	public String isSpeDupli(String seqNum ) {		
		Conditions conditions = new Conditions();
		conditions.addCondition("speSeqNum", seqNum, Operator.EQUAL);		//添加查询条件产品序列号是否与当前待插入数据的产品序列号值相等
		List<SpePro> list = new ArrayList<SpePro>();
		list = speDao.findByConditions(conditions);
		if(list.size() == 0) {			//0表示不相等，可以往数据表中添加设备
			return null;
		} else {
			return list.get(0).getSpeId();		//因为重复的只可能有一个，返回当前的id号
		}	
	}

	public List<SpePro> findByConditions(Conditions conditions) {
		return speDao.findByConditions(conditions);
	}
	

}
