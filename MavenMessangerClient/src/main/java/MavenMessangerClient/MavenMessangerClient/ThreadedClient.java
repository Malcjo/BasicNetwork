package MavenMessangerClient.MavenMessangerClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.crypto.Cipher;

public class ThreadedClient{
	ClientGUI s;
	PrintWriter out;
	BufferedReader input;
	Socket clientSocket;
	CeasarCiphar ciphar;
	
	public ThreadedClient(ClientGUI s) throws UnknownHostException, IOException {
		this.s = s;
		this.ciphar = new CeasarCiphar();
		this.clientSocket = new Socket(s.ServerAddress, s.port);
		this.out = new PrintWriter(clientSocket.getOutputStream(), true);
		this.input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		s.ConnectThreadedClient(this);

		String username ="Username";
		String password = "123";
		String login = username+":"+password;
		out.println(login);
		WaitingForInputFromServer();

	}

	public void WaitingForInputFromServer() throws IOException { 
		while(true) {
			System.out.println("Waitng for answer");
			String answer = input.readLine();
			System.out.println(answer);
			String newAnswer = ciphar.Decrypt(answer, 3);
			System.out.println(newAnswer);
		}
	}
	
	public void SendMessageToServer(String text) {
		String hiddenText = ciphar.Encrypt(text, 3);
		out.println(hiddenText);
	}
}

