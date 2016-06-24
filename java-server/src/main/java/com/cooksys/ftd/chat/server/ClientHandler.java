package com.cooksys.ftd.chat.server;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientHandler implements Runnable, Closeable {

	Logger log = LoggerFactory.getLogger(ClientHandler.class);
	String username;
	
	private Socket client;
	private PrintWriter writer;
	private BufferedReader reader;

	public ClientHandler(Socket client) throws IOException {
		super();
		this.client = client;
		this.reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
		this.writer = new PrintWriter(client.getOutputStream(), true);
	}

	public String username () throws IOException {
			writer.print("Welcome to the Server! Please input a username.");
			writer.flush();
			username = reader.readLine();
			writer.print("Your username is now " + username + ". Enjoy your stay!");
			writer.flush();
			log.info("Username obtained. Current username: {}, IP address: {}", username, this.client.getRemoteSocketAddress());
			return username;
	}
	
	public void write(String message) {
		writer.print(message);
		writer.flush();
	}
	
	@Override
	public void run() {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MM-dd 'at' hh:mm:ss aa");
			log.info("handling client {}", this.client.getRemoteSocketAddress());
			log.info("Obtaining username for {}", this.client.getRemoteSocketAddress());
			while (!this.client.isClosed()) {
				writer.flush();
				String echo = reader.readLine();
				log.info("received message [{}] from client {} ({}), echoing...", echo, username, 
						this.client.getRemoteSocketAddress());
				Date date = new Date();
				ServerChat.setMessage(("[" + dateFormat.format(date) + "] " + username + ": " + echo));
			}
			this.close();
		} catch (IOException e) {
			ServerChat.setMessage(username + " has disconnected");
		} 
		
	}

	@Override
	public void close() throws IOException {
		log.info("closing connection to client {}", this.client.getRemoteSocketAddress());
		this.client.close();
	}

}
