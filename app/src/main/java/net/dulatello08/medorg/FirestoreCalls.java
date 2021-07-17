package net.dulatello08.medorg;

import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class FirestoreCalls {
    private static final String TAG = "FirestoreCalls";
    // Access a Cloud Firestore instance from your Activity
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static void insertMap(Map<String, String> map, String docName, String collectionPath) {
        db.collection(collectionPath).document(docName)
                .set(map)
                .addOnSuccessListener((OnSuccessListener) documentReference -> Log.d(TAG, "DocumentSnapshot added"))
                .addOnFailureListener((OnFailureListener) e -> Log.w(TAG, "Error adding document", e));
    }
}
