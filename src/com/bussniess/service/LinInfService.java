package com.bussniess.service;

import java.util.ArrayList;
import java.util.List;

import com.bussniess.dao.ILinInfDao;
import com.bussniess.dao.util.Conditions;
import com.bussniess.dao.util.Conditions.Operator;
import com.bussniess.domain.LinInf;
import com.bussniess.util.DataTablesPage;


public class LinInfService {
		private ILinInfDao infoDao;

		public ILinInfDao getInfoDao() {
			return infoDao;
		}

		public void setInfoDao(ILinInfDao infoDao) {
			this.infoDao = infoDao;
		}

		public void add(LinInf lineInfo) {
			infoDao.addOrUpdate(lineInfo);
			
		}

		public void page(DataTablesPage<LinInf> page, Conditions conditions) {
			infoDao.page(page, conditions);
			
		}

		public void deleteById(String lineId) {
			infoDao.deleteById(lineId);
			
		}

		public DataTablesPage<LinInf> findAll(Conditions conditions,
				int iDisplayLength1, int nowPage1) {
			
			return infoDao.findAll(conditions, iDisplayLength1, nowPage1);
		}

		public LinInf  findById(String lineid) {
			
			return infoDao.findById(lineid);
		}
		
		
		/**
		 * 判断待增加的数据中和数据表中是否重复（主要看lineNum是否相同），若重复就不进行增加
		 * 返回值为0 代表没有重复的可以插入， 否则为重复的行号 
		 * @param lineNum
		 * @return
		 */
		public String isLineDupli(String lineNum ) {		
			Conditions conditions = new Conditions();
			conditions.addCondition("lineNum", lineNum, Operator.EQUAL);		//添加查询条件专线号是否与当前待插入数据的专线号值相等
			List<LinInf> list = new ArrayList<LinInf>();
			list = infoDao.findByConditions(conditions);
			if(list.size() == 0) {					//0表示不相等，可以往数据表中添加设备
				return null;
			} else {
				return list.get(0).getLineId();		//因为重复的只可能有一个，返回当前的id号
			}	
		}

		public List<LinInf> findByConditions(Conditions conditions) {
			return infoDao.findByConditions(conditions);
		}

		public List<LinInf> findAll() {
			
			return infoDao.findAll();
		}

}
