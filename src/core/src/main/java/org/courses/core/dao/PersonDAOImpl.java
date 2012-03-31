package org.courses.core.dao;

import java.util.ArrayList;
import java.util.List;

import org.courses.core.domain.Person;
import org.courses.core.util.FileCopier;
import org.hibernate.Session;


public class PersonDAOImpl implements IPersonDAO
{

	public void saveOrUpdate(List<Person> persons)
	{
		Session session = null;
		try
		{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			for (Person person : persons)
			{
				session.saveOrUpdate(person);
			}
			for (int i = 0; i < persons.size(); i++)
			{
				FileCopier.copyFilesToResources(persons.get(i));
			}
			session.getTransaction().commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (session != null && session.isOpen())
			{
				session.close();
			}
		}
	}
	public void saveOrUpdate(Person person)
	{
		Session session = null;
		try
		{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.saveOrUpdate(person);
			FileCopier.copyFilesToResources(person);
			session.saveOrUpdate(person);
			session.getTransaction().commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (session != null && session.isOpen())
			{
				session.close();
			}
		}
	}

	public Person getPersonById(int ID)
	{
		Person person = null;
		Session session = null;
		try
		{
			session = HibernateUtil.getSessionFactory().openSession();
			person = (Person) session.get(Person.class, ID);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (session != null && session.isOpen())
			{
				session.close();
			}
		}
		return person;
	}

	public void delPerson(Person person)
	{
		Session session = null;
		try
		{
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.delete(person);
			session.getTransaction().commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (session != null && session.isOpen())
			{
				session.close();
			}
		}
	}

	public ArrayList<Person> getPersons()
	{
		ArrayList<Person> persons = new ArrayList<Person>();
		Session session = null;
		try
		{
			session = HibernateUtil.getSessionFactory().openSession();
			persons = (ArrayList<Person>) session.createCriteria(Person.class).list();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (session != null && session.isOpen())
			{
				session.close();
			}
		}
		for ( int i =0; i<persons.size(); i++)
		{
			Person p =persons.get(i); 
			p.loadFileData();
			p.loadImageData();
		}
		return persons;
	}
}
