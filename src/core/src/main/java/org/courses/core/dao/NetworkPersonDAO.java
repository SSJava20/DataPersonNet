package org.courses.core.dao;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import org.courses.core.command.AllPersonsCommand;
import org.courses.core.command.Command;
import org.courses.core.command.PersonByIdCommand;
import org.courses.core.command.deletePersonCommand;
import org.courses.core.command.saveOrUpdateCommand;
import org.courses.core.domain.Person;

import com.google.gson.Gson;


/**
 * Created by IntelliJ IDEA. User: stvad Date: 20.03.12 Time: 22:24 To change
 * this template use File | Settings | File Templates.
 */
public class NetworkPersonDAO implements IPersonDAO, Runnable {
	protected ArrayList<Person> lastGotCollection;
	protected Person latGotPerson;
	boolean complete;
	Socket mySocket;
	Thread listenThread;

	public NetworkPersonDAO(Socket mySocket) {
		this.mySocket = mySocket;
		listenThread = new Thread(this);
		listenThread.start();
	}

	private void sendCommand(String commandString) {
		PrintWriter out;
		try {
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					mySocket.getOutputStream())), true);
			out.println(commandString);
			// System.out.println("SEND COMMAND: threadID="
			// + this.server.serverThreads.indexOf(this) + " :  "
			// + commandString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void operateCommand(String getStringCommand) {
		Gson gson = new Gson();
		Command command = Command.deserialize(getStringCommand);
//		System.out.println("GET COMMAND: threadID=" + " :COMMAND_TYPE: "
//				+ command.getType() + " " + command.getStringData());
		switch (command.getType()) {
		case Command.PERSON_BY_ID: {

			PersonByIdCommand personByIdCommand = gson.fromJson(
					command.getStringData(), PersonByIdCommand.class);
			GetPersonById(personByIdCommand.getPerson());
			break;
		}
		case Command.ALL_PERSONS: {

			AllPersonsCommand allPersonsCommand = gson.fromJson(
					command.getStringData(), AllPersonsCommand.class);
			GetAllPersons(allPersonsCommand.getPersons());
			break;
		}
		default:
		}
	}

	private void GetAllPersons(Collection got) {
		lastGotCollection = (ArrayList<Person>) got;
		complete = true;
	}

	private void GetPersonById(Person got) {
		latGotPerson = got;
		complete = true;
	}

	public void run() {
		Scanner in = null;
		try {
			in = new Scanner(new InputStreamReader(mySocket.getInputStream()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while (true) {
			String getStringCommand = "";
			try {
				getStringCommand = in.nextLine();
				operateCommand(getStringCommand);
			} catch (Exception e) {
				try {
					mySocket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				break;
			}
//			System.out.println(getStringCommand);
		}
		Thread.currentThread().interrupt();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.IPersonDAO#saveOrUpdate(java.util.Collection)
	 */
	public void saveOrUpdate(List<Person> persons) {
		for (Person p : persons) {
			saveOrUpdate(p);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.IPersonDAO#saveOrUpdate(domain.Person)
	 */
	public void saveOrUpdate(Person person) {
		Command updateCommand = new Command(new saveOrUpdateCommand(person));
		sendCommand(updateCommand.serialize());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.IPersonDAO#getPersons()
	 */
	public ArrayList<Person> getPersons() {
		Command personByIdCommand = new Command(new AllPersonsCommand(null));
		sendCommand(personByIdCommand.serialize());
		complete = false;
		while (!complete) {
		}
		return lastGotCollection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.IPersonDAO#delPerson(domain.Person)
	 */
	public void delPerson(Person person) {
		Command delCommand = new Command(new deletePersonCommand(person));
		sendCommand(delCommand.serialize());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.IPersonDAO#getPersonById(int)
	 */
	public Person getPersonById(int person_id) {
		Command personByIdCommand = new Command(new PersonByIdCommand(
				person_id, null));
		sendCommand(personByIdCommand.serialize());
		complete = false;
		while (!complete) {
		}
		return latGotPerson;
	}

}
