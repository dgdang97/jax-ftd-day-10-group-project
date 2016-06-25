package com.cooksys.ftd.chat.server;

import java.util.ArrayList;
import java.util.List;

public class ServerChat {

	private static List<ClientHandler> clients = new ArrayList<>();
	private static List<String> Users = new ArrayList<>();
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
		ServerChat.clients.remove(client);
	}
	
	public static String onlineUsers() {
		String formattedUsers = Users.get(0);
		for (int i = 1; i < Users.size(); i++) {
			formattedUsers = formattedUsers + "\n" + Users.get(i);
		}
		return formattedUsers;
	}

	public static void addUser(String string) {
		Users.add(string);
	}

	public static void removeUser(String username) {
		Users.remove(username);
	}
}
