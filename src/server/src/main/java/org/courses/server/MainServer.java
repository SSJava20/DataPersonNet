package org.courses.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import org.courses.server.web.WebSocketServer;

public class MainServer implements Runnable {
	private static int DEFAULT_PORT = 3224;
	private static String DEFAULT_IP = "127.0.0.1";

	protected Vector<ServerThread> serverThreads;
	InetAddress myAddress;
	Thread mainServerThread;

	int Port;

	protected ServerSocket mServerSocket;

	public MainServer(InetAddress address, int port) throws IOException {
		myAddress = address;
		serverThreads = new Vector<ServerThread>();
		Port = port;
		mServerSocket = new ServerSocket(port, 0, myAddress);

		mainServerThread = new Thread(this);
		mainServerThread.start();
	}

	public ServerThread getServerThreadById(int id) {
		return serverThreads.get(id);
	}

	public void deleteServerThread(ServerThread toDel) {
		serverThreads.remove(toDel);
	}

	public static void main(String[] args) {
		int port = DEFAULT_PORT;
		String ip = DEFAULT_IP;
		if (args.length > 1) {
			port = Integer.parseInt(args[1]);
			ip = args[0];
		}
		try {
			new MainServer(InetAddress.getByName(ip), port);
			new WebSocketServer();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		try {
			while (true) {
				Socket nSocket = mServerSocket.accept();
				System.out.println("New client connected");
				serverThreads.add(new ServerThread(this, nSocket));
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				mServerSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}