package MavenMessangerClient.MavenMessangerClient;



public class CeasarCiphar {

	String Alphabet = " ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	CeasarCiphar() {
//		String message = "This is a test message counting 123";
//		System.out.println(Decrypt(Encrypt(message, 3), 3));

	}

	String Encrypt(String text, int offset) {
		String result = "";
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			int pos = Alphabet.indexOf(c);
			int newPos = (pos + offset) % Alphabet.length();
			result += Alphabet.charAt(newPos);
		}
		return result;
	}

	String Decrypt(String cipher, int offset) {
		String result = "";
		for (int i = 0; i < cipher.length(); i++) {
			char c = cipher.charAt(i);
			int pos = Alphabet.indexOf(c);
			int newPos = (pos - offset) % Alphabet.length();
			result += Alphabet.charAt(newPos);
		}
		return result;
	}

	public static void main(String[] args) {
		new CeasarCiphar();
	}
}
