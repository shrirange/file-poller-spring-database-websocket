package com.example.filepoller;

public interface Websocketservice {
	
	void sendMessage(String message);
	
	void sendPrivateMessage(String message, String sessionId);
	
	void sendUserMessage(String message);

}
