package InstantMessagerProject.MavenInstantMessanger;

import javafx.beans.property.SimpleStringProperty;

public class ClientObject {

	private final SimpleStringProperty Username;
	private final SimpleStringProperty IPaddress;

	ClientObject(String name, String IP){
		
        this.Username = new SimpleStringProperty(name);
        this.IPaddress = new SimpleStringProperty(IP);
	}

	public String getUsername() {
		return Username.get();
	}

	public void setusername(String clientName) {
		this.Username.set(clientName);
	}

	public String getIPaddress() {
		return IPaddress.get();
	}

	public void setIPaddress(String iPaddress) {
		IPaddress.set(iPaddress);
	}

	public void PrintAll() {
		System.out.println(Username);
		System.out.println(IPaddress);
	}

}

