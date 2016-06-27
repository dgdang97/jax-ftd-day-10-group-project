package com.cooksys.ftd.chat.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ServerChat {

	private static List<ClientHandler> clients = new ArrayList<>();
	private static List<String> Users = new ArrayList<>();
	private static String message;
	private static Map<String, String> bannedWords = new TreeMap<>();
	
	public static void setMessage(String message) {
		ServerChat.checkMessage(message);
		for (ClientHandler s: clients) {
			s.write(ServerChat.message);
		}
	}
	
	public static String checkMessage(String message) {
		ServerChat.message = message;
		for (String s: ServerChat.bannedWords.keySet()) {
			ServerChat.profanityFilter(message, s, ServerChat.bannedWords.get(s));
		}
		return ServerChat.message;
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
	
	public static String help() {
		return ("---------Current Commands---------\n"
				+ "/help - Display the Current Commands\n"
				+ "/online - Display the current users online\n"
				+ "/disconnect - Disconnects you from the server");

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
		Collections.sort(Users);
	}

	public static void removeUser(String username) {
		Users.remove(username);
	}

	public static void banWords() {
		ServerChat.bannedWords.put("fuck", "freak");
		ServerChat.bannedWords.put("damn", "darn");
		ServerChat.bannedWords.put("shit", "crap");
		ServerChat.bannedWords.put("nigger", "person");
		ServerChat.bannedWords.put("bitch", "lady");
	}
}
