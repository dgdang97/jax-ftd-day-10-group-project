package com.cooksys.ftd.chat.server;

import java.util.ArrayList;
import java.util.List;

public class ServerChat {

	private static List<ClientHandler> clients = new ArrayList<>();
	private static List<String> Users = new ArrayList<>();
	private static String message;
	
	public static void setMessage(String message) {
		ServerChat.checkMessage(message);
		for (ClientHandler s: clients) {
			s.write(ServerChat.message);
		}
	}
	
	public static String checkMessage(String message) {
		ServerChat.message = message;
		ServerChat.profanityFilter(message, "fuck", "cluck");
		ServerChat.profanityFilter(message, "shit", "crap");
		ServerChat.profanityFilter(message, "damn", "darn");
		ServerChat.profanityFilter(message, "fuk", "cluck");
		return message;
	}

	public static String profanityFilter(String message, String profanity, String replacement) {
		if (message.toLowerCase().contains(profanity)) {
			ServerChat.message = message.toLowerCase().replace(profanity, replacement);
		}
		return message;
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
