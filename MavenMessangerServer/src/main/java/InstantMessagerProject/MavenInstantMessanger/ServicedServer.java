package InstantMessagerProject.MavenInstantMessanger;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javafx.concurrent.Service;

public class ServicedServer extends Service<ServerGUI> {
	ServerGUI s;
	ThreadedServer threadedServer;
	public ServicedServer(ServerGUI s) {
		this.s = s;
	}
	protected Task<ServerGUI> createTask(){
		return new Task<ServerGUI>() {
			protected ServerGUI call() throws Exception{
				System.out.println("Server Open"); 
				threadedServer = new ThreadedServer(s);
				return s;
			}
		};
	}
}

