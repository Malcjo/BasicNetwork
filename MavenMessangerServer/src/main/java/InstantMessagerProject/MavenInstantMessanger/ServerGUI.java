package InstantMessagerProject.MavenInstantMessanger;

import java.io.IOException;
import java.net.InetAddress;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ServerGUI extends Application {

	//192.168.1.69
	TableView<ClientObject> table;
	ObservableList<ClientObject> clientList = FXCollections.observableArrayList();
	static int port = 9090;
	static String IPAdress = "";
	Text portNumber;
	TextField portInput  = new TextField();
	ServicedServer server;
	ThreadedServer threadedServer;
	ServerThread serverThread;
	Button startServerBtn;
	Button SendMessageToClient;
//	TableView<ClientObject> table;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setWidth(300);
		primaryStage.setHeight(500);
		InetAddress IP = InetAddress.getLocalHost();
		String IPString = IP.toString();
		String[] iparr = IPString.split("/");
		String ipText = iparr[1];
		
		primaryStage.setTitle("Server");

		IPAdress = ipText;
		Text IPText = new Text("Server IP: " + IPAdress);
		Text serverport = new Text("Server Port: ");
		portNumber = new Text(Integer.toString(port));
		portInput.setEditable(true);

		startServerBtn = new Button("Start Server");
		SendMessageToClient = new Button("Send message to client");
		
		

		table = new TableView<ClientObject>();
        table.setEditable(true);
        
        TableColumn usernameCol = new TableColumn("Client Name");
        TableColumn ipCol = new TableColumn("Client IP");
        
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("Username"));
        ipCol.setCellValueFactory(new PropertyValueFactory<>("IPaddress"));
        
        table.getColumns().addAll(usernameCol, ipCol);
		table.getItems().addAll(clientList);
		
		startServerBtn.setOnAction(value -> {
			if(server == null) {

				StartServer();
			}
			else {
				StopServer();
			}

		});
		portInput.setOnAction(value -> {

			int savedValue = Integer.parseInt(portInput.getText());
			SetPortGUI(savedValue);
			port = savedValue;
			portInput.clear();
		});
		SendMessageToClient.setOnAction(value -> {
			try {
				serverThread.SendMessageToClient("Test message to client", "Server");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		
		HBox hbox = new HBox(serverport, portNumber, portInput);
		VBox vbox = new VBox(IPText, hbox, startServerBtn, SendMessageToClient,table);
		Scene scene = new Scene(vbox, 300, 200);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	private void StopServer() {
		server.cancel();
		portInput.setEditable(true);
		startServerBtn.setText("Start Server");
	}
	private void StartServer() {
		if(port == 0 || port < 9090) {
			System.out.println("There is no valid port to connect to");
			return;
		}

		startServerBtn.setText("Stop Server");
		portInput.setEditable(false);
		try {
			System.out.println("Server is running");
			(server = new ServicedServer(this)).start(); 
		} catch (Exception e) {

		}

	}
	public void SetupThreadedServer(ThreadedServer t) { 
		threadedServer = t;
	}
	
	public void SetPortGUI(int port) {
		String newPort = Integer.toString(port);
		portNumber.setText(newPort + " ");
	}
	public void SetUpServerThread(ServerThread s) {
		this.serverThread = s;
	}
	public void AddToClientList(ClientObject client) {
		clientList.add(client);
		previewClientList();
	}
	
	private void previewClientList() {
		for(ClientObject c : clientList) {
			System.out.println("Object in clientList: ");
			c.PrintAll();
		}
	}
	
	public void UpdateTable() {
		table.getItems().clear();
		table.getItems().addAll(clientList);
	}
}

