package org.courses.desktopclient.model;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import org.courses.core.dao.DAOFactory;
import org.courses.core.dao.IPersonDAO;
import org.courses.core.domain.Person;
import org.eclipse.swt.graphics.ImageData;


public class Model {
	/**
	 * @throws Exception
	 * 
	 */
	public Model() throws Exception {
		personDAO = new DAOFactory().getPersonDAO('n');
	}

	private IPersonDAO personDAO = null;
	private ArrayList<Person> persons = new ArrayList<Person>();
	boolean firstGet = true;

	public ArrayList<Person> getPersons() {
		persons = personDAO.getPersons();
		return persons;
	}

	public void setPersons(ArrayList<Person> aPersons) {
		persons = aPersons;
		personDAO.saveOrUpdate(persons);
	}

	public void addPerson(Person p) {
		persons.add(p);
		personDAO.saveOrUpdate(p);
	}

	public void editPerson(Person p) {
		Person editPerson = getPerson(p.getId());
		int index = persons.indexOf(editPerson);
		editPerson.setId(p.getId());
		persons.remove(index);
		persons.add(index, p);

		personDAO.saveOrUpdate(p);
	}

	public void delPerson(int id) {
		Person p = new Person();
		p.setId(id);
		personDAO.delPerson(p);
		for (int i = 0; i < persons.size(); i++) {
			if (p.getId() == persons.get(i).getId()) {
				persons.remove(persons.get(i));
			}
		}
	}

	public Person getPerson(int id) {
		for (Person p : persons) {
			if (p.getId() == id)
				return p;
		}
		return null;
	}

	/**
	 * Get first simage from person
	 * 
	 * @param id
	 *            - person ID
	 * @return
	 */
	public ImageData getImageData(int id) {
		for (Person p : persons) {
			if (p.getId() == id)
				if (p.getImgData() != null) {
					ByteArrayInputStream stream = new ByteArrayInputStream(
							p.getImgData());
					return new ImageData(stream);
				}
		}
		return null;
	}
}
