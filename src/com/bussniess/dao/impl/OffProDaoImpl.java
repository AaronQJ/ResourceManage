package com.bussniess.dao.impl;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.bussniess.dao.IOffProDao;
import com.bussniess.domain.OffPro;

public class OffProDaoImpl extends BaseDao<OffPro> implements IOffProDao {
	private HibernateTemplate hibernateTemplate;
	@Override
	public void save(OffPro offPro) {
		hibernateTemplate.save(offPro);
		
	}

}
