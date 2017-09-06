package com.bussniess.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.bussniess.dao.IDao;
import com.bussniess.dao.util.Conditions;
import com.bussniess.dao.util.Conditions.WhereAndValues;
import com.bussniess.util.DataTablesPage;




public class BaseDao<T> implements IDao<T> {

	private HibernateTemplate hibernateTemplate;
	
	private Class beanClass;

	{
		// 参数化类型
		// IDao<ElecText>
		ParameterizedType paramType = (ParameterizedType) this.getClass().getGenericSuperclass();
		Type[] argTypes = paramType.getActualTypeArguments();

		beanClass = (Class) argTypes[0];

	}

	public void addOrUpdate(T bean) {
		hibernateTemplate.saveOrUpdate(bean);
	}

	public void addOrUpdateAll(Collection<T> beans) {

		if (beans != null) {
			hibernateTemplate.saveOrUpdateAll(beans);
		}
	}

	public void delete(T bean) {
		hibernateTemplate.delete(bean);
	}

	public void deleteAll(Collection<T> beans) {
		hibernateTemplate.deleteAll(beans);
	}

	public void deleteAllByIds(Serializable... ids) {
		if (ids != null) {
			for (Serializable id : ids) {
				deleteById(id);
			}
		}
	}

	public void deleteById(Serializable id) {
		T bean = findById(id);

		if (bean != null) {
			delete(bean);
		}
	}

	public List<T> findAll() {

		String hql = " from " + beanClass.getName();
		return hibernateTemplate.find(hql);

		// return hibernateTemplate.loadAll(beanClass);
	}

	public List<T> findByConditions(Conditions conditions) {

		WhereAndValues wv = conditions.createWhereAndValues();
		String hql = "from " + beanClass.getName() + wv.getWhere() + conditions.createOrderByString();
		Object[] values = wv.getValues();

		System.out.println(hql);
		return hibernateTemplate.find(hql, values);

	}

	public T findById(Serializable id) {

		return (T) hibernateTemplate.get(beanClass, id);

	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public void page(final DataTablesPage<T> page, Conditions conditions) {
		if(conditions==null){
			final String hql = "from " + beanClass.getName();
			final String totalHql = "select count(*) from " + beanClass.getName();
		}
		WhereAndValues wv = conditions.createWhereAndValues();
		final String hql = "from " + beanClass.getName() + wv.getWhere();
		final Object[] values = wv.getValues();

		System.out.println(hql);

		List<T> data = hibernateTemplate.execute(new HibernateCallback<List<T>>() {

			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);

				// 设置占位符的值
				if (values != null && values.length > 0) {
					for (int i = 0; i < values.length; i++) {
						query.setParameter(i, values[i]);
					}
				}
				query.setFirstResult(page.getIDisplayStart());
				query.setMaxResults(page.getIDisplayLength());

				return query.list();
			}
		});


		
		
		page.setData(data);

		// 查询符合条件的总数

		final String totalHql = "select count(*) from " + beanClass.getName() + wv.getWhere();

		long iTotalRecords = hibernateTemplate.execute(new HibernateCallback<Long>() {
			public Long doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(totalHql);
				// 设置占位符的值
				if (values != null && values.length > 0) {
					for (int i = 0; i < values.length; i++) {
						query.setParameter(i, values[i]);
					}
				}
				return (Long) query.uniqueResult();
			}
		});


	page.setITotalRecords((int) iTotalRecords);
	page.setITotalDisplayRecords(page.getITotalRecords());
		
	}

	@Override
	public List<T>  findAllByIds(Serializable... ids) {
		List<T> list = new ArrayList<T>();
		if(ids!=null){
			for (Serializable id : ids) {
				list.add(findById(id));
			}
		}
		return list;
	}

	@Override
	public DataTablesPage<T> findAll(Conditions conditions, int pageSize,int currPage) {
		 	int  totalRecords = getTotalRecord(conditions);    //总记录数
	        int totalPage = DataTablesPage.countTotalPage(pageSize, totalRecords);    //总页数
	        final int startIndex = DataTablesPage.countOffset(pageSize, currPage);    //当前页开始记录
	        final int length = pageSize;    //每页记录数
	        final int currentPage = DataTablesPage.countCurrentPage(currPage);
	        List<T> list = queryPageData(conditions, startIndex, length);       //"一页"的记录
	       
	        //把分页信息保存到Bean中
	        DataTablesPage<T> pageBean = new DataTablesPage<T>();
	        pageBean.setIDisplayLength(pageSize);   
	        pageBean.setCurrentPage(currentPage);
	        pageBean.setITotalRecords(totalRecords);
	        pageBean.setTotalPage(totalPage);
	        pageBean.setData(list);
	        pageBean.init();
	        return pageBean;
		
	
	}

	@Override
	public List<T> queryPageData(Conditions conditions, final int index, final int length) {

		WhereAndValues wv = conditions.createWhereAndValues();
		final String hql = "from " + beanClass.getName() + wv.getWhere();
		final Object[] values = wv.getValues();

		System.out.println(hql);

		List<T> data = hibernateTemplate.execute(new HibernateCallback<List<T>>() {

			@SuppressWarnings("unchecked")
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);

				// 设置占位符的值
				if (values != null && values.length > 0) {
					for (int i = 0; i < values.length; i++) {
						query.setParameter(i, values[i]);
					}
				}
				query.setFirstResult(index);
				query.setMaxResults(length);

				return query.list();
			}
		});
		
		return data;
	}

	@Override
	public int getTotalRecord(Conditions conditions) {
	
		
		WhereAndValues wv = conditions.createWhereAndValues();
		final Object[] values = wv.getValues();
		final String totalHql = "select count(*) from " + beanClass.getName() + wv.getWhere();

		long iTotalRecords = hibernateTemplate.execute(new HibernateCallback<Long>() {
			public Long doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(totalHql);
				// 设置占位符的值
				if (values != null && values.length > 0) {
					for (int i = 0; i < values.length; i++) {
						query.setParameter(i, values[i]);
					}
				}
				return (Long) query.uniqueResult();
			}
		});

		return (int)iTotalRecords;
	}

	@Override
	public Boolean isRepeat(Conditions condition) {
		WhereAndValues wv = condition.createWhereAndValues();
		final String hql = "from " + beanClass.getName() + wv.getWhere();
		final Object[] values = wv.getValues();
		List<T> list = hibernateTemplate.find(hql, values);
		if(list.size()!=0){
			return true;
		}
		return false;
	}


	
}
