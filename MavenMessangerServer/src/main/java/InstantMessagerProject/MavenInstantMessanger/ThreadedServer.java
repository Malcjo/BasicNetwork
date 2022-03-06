package InstantMessagerProject.MavenInstantMessanger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class ThreadedServer {

//	firebaseController dbController;
	 HashMap<String, ServerThread> userList = new HashMap<String, ServerThread>();
	 HashMap<String, File> userdb = new HashMap<String, File>();

	ServerGUI s;

	ThreadedServer(ServerGUI s) throws IOException, InterruptedException, ExecutionException{
		this.s = s;
		System.out.println("Server started on port: # "+ s.port);
		ServerSocket listener = new ServerSocket(s.port);
		s.SetupThreadedServer(this);
//		dbController = new firebaseController();
		
		try {
			while(true) {
				System.out.println("Connect socket to listener"); 
				Socket socket = listener.accept();
				try {
					System.out.println("Connect socket");
					new ServerThread(socket, s, this).start(); 
				}
				finally {
					
				}
			}

		}finally 
		{
			
		}
	}
	
	public void storeMessage(String message, String target, String username) throws IOException {
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(username+".txt", true)))) {
		    out.println(target+":"+message);
		} catch (IOException e) {
		    System.err.println(e);
		}
		System.out.println("Checking entries");
		for(Map.Entry<String,ServerThread> entry : userList.entrySet()) {
			System.out.println(entry.getKey() + ' ' + entry.getValue());
			if (entry.getKey().equals(username)) {
				ServerThread tempThread = entry.getValue();
				try {
					Scanner scanner = new Scanner(new File(username+".txt"));
					while (scanner.hasNextLine()) {
					   String line = scanner.nextLine();
					   String[] check = line.split(":");
					   if(check[0].equals(target)) {
							tempThread.SendMessageToClient(line, username);
					   }
					}
					System.out.println("Message sent to client");


				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}
	}
	
	public void addUserToList(String username, ServerThread thread) throws IOException {
		
		File newFile = new File(username+".txt");
		if(newFile.createNewFile()) {
			userdb.put(username, newFile);
			userList.put(username, thread);
		}

	}
}

