/**
 * 
 */
package org.courses.server.web;

import static org.courses.server.web.WebSocketServer.PUB_RESOURCES_DIR;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.codec.binary.Base64;
import org.courses.core.dao.DAOFactory;
import org.courses.core.dao.IPersonDAO;
import org.courses.core.domain.Person;
import org.eclipse.jetty.websocket.WebSocket.Connection;

import com.google.gson.Gson;


/**
 * @author NGAL version 26.03.2012
 */
public class SocketThread implements Runnable {
	private Connection connection;
	private BufferedReader reader;
	Gson gson = new Gson();

	/**
	 * @throws IOException
	 * 
	 */
	public SocketThread(Connection conn) throws IOException {
		connection = conn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		try {
			while (true) {
				String str = reader.readLine();
				operateCommand(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param str
	 * @throws IOException
	 */
	private void operateCommand(String str) throws IOException {
		connection.sendMessage(str);
	}

	/**
	 * @param data
	 * @throws Exception
	 */
	public void operateFromWeb(String message) throws Exception {
		IPersonDAO dao = DAOFactory.getInstance().getPersonDAO('l');
		String type = message.substring(0, message.indexOf("|"));
		String data = message.substring(message.indexOf("|") + 1);

		if (type.equals("save")) {
			Person p = gson.fromJson(data, Person.class);
			dao.saveOrUpdate(p);
		}
		if (type.equals("loadPersons")) {
			ArrayList<Person> persons = dao.getPersons();
			convertImageData(persons);
			changeFilePath(persons);
			connection.sendMessage(gson.toJson(persons.toArray()));
		}
		if (type.equals("delete")) {
			Person toDel = new Person();
			toDel.setId(Integer.parseInt(data));
			dao.delPerson(toDel);
		}
	}

	/**
	 * Convert person image to base64 array
	 * 
	 * @param persons
	 * @throws IOException
	 */
	private void convertImageData(ArrayList<Person> persons) throws IOException {
		for (int i = 0; i < persons.size(); i++) {
			Person p = persons.get(i);
			byte[] data = p.getImgData();
			if (data != null) {
				p.setImgData(Base64.encodeBase64(p.getImgData()));
			}
		}
	}

	/**
	 * Change filepath to web filepath
	 * 
	 * @param persons
	 * @throws IOException
	 */
	private void changeFilePath(ArrayList<Person> persons) throws IOException {
		for (int i = 0; i < persons.size(); i++) {
			Person p = persons.get(i);
			String path = p.getFilePath();
			if (path != null) {
				// path = path.substring(1);
				path = PUB_RESOURCES_DIR + path;
				p.setFilePath(path);
			}
		}
	}
}
