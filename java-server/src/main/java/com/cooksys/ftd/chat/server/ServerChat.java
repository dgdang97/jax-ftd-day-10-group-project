package com.cooksys.ftd.chat.server;

import java.util.ArrayList;
import java.util.List;

public class ServerChat {

	private static List<ClientHandler> clients = new ArrayList<>();
	private static String message;
	
	public static void setMessage(String message) {
		ServerChat.message = message;
		for (ClientHandler s: clients) {
			s.write(ServerChat.message);
		}
	}

	public static void addClient(ClientHandler client) {
		ServerChat.clients.add(client);
	}
	
	public static void removeClient(ClientHandler client) {
		clients.remove(client);
	}
}
