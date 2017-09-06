package com.bussniess.test;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.bussniess.domain.ElecText;



public class HibernateTest {

	public static void main(String[] args) {
		Configuration configuration = new Configuration();
		configuration.configure();

		SessionFactory sessionFactory = configuration.buildSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		ElecText text = new ElecText();
		text.setTextName("name222");
		text.setTextDate(new Date());
		text.setTextComment("Hibernate测试222");

		session.save(text);

	
		transaction.commit();

		session.close();
		sessionFactory.close();
	}
}
