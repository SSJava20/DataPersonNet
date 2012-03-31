package org.courses.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import org.courses.core.command.AllPersonsCommand;
import org.courses.core.command.Command;
import org.courses.core.command.PersonByIdCommand;
import org.courses.core.command.deletePersonCommand;
import org.courses.core.command.saveOrUpdateCommand;
import org.courses.core.dao.DAOFactory;
import org.courses.core.domain.Person;

import com.google.gson.Gson;


public class ServerThread implements Runnable {
	private String name;
	private Socket clientSocket;
	private MainServer server;
	private Thread clientThread;
	int id;

	public ServerThread(MainServer server, Socket clientSocket) {
		int iid = server.serverThreads.size();
		name = "Player" + iid; // Default
		this.server = server;
		this.clientSocket = clientSocket;
		clientThread = new Thread(this);
		clientThread.start();
	}

	private void sendCommand(String commandString) {
		PrintWriter out;
		try {
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					clientSocket.getOutputStream())), true);
			out.println(commandString);
//			System.out.println("SEND COMMAND: threadID="
//					+ this.server.serverThreads.indexOf(this) + " :  "
//					+ commandString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void SaveOrUpdatePerson(Person toAdd) throws Exception {
		DAOFactory.getInstance().getPersonDAO('l').saveOrUpdate(toAdd);
	}


	private void DeletePerson(Person toDel) throws Exception {
		DAOFactory.getInstance().getPersonDAO('l').delPerson(toDel);
	}

	private void SendPersonById(int id) throws Exception {
		Command command = new Command(new PersonByIdCommand(id, DAOFactory
				.getInstance().getPersonDAO('l').getPersonById(id)));
		String commandString = command.serialize();
		sendCommand(commandString);
	}

	private void SendAllPersons() throws Exception {
		Command command = new Command(new AllPersonsCommand(DAOFactory
				.getInstance().getPersonDAO('l').getPersons()));
		String commandString = command.serialize();
		sendCommand(commandString);
	}

	private void operateCommand(String getcommand) throws Exception {
		Gson gson = new Gson();
		Command command = Command.deserialize(getcommand);
//		System.out.println("GET COMMAND: threadID="
//				+ this.server.serverThreads.indexOf(this) + " :COMMAND_TYPE: "
//				+ command.getType() + " " + command.getStringData());
		switch (command.getType()) {
		case Command.SAVE_OR_UPDATE_PERSON: {

			saveOrUpdateCommand apComm = gson.fromJson(command.getStringData(),
					saveOrUpdateCommand.class);
			SaveOrUpdatePerson(apComm.getPerson());
			break;
		}
		case Command.DELETE_PERSON: {

			deletePersonCommand delComm = gson.fromJson(
					command.getStringData(), deletePersonCommand.class);
			DeletePerson(delComm.getPerson());
			break;
		}
		case Command.PERSON_BY_ID: {

			PersonByIdCommand personByIdCommand = gson.fromJson(
					command.getStringData(), PersonByIdCommand.class);
			SendPersonById(personByIdCommand.getIndex());
			break;
		}
		case Command.ALL_PERSONS: {

			AllPersonsCommand allPersonsCommand = gson.fromJson(
					command.getStringData(), AllPersonsCommand.class);
			SendAllPersons();
			break;
		}
		default:
		}
	}

	public void run() {
		Scanner in = null;
		try {
			in = new Scanner(new InputStreamReader(
					clientSocket.getInputStream()));
		} catch (IOException e1) {
			server.serverThreads.remove(this);
			e1.printStackTrace();
		}
		while (true) {
			String getStringCommand = "";
			try {
				getStringCommand = in.nextLine();
				System.out.println(getStringCommand);
				operateCommand(getStringCommand);
			} catch (Exception e) {
				try {
					clientSocket.close();
					server.serverThreads.remove(this);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				break;
			}
//			System.out.println(getStringCommand);
		}
		Thread.currentThread().interrupt();
	}

}
