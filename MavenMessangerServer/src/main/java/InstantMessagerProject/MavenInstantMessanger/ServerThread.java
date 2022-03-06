package InstantMessagerProject.MavenInstantMessanger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {

	ServerGUI g;
	Socket socket;
	PrintWriter out;
	BufferedReader input;
	CeasarCiphar ciphar;
	String username;
	ThreadedServer threadedServer;

	public ServerThread(Socket s, ServerGUI g, ThreadedServer t) {
		this.socket = s;
		this.g = g;
		this.threadedServer = t;
		this.ciphar = new CeasarCiphar();
		g.SetUpServerThread(this);
	}

	public void run() {
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			System.out.println("A client request recieved at " + socket);

			input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			
			String ClientRequest = input.readLine();
			System.out.println(ClientRequest);
			String[] requestArray = ClientRequest.split(":");
			username = requestArray[0];
			String password = requestArray[1];
			
			if(password.equals("123")) {
				String localAddress = socket.getLocalAddress().toString();
				String[] localAddArray = localAddress.split("/");
				String clientIp = localAddArray[1];
				System.out.println(clientIp);
				System.out.println(username + " Connected");
				
				ClientObject newClient = new ClientObject(username, clientIp);
				g.AddToClientList(newClient);
				threadedServer.addUserToList(username, this);
				g.UpdateTable();
				WaitingForInputFromClient();
			}


		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void WaitingForInputFromClient() throws IOException{
		while(true) {
			System.out.println("Waitng for Client");
			String hiddenAnswer = input.readLine();
			String answer = ciphar.Decrypt(hiddenAnswer, 3);
			threadedServer.storeMessage(answer, username, username);
			System.out.println(username+":"+answer);
		}
	}
	
	public void SendMessageToClient(String text, String recepient) throws IOException {
		String  newMessage = "sent from "+recepient+": "  + text;
		String hidden =ciphar.Encrypt(newMessage, 3);
		out.println(hidden);
	}
}

