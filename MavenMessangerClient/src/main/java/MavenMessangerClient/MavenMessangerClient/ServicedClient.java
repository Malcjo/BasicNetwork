package MavenMessangerClient.MavenMessangerClient;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class ServicedClient extends Service<ClientGUI>{
	ThreadedClient threadedClient;
	ClientGUI s;
	public ServicedClient(ClientGUI s) {
		this.s = s;
	}
	@Override
	protected Task<ClientGUI> createTask() {
		return new Task<ClientGUI>() {
			protected ClientGUI call() throws Exception{
				System.out.println("Client Open");
				threadedClient = new ThreadedClient(s);
				return s;
			}
		};
	}
} 
