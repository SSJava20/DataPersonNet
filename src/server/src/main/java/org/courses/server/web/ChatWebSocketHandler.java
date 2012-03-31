package org.courses.server.web;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketHandler;

public class ChatWebSocketHandler extends WebSocketHandler {

	public static int count = 0;

	public WebSocket doWebSocketConnect(HttpServletRequest request,
			String protocol) {
		return new SocketHandler();
	}

	private class SocketHandler implements WebSocket.OnTextMessage {
		SocketThread socketThread;

		public void onOpen(Connection connection) {
			try {
				socketThread = new SocketThread(connection);
				System.out.println("New player on proxy : " + ++count);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		public void onMessage(String data) {
			try {
				System.out.println("Proxy get " + data);
				socketThread.operateFromWeb(data);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		public void onClose(int closeCode, String message) {
			// processor.kill();
			// connection.close();
			System.out.println("Now at proxy : " + --count + " players");
			System.out.println("Proxy close socket");
		}
	}
}