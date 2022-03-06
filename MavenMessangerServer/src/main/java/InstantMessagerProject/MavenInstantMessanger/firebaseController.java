package InstantMessagerProject.MavenInstantMessanger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.common.collect.Lists;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
// [START firestore_deps]
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
// [END firestore_deps]
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class firebaseController 
{
	static GoogleCredentials authExplicit(String jsonPath) throws IOException {
		  // You can specify a credential file by providing a path to GoogleCredentials.
		  // Otherwise credentials are read from the GOOGLE_APPLICATION_CREDENTIALS environment variable.
		  GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(jsonPath))
		        .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
		  return credentials;
		}
	
	public void authenticate() {
		
	}
	Firestore db;
	public Firestore getInstance(GoogleCredentials credentials, String projectId) {
		FirebaseOptions options = new FirebaseOptions.Builder()
			    .setCredentials(credentials)
			    .setProjectId(projectId)
			    .build();
			FirebaseApp.initializeApp(options);

			return FirestoreClient.getFirestore();
	}
	
	public firebaseController() throws IOException, InterruptedException, ExecutionException {
		//authentication
		GoogleCredentials credentials = authExplicit("messangerdb-edd449c884f3.json");
		//get the instance of the project
		db = getInstance(credentials, "messangerdb");
		//manipulate that instance
	}
	
	public void saveData(String username, String target, Object message) throws InterruptedException, ExecutionException{
		DocumentReference docRef = db.collection(username).document(target);
		// Add document data  with id "alovelace" using a hashmap
		Map<Object, Object> data = new HashMap<>();
		data.put(target, message);
		//asynchronously write data
		ApiFuture<WriteResult> result = docRef.set(data);
		result.get();
		System.out.println("Sata saved");
		// ...
		// result.get() blocks on response
		//System.out.println("Update time : " + result.get().getUpdateTime());
	}
	
	public void readData(String username, String target) throws InterruptedException, ExecutionException {
		// asynchronously retrieve all users
		ApiFuture<QuerySnapshot> query = db.collection(username).get();
		// ...
		// query.get() blocks on response
		QuerySnapshot querySnapshot = query.get();
		List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
		for (QueryDocumentSnapshot document : documents) {
			if(document.getId().equals(target)) {
			}
		  System.out.println("User: " + document.getId());
		  System.out.println("First: " + document.getString(target));
		  if (document.contains("middle")) {
		    System.out.println("Middle: " + document.getString("middle"));
		  }
		  System.out.println("Last: " + document.getString("last"));
		  System.out.println("Born: " + document.getLong("born"));
		}
	}
	
    public static void main( String[] args ) throws IOException, InterruptedException, ExecutionException
    {
        System.out.println( "Hello World!" );
        new firebaseController();
    }
}

