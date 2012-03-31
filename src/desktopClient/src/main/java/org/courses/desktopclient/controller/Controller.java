package org.courses.desktopclient.controller;

import org.courses.core.domain.Person;
import org.courses.desktopclient.model.Model;
import org.courses.desktopclient.view.MainWindow;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;


public class Controller
{
	private Model model;
	private MainWindow view;
	public Controller(Model m, MainWindow v)
	{
		model = m;
		view = v;
		view.addBtnLoadListener(new BtnLoadListener());
		view.addBtnEditListener(new BtnEditListener());
		view.addBtnAddRowListener(new BtnAddRowListener());
		view.addBtnDelRowListener(new BtnDelRowListener());
	}

	/**
	 * Get list of persons from model and set them to view
	 * 
	 * @version 1.0, 19.02.2012
	 * @author Alexander Galkovsky
	 */
	class BtnLoadListener extends SelectionAdapter
	{
		@Override
		public void widgetSelected(SelectionEvent e)
		{
			view.setPersons(model.getPersons());
		}
	}

	/**
	 * Get list of persons from view and set them to model
	 * 
	 * @version 1.0, 19.02.2012
	 * @author Alexander Galkovsky
	 */
	class BtnEditListener extends SelectionAdapter
	{
		@Override
		public void widgetSelected(SelectionEvent e)
		{
			Person p = view.showEditPersonDialog();
			if (p != null)
			{
				model.editPerson(p);
				view.editPersonInTable(p);
			}
		}
	}

	/**
	 * Get person from <b>AddPersonDialog</b>, add it to model and to view
	 * 
	 * @version 1.0, 19.02.2012
	 * @author Alexander Galkovsky
	 */
	class BtnAddRowListener extends SelectionAdapter
	{
		@Override
		public void widgetSelected(SelectionEvent e)
		{
			Person p = view.showAddPersonDialog();
			if (p != null)
			{
				model.addPerson(p);
				view.addPersonInTable(p);
			}
		}
	}

	/**
	 * Get id from selected person in view and delete person from model
	 * 
	 * @version 1.0, 19.02.2012
	 * @author Alexander Galkovsky
	 */
	class BtnDelRowListener extends SelectionAdapter
	{
		@Override
		public void widgetSelected(SelectionEvent e)
		{
			int id = view.delSelectedPerson();
			if (id != -1)
			{
				model.delPerson(id);
			}
		}
	}
}
