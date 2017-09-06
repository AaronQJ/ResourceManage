package com.bussniess.service;

import java.util.ArrayList;
import java.util.List;

import com.bussniess.dao.IOffProDao;
import com.bussniess.dao.util.Conditions;
import com.bussniess.dao.util.Conditions.Operator;
import com.bussniess.domain.OffPro;
import com.bussniess.util.DataTablesPage;

public class OffProService {
    private IOffProDao offDao;

	public IOffProDao getOffDao() {
		return offDao;
	}

	public void setOffDao(IOffProDao offDao) {
		this.offDao = offDao;
	}

	public void add(OffPro offPro) {
	
		offDao.addOrUpdate(offPro);
	}

	public void page(DataTablesPage<OffPro> page, Conditions conditions) {
		offDao.page(page, conditions);
		
	}

	public Boolean checkSeqNum(Conditions conditions) {
		return offDao.isRepeat(conditions);
		
	}
    
	public void save(OffPro offPro){
		offDao.save(offPro);
		
		
	}
	
	
	
	
	
/////
	/**
	 * 判断待增加的数据中和数据表中是否重复（主要看offSeqNum是否相同），若重复就不进行增加（但是可以将相同的数据的 更新人，更新时间 字段进行更新）
	 * 返回值为0 代表没有重复的可以插入， 否则为重复的行号 【待添加offNum参数】
	 * @param seqNum
	 * @return
	 */
	public String isOffDupli(String seqNum ) {
	//	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
	//	HibernateTemplate hibernateTemplate = (HibernateTemplate) applicationContext.getBean("hibernateTemplate");
	//	OffProDaoImpl offDao = (OffProDaoImpl) applicationContext.getBean("offDao");
		
		Conditions conditions = new Conditions();
		conditions.addCondition("offSeqNum", seqNum, Operator.EQUAL);		//添加查询条件产品序列号是否与当前待插入数据的产品序列号值相等
		List<OffPro> list = new ArrayList<OffPro>();
		list = offDao.findByConditions(conditions);
		if(list.size() == 0) {			//0表示不相等，可以往数据表中添加设备
			return null;
		} else {
			System.out.println("有"+list.size()+"条信息重复！"+list.get(0).getOffName()+" "+list.get(0).getOffSeqNum());
			return list.get(0).getOffId();		//因为重复的只可能有一个，返回当前的id号
		}	
	}

	public DataTablesPage<OffPro> findAll(Conditions conditions,
			int iDisplayLength1, int nowPage1) {
	
		return offDao.findAll(conditions, iDisplayLength1, nowPage1);
	}

	public OffPro findById(String offId) {
		
		return offDao.findById(offId);
	}

	public void update(OffPro offPro) {
		offDao.addOrUpdate(offPro);
		
	}

	public void deleteById(String offid) {
		offDao.deleteById(offid);
		
	}
	
	public int findByCondition(Conditions conditions){
		return offDao.findByConditions(conditions).size();
	}
	
	public  List<OffPro> findAll(){
		return offDao.findAll();
	}

	public List<OffPro> findByConditions(Conditions conditions) {
		
		return offDao.findAll();
	}
    
}
