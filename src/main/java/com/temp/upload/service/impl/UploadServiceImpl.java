package com.temp.upload.service.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Bucket;
import com.temp.upload.service.UploadService;
import com.temp.upload.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    private UploadUtil uploadUtil;

    private final String GSURL_PREFIX="gs://upload-project-firebase.appspot.com/";
    private final String COLLECTION_NAME = "upload-project";

    @Override
    public void deleteFiles() {
        Firestore fireStoreDb = uploadUtil.getFireStore();
        ApiFuture<QuerySnapshot> query = fireStoreDb.collection(COLLECTION_NAME).get();
        QuerySnapshot querySnapshot = null;
        try {
            querySnapshot = query.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

        for (QueryDocumentSnapshot queryFile:documents) {
            DocumentReference reference = queryFile.getReference();
            String timestamp = (String) queryFile.get("createdAt").toString();
            System.out.println("Timestamp of Doc: "+timestamp);
            if(uploadUtil.timeDiffNow(timestamp)>=5)
            {
                String path = (String) queryFile.get("path");
                deleteFromStorageByLocation(path);
                if(reference.delete().isDone())
                {
                    System.out.println(reference.toString()+": deleted from firestore!");
                }
            }
            else
            {
                System.out.println(reference.toString()+": skipped");
            }
        }

        System.out.println("Completed");

    }

    private void deleteFromStorageByLocation(String path) {
        Bucket bucket = uploadUtil.getBucket();
        bucket.getStorage().delete(BlobId.fromGsUtilUri(GSURL_PREFIX+path));
        System.out.println("Deleted");
    }
}
