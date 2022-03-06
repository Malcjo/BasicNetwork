package MavenMessangerClient.MavenMessangerClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
//clean javafx:run
//10.140.159.243
//192.168.1.69

public class ClientGUI extends Application {
	TextField portInput = new TextField();
	TextField addressInput = new TextField();
	Text portNumber;
	Text addressText;
	Button conServerBtn;
	Button SentTestBtn;
	Button SentTestBtn2;
	int port = 9090;
	String ServerAddress = "192.168.1.69";
	ThreadedClient server;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("User 1");
		portNumber = new Text(Integer.toString(port));
		addressText = new Text(ServerAddress);
		portInput.setEditable(true);
		Text serverport = new Text("Server Port: ");
		Text addressport = new Text("IP address: ");
		conServerBtn = new Button("Connect to Server");
		SentTestBtn = new Button("Send Test message to server");
		SentTestBtn2 = new Button("Send second Test message to server");

		conServerBtn.setOnAction(value -> {
			ConnectServer();
		});

		portInput.setOnAction(value -> {

			int savedValue = Integer.parseInt(portInput.getText());
			SetPortGUI(savedValue);
			port = savedValue;
			portInput.clear();
		});

		addressInput.setOnAction(value -> {

			String newvalue = portInput.getText();
			SetAddressGUI(newvalue);
			ServerAddress = newvalue;
			portInput.clear();
		});
		
		SentTestBtn.setOnAction(value -> {
			server.SendMessageToServer("Test message");
		});
		SentTestBtn2.setOnAction(value -> {
			server.SendMessageToServer("send to server");
		});

		HBox portHBox = new HBox(serverport, portNumber, portInput);
		HBox ipHBox = new HBox(addressport, addressText, addressInput);
		VBox vbox = new VBox(ipHBox, portHBox, conServerBtn, SentTestBtn, SentTestBtn2);
		Scene scene = new Scene(vbox, 300, 200);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void ConnectServer() {
		try {
			(new ServicedClient(this)).start();
			;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void SetPortGUI(int port) {
		String newPort = Integer.toString(port);
		portNumber.setText(newPort + " ");
	}
	
	public void SetAddressGUI(String value) {
		addressText.setText(value + " ");
	}

	public void ConnectThreadedClient(ThreadedClient t) {
		this.server = t;
	}
}
