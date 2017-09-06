package com.bussniess.dao.util;

import java.util.ArrayList;
import java.util.List;

//高内聚 的类
public class Conditions {

	private List<OrderBy> orderByList = new ArrayList<OrderBy>();

	// 添加排序规则
	public void addOrderBy(String key, boolean isAsc) {
		if (key == null || key.trim().length() == 0) {
			return;
		}
		orderByList.add(new OrderBy(key, isAsc));
	}

	// order by username asc , age desc ,
	public String createOrderByString() {
		if (orderByList.size() == 0) {
			return "";
		}
		StringBuilder re = new StringBuilder(" order by ");
		for (OrderBy orderBy : orderByList) {
			re.append(orderBy.getKey()).append(orderBy.isAsc ? " asc " : " desc ").append(" , ");
		}
		return re.substring(0, re.length() - 3);
	}

	class OrderBy {
		private String key;// 按照什么排序
		private boolean isAsc;// 是否是安装升序排序

		public OrderBy() {
		}

		public OrderBy(String key, boolean isAsc) {
			super();
			this.key = key;
			this.isAsc = isAsc;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public boolean isAsc() {
			return isAsc;
		}

		public void setAsc(boolean isAsc) {
			this.isAsc = isAsc;
		}

	}

	class Condition {
		private String key;// 左侧的操作数
		private Object value;// 右侧
		private Operator operator;

		public Condition() {
		}

		public Condition(String key, Object value, Operator operator) {
			super();
			this.key = key;
			this.value = value;
			this.operator = operator;
		}

		public String getKey() {
			return key;
		}

		public Operator getOperator() {
			return operator;
		}

		public Object getValue() {
			return value;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public void setOperator(Operator operator) {
			this.operator = operator;
		}

		public void setValue(Object value) {
			this.value = value;
		}

	}

	// 操作符,运算符
	public enum Operator {
		EQUAL, LIKE, NOT_EQUAL, GREATER, GREATER_EQUAL, LESS, LESS_EQUAL, IS, NOT_IS
	}

	// where语句 和占位符的值
	public class WhereAndValues {
		private String where = "";
		private Object[] values;

		public Object[] getValues() {
			return values;
		}

		public String getWhere() {
			return where;
		}

		public void setValues(Object[] values) {
			this.values = values;
		}

		public void setWhere(String where) {
			this.where = where;
		}

	}

	/*
	 * 
	用来封装所有的查询条件
	
	= like != > >= < <= is not is

	hql
	from beanName where username=? and  username like ? and age > ? 
	'aa'  '%张%'  20
	
	from ElecText  
	username='aa'
	 */

	private List<Condition> conditions = new ArrayList<Condition>();

	// 添加查询条件的方法(关键)
	// 严格的 - 无效的查询条件都不添加
	// 安静的方法 - 添加失败不会抛出任何异常,不会给出任何提示
	// username like null
	public void addCondition(String key, Object value, Operator operator) {
		if (key == null || key.trim().length() == 0) {
			return;
		}
		if (value == null) {
			if (operator != Operator.IS && operator != Operator.NOT_IS) {
				return;
			}
		}

		if (value != null && value instanceof String) {
			String v = (String) value;
			if (v.trim().length() == 0) {
				return;
			}
		}

		if (operator == null) {
			return;
		}
		conditions.add(new Condition(key, value, operator));

	}

	public WhereAndValues createWhereAndValues() {
		// 1 没有有效的查询条件
		WhereAndValues wv = new WhereAndValues();
		StringBuilder where = new StringBuilder(" where ");
		List<Object> values = new ArrayList<Object>();

		for (Condition condition : conditions) {
			Operator operator = condition.getOperator();
			String key = condition.getKey();
			Object value = condition.getValue();

			switch (operator) {
			case LIKE:
				// key like %xxx%
				where.append(key).append(" like ").append(" ? ");
				values.add("%" + value + "%");
				break;
			case EQUAL:
				where.append(key).append(" = ").append(" ? ");
				values.add(value);
				break;
			case NOT_EQUAL:
				where.append(key).append(" != ").append(" ? ");
				values.add(value);
				break;
			case GREATER:
				where.append(key).append(" > ").append(" ? ");
				values.add(value);
				break;
			case GREATER_EQUAL:
				where.append(key).append(" >= ").append(" ? ");
				values.add(value);
				break;
			case LESS:
				where.append(key).append(" < ").append(" ? ");
				values.add(value);
				break;
			case LESS_EQUAL:
				where.append(key).append(" <= ").append(" ? ");
				values.add(value);
				break;
			case IS:
				where.append(key).append(" is ").append(" ? ");
				values.add(null);
				break;
			case NOT_IS:
				where.append(key).append(" not is ").append(" ? ");
				values.add(null);
				break;
			}

			// where username like ? and age > ?
			where.append("  and ");
		}
		// where username like ? and age > ? and
		String whereString = where.substring(0, where.length() - 6);

		wv.setWhere(whereString);
		wv.setValues(values.toArray());
		return wv;
	}
}
