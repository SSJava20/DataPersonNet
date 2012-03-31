package org.courses.core.dao;

import java.util.ArrayList;
import java.util.List;

import org.courses.core.domain.Person;

public interface IPersonDAO
{
	void saveOrUpdate(List<Person> persons);
	void saveOrUpdate(Person person);
	Person getPersonById(int ID);
	ArrayList<Person> getPersons();
	void delPerson(Person person);
}
