package org.courses.desktopclient;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.courses.core.domain.Person;
import org.courses.desktopclient.controller.Controller;
import org.courses.desktopclient.model.Model;
import org.courses.desktopclient.view.MainWindow;


public class Main
{
	static Connection conn = null;
	ArrayList<Person> persons = new ArrayList<Person>();
	/**
	 * @param args
	 * @throws SQLException
	 */

	public static void main(String[] args) throws SQLException
	{
		try
		{
			Model model = new Model();
			MainWindow window = new MainWindow(model);
			window.open();
			Controller controller = new Controller(model, window);

			while (!window.shell.isDisposed())
			{
				if (!window.display.readAndDispatch())
				{
					window.display.sleep();
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
