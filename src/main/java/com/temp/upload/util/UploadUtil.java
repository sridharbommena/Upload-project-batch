package com.temp.upload.util;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class UploadUtil {

    UploadUtil()
    {
        FirebaseApp firebaseApp = null;
        try {

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/firebase-config.json")))
                    .setStorageBucket("upload-project-firebase.appspot.com")
                    .build();

            firebaseApp = FirebaseApp.initializeApp(options);
        } catch (Exception exception) {
            System.out.println("Exception: " + exception.getMessage());
        }
    }


    public Firestore getFireStore() {
        return FirestoreClient.getFirestore();
    }

    public Bucket getBucket()
    {
        return StorageClient.getInstance().bucket();
    }

    public long timeDiffNow(String docDate)
    {
            Long currentTimeStamp = System.currentTimeMillis();
            System.out.println("current time stamp: "+currentTimeStamp);

            long difference_In_Time = currentTimeStamp - Long.parseLong(docDate);

            long difference_In_Days
                    = (difference_In_Time
                    / (1000 * 60 * 60 * 24));

            long difference_In_Minutes
                = (difference_In_Time
                / (1000 * 60));

            System.out.println("Difference: "+ difference_In_Minutes);
            return difference_In_Minutes;
            }
}
