package com.example.journalapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;

public class AddJournalActivity extends AppCompatActivity {

    private EditText titleEditText, thoughtsEditText;
    private Button saveButton;
    private ImageView imageView, addPhotoBtn;
    private ProgressBar progressBar;
    private Uri imageUri;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Journal");
    private StorageReference storageReference;
    private String currentUserId, currentUserName;

    ActivityResultLauncher<String> mTakePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_journal);

        titleEditText = findViewById(R.id.post_title_et);
        thoughtsEditText = findViewById(R.id.post_description_et);
        saveButton = findViewById(R.id.post_save_journal_button);
        imageView = findViewById(R.id.post_imageView);
        addPhotoBtn = findViewById(R.id.postCameraButton);
        progressBar = findViewById(R.id.post_progressBar);

        storageReference = FirebaseStorage.getInstance().getReference("journal_images");
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        if (user != null) {
            currentUserId = user.getUid();
            currentUserName = user.getDisplayName();
        }

        progressBar.setVisibility(View.INVISIBLE);

        mTakePhoto = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        imageUri = result;
                        imageView.setImageURI(imageUri);
                    }
                }
        );

        addPhotoBtn.setOnClickListener(v -> mTakePhoto.launch("image/*"));

        saveButton.setOnClickListener(v -> SaveJournal());
    }

    private void SaveJournal() {
        String title = titleEditText.getText().toString().trim();
        String thoughts = thoughtsEditText.getText().toString().trim();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(thoughts) || imageUri == null) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        final StorageReference filePath = storageReference.child("my_image_" + Timestamp.now().getSeconds());

        filePath.putFile(imageUri).addOnSuccessListener(taskSnapshot ->
                filePath.getDownloadUrl().addOnSuccessListener(uri -> {
                    String imageUrl = uri.toString();

                    Journal journal = new Journal(title, thoughts, imageUrl, currentUserId, currentUserName, new Timestamp(new Date()));

                    collectionReference.add(journal).addOnSuccessListener(documentReference -> {
                        Toast.makeText(AddJournalActivity.this, "Journal Saved", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddJournalActivity.this, JournalListActivity.class));
                        finish();
                    }).addOnFailureListener(e ->
                            Toast.makeText(AddJournalActivity.this, "Failed to save: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    progressBar.setVisibility(View.INVISIBLE);
                })).addOnFailureListener(e ->
                Toast.makeText(AddJournalActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show());
    }
}
