/**
 * 
 */
package org.courses.server.web;

import java.net.InetSocketAddress;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;

/**
 * @author NGAL version 27.03.2012
 */
public class WebSocketServer {

	public static String HOST = "localhost";
	public static String PROTOCOL = "http";
	public static String DEL = "/";
	public static int PORT = 8001;
	public static String PUB_RESOURCES_DIR = PROTOCOL + ":" + DEL + DEL + HOST + ":" + PORT + DEL;

	public WebSocketServer() throws Exception {
		
		Server server = new Server(new InetSocketAddress(HOST, PORT));
		ChatWebSocketHandler chatWebSocketHandler = new ChatWebSocketHandler();
		chatWebSocketHandler.setHandler(new DefaultHandler());

		ResourceHandler handler = new ResourceHandler();
		handler.setDirectoriesListed(true);
		handler.setResourceBase(".");
		chatWebSocketHandler.setHandler(handler);
		server.setHandler(chatWebSocketHandler);

		server.start();
	}
}
